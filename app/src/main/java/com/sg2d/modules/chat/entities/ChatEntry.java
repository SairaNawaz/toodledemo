package com.sg2d.modules.chat.entities;

import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatEntry implements Comparable<ChatEntry> {

    public int msg_id;
    public int userid;
    public int recipientid;
    public String timestamp;
    public String body;
    public int status;
    public String type;
    public boolean isSynced;
    public boolean isError;

    public ChatEntry(int msg_id, int userid, int recipientid,
                     String timestamp, String body, int status,
                     String type, boolean isSynced, boolean isError) {
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

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public int getUserid() {
        return userid;
    }

    public boolean isSynced() {
        return isSynced;
    }

    public void setSynced(boolean synced) {
        isSynced = synced;
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

    public String getFormatedTime() {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        try {
            date = format1.parse(timestamp);
            format2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBody() {
        return body;
    }

    public FileBody getFileBody() {
        return new Gson().fromJson(body, FileBody.class);
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

    @Override
    public int compareTo(ChatEntry o) {
        return 0;
    }

    public ChatEntry copyWithError(ChatEntry chatEntry, boolean isError) {
        ChatEntry obj = new ChatEntry(chatEntry.getMsg_id(), chatEntry.getUserid(), chatEntry.getRecipientid(), chatEntry.getTimestamp(),
                chatEntry.getBody(), chatEntry.getStatus(), chatEntry.getType(), chatEntry.isSynced(), isError);
        return obj;
    }
}
