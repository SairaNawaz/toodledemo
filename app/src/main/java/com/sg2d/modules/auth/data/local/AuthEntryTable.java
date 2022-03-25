package com.sg2d.modules.auth.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AuthEntryTable {

    @PrimaryKey
    public int id;
    public String code;
    public String error;
    public int myId;
    public String username;
    public String password;

    public AuthEntryTable(String code, String error, int myId, String username, String password) {
        this.code = code;
        this.error = error;
        this.myId = myId;
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
