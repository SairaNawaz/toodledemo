package com.sg2d.modules.chat.usecases;

import android.util.Log;

import com.sg2d.base.usecases.FlowableUseCase;
import com.sg2d.modules.chat.data.ChatEntryRepo;
import com.sg2d.modules.chat.entities.ChatEntry;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;


public class GetChatEntryFlowableUseCase extends FlowableUseCase<GetChatEntryFlowableUseCase.Input, List<ChatEntry>> {

    private static final String TAG = GetChatEntryFlowableUseCase.class.getSimpleName();
    private ChatEntryRepo repo;

    @Inject
    GetChatEntryFlowableUseCase(ChatEntryRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Input input, DisposableSubscriber<List<ChatEntry>> subscriber) {

        Flowable.just(input)
                .flatMap(input1 -> repo.getChatEntry(input1.myId, input1.customerId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);

        Log.d(TAG, "called subscribe on getChatEntry flowable");

        disposables.add(subscriber);
    }

    public static class Input {

        private int myId;
        private int customerId;

        private Scheduler observerOnScheduler;

        public Input(int myId, int customerId, Scheduler observerOnScheduler) {
            this.myId = myId;
            this.customerId = customerId;
            this.observerOnScheduler = observerOnScheduler;
        }
    }

}
