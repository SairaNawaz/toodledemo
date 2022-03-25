package com.sg2d.modules.chat;

import com.sg2d.app.AppComponent;
import com.sg2d.modules.chat.presentation.ChatEntryViewModel;

import dagger.Component;

@ChatEntryScope
@Component(modules = ChatEntryModule.class, dependencies = AppComponent.class)
public interface ChatEntryComponent {
    void inject(ChatEntryViewModel target);
}
