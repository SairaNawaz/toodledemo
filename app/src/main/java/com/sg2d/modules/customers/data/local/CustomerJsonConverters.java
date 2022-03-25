package com.sg2d.modules.customers.data.local;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.sg2d.modules.customers.data.remote.CustomerEntryApiResponse;

public class CustomerJsonConverters {
    @TypeConverter
    public CustomerEntryApiResponse.Customer gsonToCustomer(String json) {
        return new Gson().fromJson(json, CustomerEntryApiResponse.Customer.class);
    }

    @TypeConverter
    public String CustomerToGson(CustomerEntryApiResponse.Customer city) {
        return new Gson().toJson(city);
    }

//    @TypeConverter
//    public WeatherEntryApiResponse.Coord gsonToCoord(String json) {
//        return new Gson().fromJson(json, WeatherEntryApiResponse.Coord.class);
//    }
//
//    @TypeConverter
//    public String CoordToGson(WeatherEntryApiResponse.Coord coord) {
//        return new Gson().toJson(coord);
//    }
//
//    @TypeConverter
//    public WeatherEntryApiResponse.Temp gsonToCondition(String json) {
//        return new Gson().fromJson(json, WeatherEntryApiResponse.Temp.class);
//    }
//
//    @TypeConverter
//    public String TempToGson(WeatherEntryApiResponse.Temp temp) {
//        return new Gson().toJson(temp);
//    }
//
//    @TypeConverter
//    public WeatherEntryApiResponse.Weather gsonToWeather(String json) {
//        return new Gson().fromJson(json, WeatherEntryApiResponse.Weather.class);
//    }
//
//    @TypeConverter
//    public String WeatherToGson(WeatherEntryApiResponse.Weather weather) {
//        return new Gson().toJson(weather);
//    }
//
//    @TypeConverter
//    public WeatherEntryApiResponse.Result gsonToForecast(String json) {
//        return new Gson().fromJson(json, WeatherEntryApiResponse.Result.class);
//    }
//
//    @TypeConverter
//    public String ResultToGson(WeatherEntryApiResponse.Result forecast) {
//        return new Gson().toJson(forecast);
//    }
//
//
//    @TypeConverter
//    public List<WeatherEntryApiResponse.Result> gsonToForecastList(String json) {
//        return new Gson().fromJson(json, new TypeToken<List<WeatherEntryApiResponse.Result>>() {
//        }.getType());
//    }
//
//    @TypeConverter
//    public String ForecastToGson(List<WeatherEntryApiResponse.Result> forecast) {
//        return new Gson().toJson(forecast);
//    }
//
//    @TypeConverter
//    public static Date toDate(Long timestamp) {
//        return timestamp == null ? null : new Date(timestamp);
//    }
//
//    @TypeConverter
//    public static Long toTimestamp(Date date) {
//        return date == null ? null : date.getTime();
//    }
}
