package com.sg2d.modules.auth.presentation;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sg2d.app.CleanArchApp;
import com.sg2d.modules.auth.entities.AuthEntry;
import com.sg2d.modules.auth.usecases.GetAuthEntryCompletableUseCase;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class AuthEntryViewModel extends AndroidViewModel {

    private final static String TAG = AuthEntryViewModel.class.getSimpleName();
    @Inject
    Lazy<GetAuthEntryCompletableUseCase> getAuthEntryUseCase;
    private MutableLiveData<AuthEntry> authEntry;

    public AuthEntryViewModel(Application application) {
        super(application);
        ((CleanArchApp) application).getAuthEntryComponent().inject(this);
    }

    LiveData<AuthEntry> getAuthEntry() {
        if (authEntry == null) {
            authEntry = new MutableLiveData<>();
        }
        return authEntry;
    }


    void loadAuthEntry(String username, String password) {
        getAuthEntryUseCase.get().execute(
                new GetAuthEntryCompletableUseCase.Input(username, password, AndroidSchedulers.mainThread()),
                getAuthObserverLocal());

    }

    @Override
    protected void onCleared() {
        super.onCleared();
        //remove subscriptions if any
        getAuthEntryUseCase.get().cancel();
        Log.d(TAG, "onCleared");
    }

    private SingleObserver<AuthEntry> getAuthObserverLocal() {
        return new SingleObserver<AuthEntry>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "Subscribed for Auth Entry Local");
            }

            @Override
            public void onSuccess(AuthEntry authEntry) {
                Log.d(TAG, "Received response for Auth Entry Local");
                AuthEntryViewModel.this.authEntry.setValue(authEntry);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Received Error response for Auth Entry Local");
                AuthEntryViewModel.this.authEntry.setValue(new AuthEntry());
            }
        };
    }

}
