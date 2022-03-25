package com.sg2d.modules.auth.data;

import com.sg2d.modules.auth.entities.AuthEntry;
import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

public interface AuthEntryRepo {
    Single<AuthEntry> getAuthEntry(String username, String password);
}

