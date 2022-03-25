package com.sg2d.modules.customers.entities;

public class CustomerEntry {

    public int id;
    public String name;
    public String email;
    public int level;

    public CustomerEntry(int id, String name, String email, int level) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    //    private long dt;
//    private Date date;
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
//    public WeatherEntry(long dt, Date date, double temp_min, double temp_max, double temp_night, double temp_eve,
//                        double temp_morn, double pressure, double humidity, String weather_main, String description,
//                        String icon, double speed, double deg, double clouds) {
//        this.dt = dt;
//        this.date = date;
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
