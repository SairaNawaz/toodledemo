package com.sg2d.modules.chat.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatEntryTable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int msg_id;
    public int userid;
    public int recipientid;
    public String timestamp = "..";
    public String body;
    public int status;
    public String type;
    public boolean isSynced = true;
    public boolean isError = false;

    public ChatEntryTable(int msg_id, int userid, int recipientid,
                          String timestamp, String body, int status, String type, boolean isSynced, boolean isError) {
        this.msg_id = msg_id;
        this.userid = userid;
        this.recipientid = recipientid;
        this.timestamp = timestamp;
        this.body = body;
        this.status = status;
        this.type = type;
        this.isSynced = isSynced;
        this.isError = isError;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRecipientid() {
        return recipientid;
    }

    public void setRecipientid(int recipientid) {
        this.recipientid = recipientid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//    private Date date;
//    private long dt;
//    private double temp_min;
//    private double temp_max;
//    private double temp_night;
//    private double temp_eve;
//    private double temp_morn;
//    private double pressure;
//    private double humidity;
//    private String weather_main;
//    private String description;
//    private String icon;
//    private double speed;
//    private double deg;
//    private double clouds;
//
//    public WeatherEntryTable( long dt, Date date,double temp_min, double temp_max, double temp_night,
//                             double temp_eve, double temp_morn, double pressure, double humidity,
//                             String weather_main, String description, String icon, double speed, double deg,
//                             double clouds) {
//        this.date = date;
//        this.dt = dt;
//        this.temp_min = temp_min;
//        this.temp_max = temp_max;
//        this.temp_night = temp_night;
//        this.temp_eve = temp_eve;
//        this.temp_morn = temp_morn;
//        this.pressure = pressure;
//        this.humidity = humidity;
//        this.weather_main = weather_main;
//        this.description = description;
//        this.icon = icon;
//        this.speed = speed;
//        this.deg = deg;
//        this.clouds = clouds;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public long getDt() {
//        return dt;
//    }
//
//    public void setDt(long dt) {
//        this.dt = dt;
//    }
//
//    public double getTemp_min() {
//        return temp_min;
//    }
//
//    public void setTemp_min(double temp_min) {
//        this.temp_min = temp_min;
//    }
//
//    public double getTemp_max() {
//        return temp_max;
//    }
//
//    public void setTemp_max(double temp_max) {
//        this.temp_max = temp_max;
//    }
//
//    public double getTemp_night() {
//        return temp_night;
//    }
//
//    public void setTemp_night(double temp_night) {
//        this.temp_night = temp_night;
//    }
//
//    public double getTemp_eve() {
//        return temp_eve;
//    }
//
//    public void setTemp_eve(double temp_eve) {
//        this.temp_eve = temp_eve;
//    }
//
//    public double getTemp_morn() {
//        return temp_morn;
//    }
//
//    public void setTemp_morn(double temp_morn) {
//        this.temp_morn = temp_morn;
//    }
//
//    public double getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(double pressure) {
//        this.pressure = pressure;
//    }
//
//    public double getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(double humidity) {
//        this.humidity = humidity;
//    }
//
//    public String getWeather_main() {
//        return weather_main;
//    }
//
//    public void setWeather_main(String weather_main) {
//        this.weather_main = weather_main;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getIcon() {
//        return icon;
//    }
//
//    public void setIcon(String icon) {
//        this.icon = icon;
//    }
//
//    public double getSpeed() {
//        return speed;
//    }
//
//    public void setSpeed(double speed) {
//        this.speed = speed;
//    }
//
//    public double getDeg() {
//        return deg;
//    }
//
//    public void setDeg(double deg) {
//        this.deg = deg;
//    }
//
//    public double getClouds() {
//        return clouds;
//    }
//
//    public void setClouds(double clouds) {
//        this.clouds = clouds;
//    }
}
