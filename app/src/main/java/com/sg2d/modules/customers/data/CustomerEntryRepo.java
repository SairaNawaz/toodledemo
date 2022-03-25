package com.sg2d.modules.customers.data;

import com.sg2d.modules.customers.entities.CustomerEntry;

import java.util.List;

import io.reactivex.Flowable;

public interface CustomerEntryRepo {
    Flowable<List<CustomerEntry>> getCustomerEntry(int myId);
}
