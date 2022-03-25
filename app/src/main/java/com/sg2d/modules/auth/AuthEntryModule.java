package com.sg2d.modules.auth;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.auth.data.AuthEntryRepo;
import com.sg2d.modules.auth.data.AuthEntryRepoImpl;
import com.sg2d.modules.auth.data.remote.AuthApiService;
import com.sg2d.modules.customers.data.CustomerEntryRepo;
import com.sg2d.modules.customers.data.CustomerEntryRepoImpl;
import com.sg2d.modules.customers.data.local.AuthEntryDao;
import com.sg2d.modules.customers.data.local.CustomerEntryDao;
import com.sg2d.modules.customers.data.remote.CustomerApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AuthEntryModule {

    @AuthEntryScope
    @Provides
    AuthEntryDao provideAuthEntryDao(AppDatabase db) {
        return db.authEntryDao();
    }

    @AuthEntryScope
    @Provides
    AuthApiService provideAuthApiService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        return new Retrofit.Builder()
                .baseUrl(Constants.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
                .create(AuthApiService.class);
    }

    @AuthEntryScope
    @Provides
    AuthEntryRepo provideAuthEntryRepo(AppDatabase appDatabase, AuthApiService apiService) {
        return new AuthEntryRepoImpl(appDatabase, apiService);
    }


}
