package com.sg2d.modules.chat.presentation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sg2d.app.CleanArchApp;
import com.sg2d.modules.MainActivity;
import com.sg2d.app.utils.Utils;
import com.sg2d.modules.R;
import com.sg2d.modules.chat.entities.ChatEntry;
import com.sg2d.modules.chat.entities.FileBody;

import java.util.ArrayList;
import java.util.Collections;

import static android.app.Activity.RESULT_OK;
import static com.sg2d.app.utils.Constants.TYPE_FILE;
import static com.sg2d.app.utils.Constants.TYPE_TEXT;

public class ChatFragment extends Fragment implements View.OnClickListener, ChatAdapter.onChatItemClickListener {

    private static final String TAG = ChatFragment.class.getSimpleName();
    RecyclerView chatlistView;
    ChatAdapter chatAdapter;
    ArrayList<ChatEntry> chatList;
    private ChatEntryViewModel chatEntryViewModel;
    View view;
    int customerId;
    TextView no_result_found;
    EditText input_message;
    ImageView btn_send;
    ImageView btn_file;
    ImageView input_image;
    Uri fileuri;
    EditText search_box;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_chat, container, false);
        setUpViews();
        return view;
    }

    void setUpViews() {
        //ChatEntry feature component scope start here
        ((CleanArchApp) getActivity().getApplication()).buildChatEntryComponent();
        initViews();
        ((MainActivity) getActivity()).showProgress();
        chatEntryViewModel.loadChatEntry(((MainActivity)getActivity()).authEntry.getMyId(), customerId);
    }

    void initViews() {
        customerId = getArguments().getInt("customer_id");
        chatlistView = view.findViewById(R.id.chat_list_view);
        no_result_found = view.findViewById(R.id.no_result_found);
        input_message = view.findViewById(R.id.input_message);
        input_image = view.findViewById(R.id.input_image);
        search_box = view.findViewById(R.id.search_box);
        btn_send = view.findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this::onClick);
        btn_file = view.findViewById(R.id.btn_file);
        btn_file.setOnClickListener(this::onClick);
        chatList = new ArrayList<>();
        chatAdapter = new ChatAdapter(getActivity(), 1, chatList, this::onChatItemClick);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        chatlistView.setLayoutManager(mLayoutManager);
        chatlistView.setItemAnimator(new DefaultItemAnimator());
        chatlistView.setAdapter(chatAdapter);
        chatEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(ChatEntryViewModel.class);

        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                chatAdapter.getFilter().filter(search_box.getText());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateHeader(getResources().getString(R.string.tag_chat), true);
        setObservers();
    }

    void setObservers() {
        chatEntryViewModel.getChatEntry().observe(this, chatEntry -> {
            Log.d(TAG, "received update for getchatEntry, response:" + new Gson().toJson(chatEntry));
            ((MainActivity) getActivity()).hideProgress();
            chatList.clear();
            chatList.addAll(chatEntry);
            Collections.sort(chatList, (lhs, rhs) -> lhs.getMsg_id() - rhs.getMsg_id());
            chatAdapter.notifyDataSetChanged();
            if (chatList.size() > 0) {
                chatlistView.scrollToPosition(chatList.size() - 1);
                no_result_found.setVisibility(View.GONE);
            } else {
                no_result_found.setVisibility(View.VISIBLE);
            }
        });

        chatEntryViewModel.addChatEntryLocal().observe(this, chatEntry -> {
            Log.d(TAG, "received update for addchatEntry Local, response:" + new Gson().toJson(chatEntry));
            if (chatEntry.getType().equalsIgnoreCase(TYPE_FILE)) {
                fileuri = null;
                input_image.setVisibility(View.GONE);
            }
            chatList.add(chatEntry);
            if (chatList.size() > 0) {
                chatAdapter.notifyItemInserted(chatList.size() - 1);
                chatlistView.scrollToPosition(chatList.size() - 1);
                no_result_found.setVisibility(View.GONE);
            } else {
                no_result_found.setVisibility(View.VISIBLE);
            }
        });

        chatEntryViewModel.updateChatEntry().observe(this, entrySynced -> {
            Log.d(TAG, "received update for addchatEntry Remote, response:" + new Gson().toJson(entrySynced));
            int searchedIndex = chatList.indexOf(entrySynced.oldEntry);
            chatList.set(searchedIndex, entrySynced.syncedEntry);
            chatAdapter.notifyItemChanged(searchedIndex);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Log.d("footer", "onActivityResult: " + data);
            fileuri = data.getData();
            input_image.setImageURI(fileuri);
            input_image.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Utils.GALLERY_READ_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    launchChooserActivity();
                }
                return;
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((CleanArchApp) getActivity().getApplication()).releaseChatEntryComponent();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_send) {
            int id = (int) (Math.random() * 50 + 1);
            if (fileuri != null) {
                String fileBody = new Gson().toJson(new FileBody(fileuri.toString(), "temp_file", Utils.getPath(getActivity(), fileuri)));
                chatEntryViewModel.insertChatEntry(new ChatEntry(id, 1, customerId, "--",
                        fileBody, 0, TYPE_FILE, false, false));
            } else if (!TextUtils.isEmpty(input_message.getText())) {
                String msg = input_message.getText().toString();
                input_message.setText("");
                chatEntryViewModel.insertChatEntry(new ChatEntry(id, 1, customerId, "--"
                        , msg, 0, TYPE_TEXT, false, false));
            }
        } else if (v == btn_file) {
            if (Utils.isGalleryPermissionGranted(getActivity())) {
                launchChooserActivity();
            } else {
                Utils.requestGalleryPermission(getActivity());
            }
        }
    }

    void launchChooserActivity() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    Utils.PICK_IMAGE_REQUEST);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getActivity(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChatItemClick(ChatEntry chatEntry) {
        chatEntryViewModel.updateChatEntryLocalSync(chatEntry, chatEntry.copyWithError(chatEntry, false));
    }
}
