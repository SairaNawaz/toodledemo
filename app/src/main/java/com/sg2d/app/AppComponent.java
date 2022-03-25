package com.sg2d.app;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component (modules = AppModule.class)
public interface AppComponent {
    Application provideApp();
    AppDatabase provideDb();

}
