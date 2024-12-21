package com.app.models;

import com.google.gson.annotations.SerializedName;

public class Entrave {

    // Attributs
    @SerializedName("_id")
    private int _id;
    @SerializedName("id_request")
    private String id_request;
    private String streetid;
    private String streetimpactwidth;
    private String streetimpacttype;
    private String nbfreeparkingplace;
    private String sidewalk_blockedtype;
    private String backsidewalk_blockedtype;
    private String bikepath_blockedtype;
    private String name;
    private String shortname;
    private String fromname;
    private String fromshortname;
    private String toname;
    private String toshortname;
    private String length;
    private String isarterial;
    private String stmimpact_blockedtype;
    private String otherproviderimpact_blockedtype;
    private String reservedlane_blockedtype;


    // Getters

    public int get_id() {
        return _id;
    }

    public String getId_request() {
        return id_request;
    }

    public String getStreetid() {
        return streetid;
    }

    public String getStreetimpactwidth() {
        return streetimpactwidth;
    }

    public String getStreetimpacttype() {
        return streetimpacttype;
    }

    public String getNbfreeparkingplace() {
        return nbfreeparkingplace;
    }

    public String getSidewalk_blockedtype() {
        return sidewalk_blockedtype;
    }

    public String getBacksidewalk_blockedtype() {
        return backsidewalk_blockedtype;
    }

    public String getBikepath_blockedtype() {
        return bikepath_blockedtype;
    }

    public String getName() {
        return name;
    }

    public String getShortname() {
        return shortname;
    }

    public String getFromname() {
        return fromname;
    }

    public String getFromshortname() {
        return fromshortname;
    }

    public String getToname() {
        return toname;
    }

    public String getToshortname() {
        return toshortname;
    }

    public String getLength() {
        return length;
    }

    public String getIsarterial() {
        return isarterial;
    }

    public String getStmimpact_blockedtype() {
        return stmimpact_blockedtype;
    }

    public String getOtherproviderimpact_blockedtype() {
        return otherproviderimpact_blockedtype;
    }

    public String getReservedlane_blockedtype() {
        return reservedlane_blockedtype;
    }

}
