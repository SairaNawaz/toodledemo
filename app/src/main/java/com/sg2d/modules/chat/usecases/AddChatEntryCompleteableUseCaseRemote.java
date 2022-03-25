package com.sg2d.modules.chat.usecases;

import android.util.Log;

import com.sg2d.base.usecases.SingleUseCase;
import com.sg2d.modules.chat.data.ChatEntryRepo;
import com.sg2d.modules.chat.entities.ChatEntry;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.schedulers.Schedulers;


public class AddChatEntryCompleteableUseCaseRemote extends SingleUseCase<AddChatEntryCompleteableUseCaseRemote.Input, ChatEntry> {

    private static final String TAG = AddChatEntryCompleteableUseCaseRemote.class.getSimpleName();
    private ChatEntryRepo repo;

    @Inject
    public AddChatEntryCompleteableUseCaseRemote(ChatEntryRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Input input, SingleObserver<ChatEntry> subscriber) {
        Single.just(input)
                .flatMap(input1 -> repo.addChatEntryRemote(input1.chatEntry))
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);
        Log.d(TAG, "called subscribe on addChat Single");

        //  disposables.add(subscriber);
    }


    public static class Input {

        private ChatEntry chatEntry;
        private Scheduler observerOnScheduler;

        public Input(ChatEntry chatEntry, Scheduler observerOnScheduler) {
            this.chatEntry = chatEntry;
            this.observerOnScheduler = observerOnScheduler;
        }
    }

}
