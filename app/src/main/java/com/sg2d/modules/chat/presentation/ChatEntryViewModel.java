package com.sg2d.modules.chat.presentation;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sg2d.app.CleanArchApp;
import com.sg2d.modules.chat.entities.ChatEntry;
import com.sg2d.modules.chat.entities.ChatEntrySynced;
import com.sg2d.modules.chat.usecases.AddChatEntryCompleteableUseCaseLocal;
import com.sg2d.modules.chat.usecases.AddChatEntryCompleteableUseCaseRemote;
import com.sg2d.modules.chat.usecases.GetChatEntryFlowableUseCase;
import com.sg2d.modules.chat.usecases.UpdateChatEntryCompleteableUseCaseLocal;

import java.util.List;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.DisposableSubscriber;

public class ChatEntryViewModel extends AndroidViewModel {

    private final static String TAG = ChatEntryViewModel.class.getSimpleName();
    @Inject
    Lazy<GetChatEntryFlowableUseCase> getChatEntryUseCase;
    @Inject
    Lazy<AddChatEntryCompleteableUseCaseLocal> addChatEntryUseCase;
    @Inject
    Lazy<AddChatEntryCompleteableUseCaseRemote> addChatEntryUseCaseRemote;
    @Inject
    Lazy<UpdateChatEntryCompleteableUseCaseLocal> updateChatEntryUseCaseLocal;
    private MutableLiveData<List<ChatEntry>> chatEntry;
    private MutableLiveData<ChatEntry> addChatEntryLocal;
    private MutableLiveData<ChatEntrySynced> updateChatEntry;

    public ChatEntryViewModel(Application application) {
        super(application);
        ((CleanArchApp) application).getChatEntryComponent().inject(this);
    }

    LiveData<ChatEntry> addChatEntryLocal() {
        if (addChatEntryLocal == null) {
            addChatEntryLocal = new MutableLiveData<>();
        }
        return addChatEntryLocal;
    }

    LiveData<ChatEntrySynced> updateChatEntry() {
        if (updateChatEntry == null) {
            updateChatEntry = new MutableLiveData<>();
        }
        return updateChatEntry;
    }

    LiveData<List<ChatEntry>> getChatEntry() {
        if (chatEntry == null) {
            chatEntry = new MutableLiveData<>();
        }
        return chatEntry;
    }

    void insertChatEntry(ChatEntry chatEntry) {
        addChatEntryUseCase.get().execute(new AddChatEntryCompleteableUseCaseLocal.
                Input(chatEntry, AndroidSchedulers.mainThread()), getAddChatObserverLocal());
    }

    void insertChatEntryRemote(ChatEntry chatEntry) {
        addChatEntryUseCaseRemote.get().execute(new AddChatEntryCompleteableUseCaseRemote.
                Input(chatEntry, AndroidSchedulers.mainThread()), getAddChatObserverRemote(chatEntry));
    }

    void updateChatEntryLocal(ChatEntry oldChatEntry, ChatEntry newChatEntry) {
        updateChatEntryUseCaseLocal.get().execute(new UpdateChatEntryCompleteableUseCaseLocal.
                Input(oldChatEntry, newChatEntry, AndroidSchedulers.mainThread()), getUpdateChatObserverLocal());
    }

    void updateChatEntryLocalSync(ChatEntry oldChatEntry, ChatEntry newChatEntry) {
        updateChatEntryUseCaseLocal.get().execute(new UpdateChatEntryCompleteableUseCaseLocal.
                Input(oldChatEntry, newChatEntry, AndroidSchedulers.mainThread()), getUpdateChatObserverLocalSync());
    }

    void loadChatEntry(int myId, int customerId) {
        getChatEntryUseCase.get().execute(
                new GetChatEntryFlowableUseCase.Input(myId, customerId, AndroidSchedulers.mainThread()),
                new GetUseCaseSubscriber());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //remove subscriptions if any
        getChatEntryUseCase.get().cancel();
        Log.d(TAG, "onCleared");
    }


    private class GetUseCaseSubscriber extends DisposableSubscriber<List<ChatEntry>> {

        @Override
        public void onNext(List<ChatEntry> entries) {

            Log.d(TAG, "Received response for getChat");
            ChatEntryViewModel.this.chatEntry.setValue(entries);
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "Received error: " + e.toString());
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "GetChat onComplete called");
        }
    }

    private SingleObserver<ChatEntry> getAddChatObserverLocal() {
        return new SingleObserver<ChatEntry>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Subscribed for addChat Entry Local");
            }

            @Override
            public void onSuccess(ChatEntry chatEntry) {
                Log.d(TAG, "Received response for addChat Entry Local");
                insertChatEntryRemote(chatEntry);
                ChatEntryViewModel.this.addChatEntryLocal.setValue(chatEntry);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private SingleObserver<ChatEntrySynced> getUpdateChatObserverLocal() {
        return new SingleObserver<ChatEntrySynced>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Subscribed for addChat Entry Local");
            }

            @Override
            public void onSuccess(ChatEntrySynced chatEntry) {
                Log.d(TAG, "Received response for addChat Entry Local");
                ChatEntryViewModel.this.updateChatEntry.setValue(chatEntry);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private SingleObserver<ChatEntrySynced> getUpdateChatObserverLocalSync() {
        return new SingleObserver<ChatEntrySynced>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Subscribed for addChat Entry Local");
            }

            @Override
            public void onSuccess(ChatEntrySynced chatEntry) {
                Log.d(TAG, "Received response for addChat Entry Local");
                insertChatEntryRemote(chatEntry.syncedEntry);
                ChatEntryViewModel.this.updateChatEntry.setValue(chatEntry);
            }

            @Override
            public void onError(Throwable e) {

            }
        };
    }

    private SingleObserver<ChatEntry> getAddChatObserverRemote(ChatEntry chatEntryOld) {
        return new SingleObserver<ChatEntry>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Subscribed for addChat Entry Remote");
            }

            @Override
            public void onSuccess(ChatEntry chatEntry) {
                Log.d(TAG, "Received response for addChat Entry Remote");
                updateChatEntryLocal(chatEntryOld, chatEntry);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Received response for addChat Entry Remote Error");
                updateChatEntryLocal(chatEntryOld, chatEntryOld.copyWithError(chatEntryOld,true));
            }
        };
    }
}
