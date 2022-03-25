package com.sg2d.modules.chat.usecases;

import android.util.Log;

import com.sg2d.base.usecases.SingleUseCase;
import com.sg2d.modules.chat.data.ChatEntryRepo;
import com.sg2d.modules.chat.entities.ChatEntry;
import com.sg2d.modules.chat.entities.ChatEntrySynced;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.schedulers.Schedulers;


public class UpdateChatEntryCompleteableUseCaseLocal extends SingleUseCase<UpdateChatEntryCompleteableUseCaseLocal.Input, ChatEntrySynced> {

    private static final String TAG = UpdateChatEntryCompleteableUseCaseLocal.class.getSimpleName();
    private ChatEntryRepo repo;

    @Inject
    UpdateChatEntryCompleteableUseCaseLocal(ChatEntryRepo repo) {
        this.repo = repo;
    }

    @Override
    public void execute(Input input, SingleObserver<ChatEntrySynced> subscriber) {
        Single.just(input)
                .flatMap(input1 -> repo.updateChatEntryLocal(input1.oldChatEntry,input.newChatEntry))
                .subscribeOn(Schedulers.newThread())
                .observeOn(input.observerOnScheduler)
                .subscribe(subscriber);
        Log.d(TAG, "called subscribe on addChat Single");

        //  disposables.add(subscriber);
    }


    public static class Input {

        private ChatEntry oldChatEntry;
        private ChatEntry newChatEntry;
        private Scheduler observerOnScheduler;

        public Input(ChatEntry oldChatEntry,ChatEntry newChatEntry, Scheduler observerOnScheduler) {
            this.oldChatEntry = oldChatEntry;
            this.newChatEntry = newChatEntry;
            this.observerOnScheduler = observerOnScheduler;
        }
    }

}
