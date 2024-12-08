package com.app.models;

import org.bson.Document;

public class Notification {
    private String msg;
    private String id;
    private boolean vu;

    // Constructeur par défaut
    public Notification() {
    }

    // Constructeur
    public Notification(String id, String msg, boolean vu) {
        this.id = id;
        this.msg = msg;
        this.vu = vu;
    }

    // Méthode de conversion à partir d'un Document
    public static Notification fromDocument(Document document) {
        String id = document.getString("id");
        String msg = document.getString("msg");
        boolean vu = document.getBoolean("vu");

        // Créer une instance de Notification
        return new Notification(id, msg, vu);
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
