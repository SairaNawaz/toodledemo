package com.sg2d.modules.customers;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.customers.data.CustomerEntryRepo;
import com.sg2d.modules.customers.data.CustomerEntryRepoImpl;
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
public class CustomerEntryModule {

    @CustomerEntryScope
    @Provides
    CustomerEntryDao provideCustomerEntryDao(AppDatabase db) {
        return db.customerEntryDao();
    }

    @CustomerEntryScope
    @Provides
    CustomerApiService provideCustomerApiService() {

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
                .create(CustomerApiService.class);
    }

    @CustomerEntryScope
    @Provides
    CustomerEntryRepo provideCustomerEntryRepo(AppDatabase appDatabase, CustomerApiService apiService) {
        return new CustomerEntryRepoImpl(appDatabase, apiService);
    }


}
