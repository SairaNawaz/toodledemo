package com.sg2d.modules.customers;

import com.sg2d.app.AppComponent;
import com.sg2d.modules.customers.presentation.CustomerEntryViewModel;

import dagger.Component;

@CustomerEntryScope
@Component(modules = CustomerEntryModule.class, dependencies = AppComponent.class)
public interface CustomerEntryComponent {
    void inject(CustomerEntryViewModel target);
}
