package com.sg2d.modules.auth.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AuthEntryApiResponse {

    @SerializedName("code")
    @Expose
    public String code;

    @SerializedName("error")
    @Expose
    public String error;

    @SerializedName("my_id")
    @Expose
    public int myId;

    public AuthEntryApiResponse(String code, String error, int myId) {
        this.code = code;
        this.error = error;
        this.myId = myId;
    }

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

    public int getMyId() {
        return myId;
    }

    public void setMyId(int myId) {
        this.myId = myId;
    }
}


