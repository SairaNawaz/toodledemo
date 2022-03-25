package com.sg2d.app;

import android.app.Application;

import com.sg2d.modules.auth.AuthEntryComponent;
import com.sg2d.modules.auth.AuthEntryModule;
import com.sg2d.modules.auth.DaggerAuthEntryComponent;
import com.sg2d.modules.chat.ChatEntryComponent;
import com.sg2d.modules.chat.ChatEntryModule;
import com.sg2d.modules.chat.DaggerChatEntryComponent;
import com.sg2d.modules.customers.CustomerEntryComponent;
import com.sg2d.modules.customers.CustomerEntryModule;
import com.sg2d.modules.customers.DaggerCustomerEntryComponent;

public class CleanArchApp extends Application {


    private AppComponent appComponent;
    private AuthEntryComponent authEntryComponent;
    private CustomerEntryComponent customerEntryComponent;
    private ChatEntryComponent chatEntryComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = buildAppComponent();
    }


    private AppComponent buildAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }


    /********Support for Feature Component ****************/

    public AuthEntryComponent buildAuthEntryComponent() {
        authEntryComponent = DaggerAuthEntryComponent.builder()
                .appComponent(appComponent)
                .authEntryModule(new AuthEntryModule())
                .build();
        return authEntryComponent;
    }

    public void releaseAuthEntryComponent() {
        authEntryComponent = null;
    }

    public AuthEntryComponent getAuthEntryComponent() {
        return authEntryComponent;
    }

    /**********************************************************/
    public CustomerEntryComponent buildCustomerEntryComponent() {
        customerEntryComponent = DaggerCustomerEntryComponent.builder()
                .appComponent(appComponent)
                .customerEntryModule(new CustomerEntryModule())
                .build();
        return customerEntryComponent;
    }

    public void releaseCustomerEntryComponent() {
        customerEntryComponent = null;
    }

    public CustomerEntryComponent getCustomerEntryComponent() {
        return customerEntryComponent;
    }

    /**********************************************************/

    public ChatEntryComponent buildChatEntryComponent() {
        chatEntryComponent = DaggerChatEntryComponent.builder()
                .appComponent(appComponent)
                .chatEntryModule(new ChatEntryModule())
                .build();
        return chatEntryComponent;
    }

    public void releaseChatEntryComponent() {
        chatEntryComponent = null;
    }

    public ChatEntryComponent getChatEntryComponent() {
        return chatEntryComponent;
    }

    /**********************************************************/

}
