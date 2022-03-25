package com.sg2d.modules.customers.usecases.customers;

import android.util.Log;

import com.sg2d.base.usecases.FlowableUseCase;
import com.sg2d.modules.customers.data.CustomerEntryRepo;
import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class GetCustomerEntryFlowableUseCase extends FlowableUseCase<GetCustomerEntryFlowableUseCase.Input, List<CustomerEntry>> {

    private static final String TAG = GetCustomerEntryFlowableUseCase.class.getSimpleName();
    private CustomerEntryRepo repo;

    @Inject
    GetCustomerEntryFlowableUseCase(CustomerEntryRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Input input, DisposableSubscriber<List<CustomerEntry>> subscriber) {

        Flowable.just(input.myId)
                .flatMap(myId -> repo.getCustomerEntry(myId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);

        Log.d(TAG, "called subscribe on getPlaceEntry flowable");

        disposables.add(subscriber);
    }

    public static class Input {

        private int myId;

        private Scheduler observerOnScheduler;

        public Input(int myId, Scheduler observerOnScheduler) {
            this.myId = myId;
            this.observerOnScheduler = observerOnScheduler;
        }
    }

}
