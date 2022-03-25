package com.sg2d.modules.chat.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class GetChatEntryApiResponse {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("error")
    @Expose
    public String error;


    @SerializedName("msg_content")
    @Expose
    public ArrayList<MessageContent> msg_content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<MessageContent> getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(ArrayList<MessageContent> msg_content) {
        this.msg_content = msg_content;
    }

    public class MessageContent {

        @SerializedName("id")
        @Expose
        public int id;

        @SerializedName("userid")
        @Expose
        public int userid;

        @SerializedName("recipientid")
        @Expose
        public int recipientid;

        @SerializedName("timestamp")
        @Expose
        public String timestamp;

        @SerializedName("body")
        @Expose
        public String body;

        @SerializedName("status")
        @Expose
        public int status;

        @SerializedName("type")
        @Expose
        public String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
//    @SerializedName("city")
//    @Expose
//    private City city;
//    @SerializedName("cod")
//    @Expose
//    private String cod;
//    @SerializedName("message")
//    @Expose
//    private Double message;
//    @SerializedName("cnt")
//    @Expose
//    private Integer cnt;
//    @SerializedName("list")
//    @Expose
//    private List<Result> list = null;
//
//    public City getCity() {
//        return city;
//    }
//
//    public void setCity(City city) {
//        this.city = city;
//    }
//
//    public String getCod() {
//        return cod;
//    }
//
//    public void setCod(String cod) {
//        this.cod = cod;
//    }
//
//    public Double getMessage() {
//        return message;
//    }
//
//    public void setMessage(Double message) {
//        this.message = message;
//    }
//
//    public Integer getCnt() {
//        return cnt;
//    }
//
//    public void setCnt(Integer cnt) {
//        this.cnt = cnt;
//    }
//
//    public List<Result> getList() {
//        return list;
//    }
//
//    public void setList(List<Result> list) {
//        this.list = list;
//    }
//
//    public class City {
//
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("name")
//        @Expose
//        private String name;
//        @SerializedName("coord")
//        @Expose
//        private Coord coord;
//        @SerializedName("country")
//        @Expose
//        private String country;
//        @SerializedName("population")
//        @Expose
//        private Integer population;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public Coord getCoord() {
//            return coord;
//        }
//
//        public void setCoord(Coord coord) {
//            this.coord = coord;
//        }
//
//        public String getCountry() {
//            return country;
//        }
//
//        public void setCountry(String country) {
//            this.country = country;
//        }
//
//        public Integer getPopulation() {
//            return population;
//        }
//
//        public void setPopulation(Integer population) {
//            this.population = population;
//        }
//
//    }
//
//    public class Coord {
//
//        @SerializedName("lon")
//        @Expose
//        private Double lon;
//        @SerializedName("lat")
//        @Expose
//        private Double lat;
//
//        public Double getLon() {
//            return lon;
//        }
//
//        public void setLon(Double lon) {
//            this.lon = lon;
//        }
//
//        public Double getLat() {
//            return lat;
//        }
//
//        public void setLat(Double lat) {
//            this.lat = lat;
//        }
//
//    }
//
//    public class Result {
//
//        @SerializedName("dt")
//        @Expose
//        private Integer dt;
//        @SerializedName("temp")
//        @Expose
//        private Temp temp;
//        @SerializedName("pressure")
//        @Expose
//        private Double pressure;
//        @SerializedName("humidity")
//        @Expose
//        private Double humidity;
//        @SerializedName("weather")
//        @Expose
//        private java.util.List<Weather> weather = null;
//        @SerializedName("speed")
//        @Expose
//        private Double speed;
//        @SerializedName("deg")
//        @Expose
//        private Integer deg;
//        @SerializedName("clouds")
//        @Expose
//        private Integer clouds;
//
//        public Integer getDt() {
//            return dt;
//        }
//
//        public void setDt(Integer dt) {
//            this.dt = dt;
//        }
//
//        public Temp getTemp() {
//            return temp;
//        }
//
//        public void setTemp(Temp temp) {
//            this.temp = temp;
//        }
//
//        public Double getPressure() {
//            return pressure;
//        }
//
//        public void setPressure(Double pressure) {
//            this.pressure = pressure;
//        }
//
//        public Double getHumidity() {
//            return humidity;
//        }
//
//        public void setHumidity(Double humidity) {
//            this.humidity = humidity;
//        }
//
//        public java.util.List<Weather> getWeather() {
//            return weather;
//        }
//
//        public void setWeather(java.util.List<Weather> weather) {
//            this.weather = weather;
//        }
//
//        public Double getSpeed() {
//            return speed;
//        }
//
//        public void setSpeed(Double speed) {
//            this.speed = speed;
//        }
//
//        public Integer getDeg() {
//            return deg;
//        }
//
//        public void setDeg(Integer deg) {
//            this.deg = deg;
//        }
//
//        public Integer getClouds() {
//            return clouds;
//        }
//
//        public void setClouds(Integer clouds) {
//            this.clouds = clouds;
//        }
//
//    }
//
//    public class Temp {
//
//        @SerializedName("day")
//        @Expose
//        private Double day;
//        @SerializedName("min")
//        @Expose
//        private Double min;
//        @SerializedName("max")
//        @Expose
//        private Double max;
//        @SerializedName("night")
//        @Expose
//        private Double night;
//        @SerializedName("eve")
//        @Expose
//        private Double eve;
//        @SerializedName("morn")
//        @Expose
//        private Double morn;
//
//        public Double getDay() {
//            return day;
//        }
//
//        public void setDay(Double day) {
//            this.day = day;
//        }
//
//        public Double getMin() {
//            return min;
//        }
//
//        public void setMin(Double min) {
//            this.min = min;
//        }
//
//        public Double getMax() {
//            return max;
//        }
//
//        public void setMax(Double max) {
//            this.max = max;
//        }
//
//        public Double getNight() {
//            return night;
//        }
//
//        public void setNight(Double night) {
//            this.night = night;
//        }
//
//        public Double getEve() {
//            return eve;
//        }
//
//        public void setEve(Double eve) {
//            this.eve = eve;
//        }
//
//        public Double getMorn() {
//            return morn;
//        }
//
//        public void setMorn(Double morn) {
//            this.morn = morn;
//        }
//
//    }
//
//    public class Weather {
//
//        @SerializedName("id")
//        @Expose
//        private Integer id;
//        @SerializedName("main")
//        @Expose
//        private String main;
//        @SerializedName("description")
//        @Expose
//        private String description;
//        @SerializedName("icon")
//        @Expose
//        private String icon;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getMain() {
//            return main;
//        }
//
//        public void setMain(String main) {
//            this.main = main;
//        }
//
//        public String getDescription() {
//            return description;
//        }
//
//        public void setDescription(String description) {
//            this.description = description;
//        }
//
//        public String getIcon() {
//            return icon;
//        }
//
//        public void setIcon(String icon) {
//            this.icon = icon;
//        }
//
//    }

}


