package com.sg2d.modules.chat.entities;

public class FileBody {
    String path;
    String name;
    String tempPath;

    public FileBody(String path, String name, String tempPath) {
        this.path = path;
        this.name = name;
        this.tempPath = tempPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
