package com.sg2d.modules.auth;

import com.sg2d.app.AppComponent;
import com.sg2d.modules.auth.presentation.AuthEntryViewModel;

import dagger.Component;

@AuthEntryScope
@Component(modules = AuthEntryModule.class, dependencies = AppComponent.class)
public interface AuthEntryComponent {
    void inject(AuthEntryViewModel target);
}
