package com.sg2d.modules.auth.data.local;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.sg2d.modules.auth.data.remote.AuthEntryApiResponse;

public class AuthJsonConverters {
    @TypeConverter
    public AuthEntryApiResponse gsonToAuth(String json) {
        return new Gson().fromJson(json, AuthEntryApiResponse.class);
    }

    @TypeConverter
    public String AuthToGson(AuthEntryApiResponse city) {
        return new Gson().toJson(city);
    }
}
