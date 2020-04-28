package com.example.apnabazaar.models;

import java.io.Serializable;

public class Bids implements Serializable {

    String Bids, baDateTime, bidId, bpCity, bpesc, bpId, bpImage, bpMinPrice, bpQuantity, bpTime, bpTitle, bpduration, bphone, bpuDp, bpuEmail, bpuName, bpuid, buEmail, buName, buid,timestamp;


    public Bids() {
    }

    public Bids(String bids, String baDateTime, String bidId, String bpCity, String bpesc, String bpId, String bpImage, String bpMinPrice, String bpQuantity, String bpTime, String bpTitle, String bpduration, String bphone, String bpuDp, String bpuEmail, String bpuName, String bpuid, String buEmail, String buName, String buid, String timestamp) {
        Bids = bids;
        this.baDateTime = baDateTime;
        this.bidId = bidId;
        this.bpCity = bpCity;
        this.bpesc = bpesc;
        this.bpId = bpId;
        this.bpImage = bpImage;
        this.bpMinPrice = bpMinPrice;
        this.bpQuantity = bpQuantity;
        this.bpTime = bpTime;
        this.bpTitle = bpTitle;
        this.bpduration = bpduration;
        this.bphone = bphone;
        this.bpuDp = bpuDp;
        this.bpuEmail = bpuEmail;
        this.bpuName = bpuName;
        this.bpuid = bpuid;
        this.buEmail = buEmail;
        this.buName = buName;
        this.buid = buid;
        this.timestamp = timestamp;
    }

    public String getBids() {
        return Bids;
    }

    public void setBids(String bids) {
        Bids = bids;
    }

    public String getBaDateTime() {
        return baDateTime;
    }

    public void setBaDateTime(String baDateTime) {
        this.baDateTime = baDateTime;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getBpCity() {
        return bpCity;
    }

    public void setBpCity(String bpCity) {
        this.bpCity = bpCity;
    }

    public String getBpesc() {
        return bpesc;
    }

    public void setBpesc(String bpesc) {
        this.bpesc = bpesc;
    }

    public String getBpId() {
        return bpId;
    }

    public void setBpId(String bpId) {
        this.bpId = bpId;
    }

    public String getBpImage() {
        return bpImage;
    }

    public void setBpImage(String bpImage) {
        this.bpImage = bpImage;
    }

    public String getBpMinPrice() {
        return bpMinPrice;
    }

    public void setBpMinPrice(String bpMinPrice) {
        this.bpMinPrice = bpMinPrice;
    }

    public String getBpQuantity() {
        return bpQuantity;
    }

    public void setBpQuantity(String bpQuantity) {
        this.bpQuantity = bpQuantity;
    }

    public String getBpTime() {
        return bpTime;
    }

    public void setBpTime(String bpTime) {
        this.bpTime = bpTime;
    }

    public String getBpTitle() {
        return bpTitle;
    }

    public void setBpTitle(String bpTitle) {
        this.bpTitle = bpTitle;
    }

    public String getBpduration() {
        return bpduration;
    }

    public void setBpduration(String bpduration) {
        this.bpduration = bpduration;
    }

    public String getBphone() {
        return bphone;
    }

    public void setBphone(String bphone) {
        this.bphone = bphone;
    }

    public String getBpuDp() {
        return bpuDp;
    }

    public void setBpuDp(String bpuDp) {
        this.bpuDp = bpuDp;
    }

    public String getBpuEmail() {
        return bpuEmail;
    }

    public void setBpuEmail(String bpuEmail) {
        this.bpuEmail = bpuEmail;
    }

    public String getBpuName() {
        return bpuName;
    }

    public void setBpuName(String bpuName) {
        this.bpuName = bpuName;
    }

    public String getBpuid() {
        return bpuid;
    }

    public void setBpuid(String bpuid) {
        this.bpuid = bpuid;
    }

    public String getBuEmail() {
        return buEmail;
    }

    public void setBuEmail(String buEmail) {
        this.buEmail = buEmail;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public String getBuid() {
        return buid;
    }

    public void setBuid(String buid) {
        this.buid = buid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
