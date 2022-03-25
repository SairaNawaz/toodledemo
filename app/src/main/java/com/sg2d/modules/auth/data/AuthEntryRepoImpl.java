package com.sg2d.modules.auth.data;

import android.text.TextUtils;
import android.util.Log;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.auth.data.local.AuthEntryTable;
import com.sg2d.modules.auth.data.remote.AuthApiService;
import com.sg2d.modules.auth.data.remote.AuthEntryApiResponse;
import com.sg2d.modules.auth.entities.AuthEntry;
import com.sg2d.modules.customers.data.local.AuthEntryDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AuthEntryRepoImpl implements AuthEntryRepo {

    private static final String TAG = AuthEntryRepoImpl.class.getSimpleName();
    private AppDatabase appDatabase;
    private AuthApiService authApiService;

    @Inject
    public AuthEntryRepoImpl(AppDatabase appDatabase, AuthApiService authApiService) {
        this.appDatabase = appDatabase;
        this.authApiService = authApiService;
    }

    @Override
    public Single<AuthEntry> getAuthEntry(String username, String password) {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            return fetchFromLocal();
        }
        return fetchFromRemote(username, password);
    }

    private Single<AuthEntry> fetchFromLocal() {
        Single<AuthEntryTable> entries = appDatabase.authEntryDao().getAuthEntry();
        return entries.flatMap(result -> {
            AuthEntry newEntry = new AuthEntry(result.getCode(), result.getError(),
                    result.getMyId(), result.getUsername(), result.getPassword());
            return Single.just(newEntry);
        });
    }

    private Single<AuthEntry> fetchFromRemote(String username, String password) {

        Log.d(TAG, "loginFromRemote enter");
        Single<AuthEntryApiResponse> loginRequest = authApiService.getAuthEntry(Constants.API_TOKEN, username, password);

        return loginRequest.flatMap(result -> {
            Log.d("Auth", "Login to remote");
            AuthEntry newEntry = new AuthEntry(result.getCode(), result.getError(), result.getMyId(), username, password);
            if (result.getError().equalsIgnoreCase("Success")) {
                appDatabase.beginTransaction();
                try {
                    AuthEntryDao entryDao = appDatabase.authEntryDao();
                    entryDao.insert(new AuthEntryTable(result.getCode(), result.getError(), result.getMyId(), username, password));
                    appDatabase.setTransactionSuccessful();
                } finally {
                    appDatabase.endTransaction();
                }
                Log.d(TAG, "added new entry into app database table");
            }
            return Single.just(newEntry);
        });
    }


}
