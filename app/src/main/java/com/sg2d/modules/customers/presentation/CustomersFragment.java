package com.sg2d.modules.customers.presentation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
import com.sg2d.modules.R;
import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.ArrayList;
import java.util.Collections;

public class CustomersFragment extends Fragment implements CustomersAdapter.CustomerSelectListener {

    private static final String TAG = CustomersFragment.class.getSimpleName();
    RecyclerView userlistView;
    CustomersAdapter customersAdapter;
    ArrayList<CustomerEntry> customerList;
    private CustomerEntryViewModel customerEntryViewModel;
    View view;
    TextView no_result_found;
    EditText search_box;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_customers, container, false);
        setUpViews();
        return view;
    }

    void setUpViews() {
        //WeatherEntry feature component scope start here
        ((CleanArchApp) getActivity().getApplication()).buildCustomerEntryComponent();

        initViews();
        ((MainActivity) getActivity()).showProgress();
        customerEntryViewModel.loadCustomerEntry(((MainActivity) getActivity()).authEntry.getMyId());
    }

    void initViews() {
        userlistView = view.findViewById(R.id.users_list_view);
        no_result_found = view.findViewById(R.id.no_result_found);
        search_box = view.findViewById(R.id.search_box);

        customerList = new ArrayList<>();
        customersAdapter = new CustomersAdapter(getActivity(), this, customerList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        userlistView.setLayoutManager(mLayoutManager);
        userlistView.setItemAnimator(new DefaultItemAnimator());
        userlistView.setAdapter(customersAdapter);
        customerEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(CustomerEntryViewModel.class);
        //ViewModelProviders.of(this).get(CustomerEntryViewModel.class);

        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                customersAdapter.getFilter().filter(search_box.getText());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).updateHeader(getResources().getString(R.string.tag_users), false);
        customerEntryViewModel.getCustomerEntry().observe(this, customerEntry -> {
            Log.d(TAG, "received update for chatEntry, response:" + new Gson().toJson(customerEntry));
            ((MainActivity) getActivity()).hideProgress();
            this.customerList.clear();
            this.customerList.addAll(customerEntry);
            Collections.sort(customerList, (lhs, rhs) -> lhs.getLevel() - rhs.getLevel());
            customersAdapter.notifyDataSetChanged();
            if (customerList.size() == 0) {
                no_result_found.setVisibility(View.VISIBLE);
            } else {
                no_result_found.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((CleanArchApp) getActivity().getApplication()).releaseCustomerEntryComponent();
    }

    @Override
    public void onCustomerSelect(int userId) {
        Bundle bundle = new Bundle();
        bundle.putInt("customer_id", userId);
        ((MainActivity) getActivity()).loadFragment(R.string.tag_chat, bundle);
    }
}
