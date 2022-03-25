package com.sg2d.modules.customers.data;

import android.util.Log;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.customers.data.local.CustomerEntryDao;
import com.sg2d.modules.customers.data.local.CustomerEntryTable;
import com.sg2d.modules.customers.data.remote.CustomerApiService;
import com.sg2d.modules.customers.data.remote.CustomerEntryApiResponse;
import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class CustomerEntryRepoImpl implements CustomerEntryRepo {

    private static final String TAG = CustomerEntryRepoImpl.class.getSimpleName();
    private AppDatabase appDatabase;
    private CustomerApiService customerApiService;

    @Inject
    public CustomerEntryRepoImpl(AppDatabase appDatabase, CustomerApiService customerApiService) {
        this.appDatabase = appDatabase;
        this.customerApiService = customerApiService;
    }

    @Override
    public Flowable<List<CustomerEntry>> getCustomerEntry(int myId) {

        //define your single source of truth : is it cache? database? or cloud?

        Flowable<List<CustomerEntry>> local = fetchFromLocal(myId);
        Flowable<List<CustomerEntry>> remote = fetchFromRemote(myId);

        return Flowable.merge(local, remote).firstElement().toFlowable();

    }


    private Flowable<List<CustomerEntry>> fetchFromLocal(int myId) {

        Flowable<List<CustomerEntryTable>> entries = appDatabase.customerEntryDao().getAll();
        List<CustomerEntry> data = new ArrayList<>();
        return entries.flatMap(customerEntryTables -> {

            if (!customerEntryTables.isEmpty()) {
                for (CustomerEntryTable row :
                        customerEntryTables) {
                    data.add(new CustomerEntry(row.getId(), row.getName(), row.getEmail()
                            , row.getLevel()));
                }
                return Flowable.just(data);
            }

            Log.d(TAG, "Returning flowable with invalid entry from local");
            return Flowable.empty();
        });

    }


    private Flowable<List<CustomerEntry>> fetchFromRemote(int myId) {

        Log.d(TAG, "fetchFromRemote enter");
        Flowable<CustomerEntryApiResponse> getRequest = customerApiService.getCustomerEntry(Constants.API_TOKEN,myId);

        return getRequest.flatMap(customerEntryApiResponse -> {
            List<CustomerEntryTable> data = new ArrayList<>();
            List<CustomerEntry> data1 = new ArrayList<>();
            Log.d(TAG, "received response from remote");
            List<CustomerEntryApiResponse.Customer> result = customerEntryApiResponse.getCustomers_list();
            for (int i = 0; i < result.size(); i++) {
                CustomerEntryApiResponse.Customer row = result.get(i);
                data.add(new CustomerEntryTable(row.getId(), row.getName(), row.getEmail(), row.getLevel()));
                data1.add(new CustomerEntry(row.getId(), row.getName(), row.getEmail(), row.getLevel()));
            }

            appDatabase.beginTransaction();
            try {
                CustomerEntryDao entryDao = appDatabase.customerEntryDao();
                entryDao.delete();
                entryDao.insertBulk(data);
                appDatabase.setTransactionSuccessful();
            } finally {
                appDatabase.endTransaction();
            }
            Log.d(TAG, "added new entry into app database table");

            Log.d(TAG, "Sending entry from remote");

            return Flowable.just(data1);
        });
    }


}
