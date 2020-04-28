package com.example.apnabazaar.models;

public class Bid {

   String Bids, bidId, timestamp, uEmail, uid, uName, udp, pId , phone;

    public Bid() {
    }

    public Bid(String bids, String bidId, String timestamp, String uEmail, String uid, String uName, String udp, String pId, String phone) {
        Bids = bids;
        this.bidId = bidId;
        this.timestamp = timestamp;
        this.uEmail = uEmail;
        this.uid = uid;
        this.uName = uName;
        this.udp = udp;
        this.pId = pId;
        this.phone = phone;
    }

    public Bid(String getuName, String getpId) {

    }

    public String getBids() {
        return Bids;
    }

    public void setBids(String bids) {
        Bids = bids;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
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

    public String getUdp() {
        return udp;
    }

    public void setUdp(String udp) {
        this.udp = udp;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

