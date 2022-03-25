package com.sg2d.modules.chat;

import com.sg2d.app.AppDatabase;
import com.sg2d.app.utils.Constants;
import com.sg2d.modules.chat.data.ChatEntryRepo;
import com.sg2d.modules.chat.data.ChatEntryRepoImpl;
import com.sg2d.modules.chat.data.local.ChatEntryDao;
import com.sg2d.modules.chat.data.remote.ChatApiService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ChatEntryModule {

    @ChatEntryScope
    @Provides
    ChatEntryDao provideChatEntryDao(AppDatabase db) {
        return db.chatEntryDao();
    }

    @ChatEntryScope
    @Provides
    ChatApiService provideCustomerApiService() {

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
                .create(ChatApiService.class);
    }

    @ChatEntryScope
    @Provides
    ChatEntryRepo provideChatEntryRepo(AppDatabase appDatabase, ChatApiService apiService) {
        return new ChatEntryRepoImpl(appDatabase, apiService);
    }
}
