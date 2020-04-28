package com.example.apnabazaar.models;

public class Won {

    String uid, uName, uEmail, pId, Bids;

    public Won() {
    }

    public Won(String uid, String uName, String uEmail, String pId, String bids) {
        this.uid = uid;
        this.uName = uName;
        this.uEmail = uEmail;
        this.pId = pId;
        Bids = bids;
    }

    public Won(String getuName, String getpId) {


    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getBids() {
        return Bids;
    }

    public void setBids(String bids) {
        Bids = bids;
    }
}
