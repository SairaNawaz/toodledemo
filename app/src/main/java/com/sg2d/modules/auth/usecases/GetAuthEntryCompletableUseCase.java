package com.sg2d.modules.auth.usecases;

import android.util.Log;

import com.sg2d.base.usecases.SingleUseCase;
import com.sg2d.modules.auth.data.AuthEntryRepo;
import com.sg2d.modules.auth.entities.AuthEntry;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.schedulers.Schedulers;


public class GetAuthEntryCompletableUseCase extends SingleUseCase<GetAuthEntryCompletableUseCase.Input, AuthEntry> {

    private static final String TAG = GetAuthEntryCompletableUseCase.class.getSimpleName();
    private AuthEntryRepo repo;

    @Inject
    GetAuthEntryCompletableUseCase(AuthEntryRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Input input, SingleObserver<AuthEntry> subscriber) {

        Single.just(input)
                .flatMap(input1 -> repo.getAuthEntry(input1.username, input1.password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);
        Log.d(TAG, "called subscribe on addChat Single");

        Log.d(TAG, "called subscribe on getPlaceEntry flowable");

        //   disposables.add(subscriber);
    }

    public static class Input {

        private String username;
        private String password;
        private Scheduler observerOnScheduler;

        public Input(String username, String password, Scheduler observerOnScheduler) {
            this.username = username;
            this.password = password;
            this.observerOnScheduler = observerOnScheduler;
        }
    }

}
