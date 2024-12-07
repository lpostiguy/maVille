package com.app.models;

public class Notification {
    private String msg;
    private String id;
    private boolean vu;

    // Constructeur
    public Notification(String msg, String id, boolean vu) {
        this.msg = msg;
        this.id = id;
        this.vu = vu;
    }

    // Getters
    public String getMsg() {
        return msg;
    }

    public String getId() {
        return id;
    }

    public boolean isVu() {
        return vu;
    }

    // Setters
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVu(boolean vu) {
        this.vu = vu;
    }
}
