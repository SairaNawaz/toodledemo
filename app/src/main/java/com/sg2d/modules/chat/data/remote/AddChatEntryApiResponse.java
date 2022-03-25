package com.sg2d.modules.chat.data.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AddChatEntryApiResponse {

    @SerializedName("id")
    @Expose
    public int msg_id;

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
}


