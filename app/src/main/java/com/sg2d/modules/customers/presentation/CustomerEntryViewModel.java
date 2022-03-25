package com.sg2d.modules.customers.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.util.Log;

import com.sg2d.app.CleanArchApp;
import com.sg2d.modules.customers.entities.CustomerEntry;
import com.sg2d.modules.customers.usecases.customers.GetCustomerEntryFlowableUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class CustomerEntryViewModel extends AndroidViewModel {

    private final static String TAG = CustomerEntryViewModel.class.getSimpleName();
    @Inject
    Lazy<GetCustomerEntryFlowableUseCase> getCustomerEntryUseCase;
    private MutableLiveData<List<CustomerEntry>> customerEntry;

    public CustomerEntryViewModel(Application application) {
        super(application);
        ((CleanArchApp) application).getCustomerEntryComponent().inject(this);
    }

    LiveData<List<CustomerEntry>> getCustomerEntry() {
        if (customerEntry == null) {
            customerEntry = new MutableLiveData<>();
        }
        return customerEntry;
    }

    void loadCustomerEntry(int myId) {
        getCustomerEntryUseCase.get().execute(
                new GetCustomerEntryFlowableUseCase.Input(myId, AndroidSchedulers.mainThread()),
                new UseCaseSubscriber());

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //remove subscriptions if any
        getCustomerEntryUseCase.get().cancel();
        Log.d(TAG, "onCleared");
    }

    private class UseCaseSubscriber extends DisposableSubscriber<List<CustomerEntry>> {

        @Override
        public void onNext(List<CustomerEntry> entries) {

            Log.d(TAG, "Received response for weatherEntry");
            CustomerEntryViewModel.this.customerEntry.setValue(entries);
        }

        @Override
        public void onError(Throwable e) {

            Log.d(TAG, "Received error: " + e.toString());
//            PlaceEntryViewModel.this.weatherEntry.setValue( new PlaceEntry(-1, "",
//                    getApplication().getString(R.string.no_results_found)));
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete called");
        }
    }
}
