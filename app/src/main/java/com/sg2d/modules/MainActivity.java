package com.sg2d.modules;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.sg2d.modules.auth.entities.AuthEntry;
import com.sg2d.modules.chat.presentation.ChatFragment;
import com.sg2d.modules.customers.presentation.CustomersFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ContentLoadingProgressBar progressBar;
    Handler mHandler;
    TextView headerTitle;
    ImageView backBtn;
    ImageView logoutBtn;
    public AuthEntry authEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loadFragment(R.string.tag_users, null);
    }

    void initViews() {
        authEntry = new Gson().fromJson(getIntent().getStringExtra("auth_user"), AuthEntry.class);
        progressBar = findViewById(R.id.progressBar);
        mHandler = new Handler();
        headerTitle = findViewById(R.id.header_title);
        backBtn = findViewById(R.id.btn_back);
        logoutBtn = findViewById(R.id.btn_logout);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void showProgress() {
        if (progressBar != null && !progressBar.isShown()) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.show();
        }
    }

    public void logout() {
//        Intent i = new Intent(Login.this,
//                MainActivity.class);
//        //Intent is used to switch from one activity to another.
//        startActivity(i);
        //invoke the SecondActivity.
        finish();
    }

    public void hideProgress() {
        if (progressBar != null && progressBar.isShown()) {
            progressBar.hide();
            progressBar.setVisibility(View.GONE);
        }
    }

    public void updateHeader(String title, boolean showBack) {
        headerTitle.setText(title);
        if (showBack) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setEnabled(true);
        } else {
            backBtn.setVisibility(View.INVISIBLE);
            backBtn.setEnabled(false);
        }
    }

    public void loadFragment(final int fTagId, Bundle b) {
        final FragmentManager fm = getSupportFragmentManager();
        String title = getResources().getString(fTagId);
        final String tagString = Integer.toString(fTagId);
        Fragment cFragment = fm.findFragmentByTag(tagString);
        if (cFragment == null) {
            final Fragment fragment = getFragmentByTag(fTagId);
            if (b != null) {
                fragment.setArguments(b);
            }
            Runnable mPendingRunnable = new Runnable() {
                @Override
                public void run() {
                    FragmentTransaction fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_right,
                            R.anim.slide_out_left, R.anim.slide_in_left,
                            R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.fragment_container, fragment, tagString);
                    fragmentTransaction.addToBackStack(tagString);
                    fragmentTransaction.commit();
                    fm.executePendingTransactions();
                }
            };

            if (mPendingRunnable != null) {
                mHandler.post(mPendingRunnable);
            }
        } else {
            fm.popBackStackImmediate(tagString, 0);
        }
    }

    Fragment getFragmentByTag(int tag) {
        Fragment fragment = null;
        switch (tag) {
            case R.string.tag_users:
                fragment = new CustomersFragment();
                break;
            case R.string.tag_chat:
                fragment = new ChatFragment();
                break;
        }

        return fragment;
    }

    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (f instanceof ChatFragment) {
            getSupportFragmentManager().popBackStackImmediate();
        } else {
            finish();
        }
    }
}
