package com.sg2d.app;

import android.app.Application;
import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private Application application;

    AppModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application provideApp() {
        return application;
    }

    @Singleton
    @Provides
    AppDatabase provideDb() {
        return Room.databaseBuilder(application, AppDatabase.class,"cleanapp.db").build();
    }

}
