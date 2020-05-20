package com.example.apnabazaar.models;

import java.io.Serializable;

public class Post implements Serializable {


        private String pId, pTitle, pDesc, pMinPrice, puphone ,pQuantity,aDateTime ,pucity, upiid, pCity, pustate, pupincode, pCatogry, pImage, pTime, pduration , uid, uEmail, uName, uDp, Count;



    public Post() {
    }


    // create or add the pQuantity constructor below


    public Post(String pId, String pTitle, String pDesc, String pMinPrice, String puphone, String pQuantity, String aDateTime, String pucity, String upiid, String pCity, String pustate, String pupincode, String pCatogry, String pImage, String pTime, String pduration, String uid, String uEmail, String uName, String uDp, String count) {
        this.pId = pId;
        this.pTitle = pTitle;
        this.pDesc = pDesc;
        this.pMinPrice = pMinPrice;
        this.puphone = puphone;
        this.pQuantity = pQuantity;
        this.aDateTime = aDateTime;
        this.pucity = pucity;
        this.upiid = upiid;
        this.pCity = pCity;
        this.pustate = pustate;
        this.pupincode = pupincode;
        this.pCatogry = pCatogry;
        this.pImage = pImage;
        this.pTime = pTime;
        this.pduration = pduration;
        this.uid = uid;
        this.uEmail = uEmail;
        this.uName = uName;
        this.uDp = uDp;
        Count = count;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpTitle() {
        return pTitle;
    }

    public void setpTitle(String pTitle) {
        this.pTitle = pTitle;
    }

    public String getpDesc() {
        return pDesc;
    }

    public void setpDesc(String pDesc) {
        this.pDesc = pDesc;
    }

    public String getpMinPrice() {
        return pMinPrice;
    }

    public void setpMinPrice(String pMinPrice) {
        this.pMinPrice = pMinPrice;
    }

    public String getPuphone() {
        return puphone;
    }

    public void setPuphone(String puphone) {
        this.puphone = puphone;
    }

    public String getpQuantity() {
        return pQuantity;
    }

    public void setpQuantity(String pQuantity) {
        this.pQuantity = pQuantity;
    }

    public String getaDateTime() {
        return aDateTime;
    }

    public void setaDateTime(String aDateTime) {
        this.aDateTime = aDateTime;
    }

    public String getPucity() {
        return pucity;
    }

    public void setPucity(String pucity) {
        this.pucity = pucity;
    }

    public String getUpiid() {
        return upiid;
    }

    public void setUpiid(String upiid) {
        this.upiid = upiid;
    }

    public String getpCity() {
        return pCity;
    }

    public void setpCity(String pCity) {
        this.pCity = pCity;
    }

    public String getPustate() {
        return pustate;
    }

    public void setPustate(String pustate) {
        this.pustate = pustate;
    }

    public String getPupincode() {
        return pupincode;
    }

    public void setPupincode(String pupincode) {
        this.pupincode = pupincode;
    }

    public String getpCatogry() {
        return pCatogry;
    }

    public void setpCatogry(String pCatogry) {
        this.pCatogry = pCatogry;
    }

    public String getpImage() {
        return pImage;
    }

    public void setpImage(String pImage) {
        this.pImage = pImage;
    }

    public String getpTime() {
        return pTime;
    }

    public void setpTime(String pTime) {
        this.pTime = pTime;
    }

    public String getPduration() {
        return pduration;
    }

    public void setPduration(String pduration) {
        this.pduration = pduration;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuDp() {
        return uDp;
    }

    public void setuDp(String uDp) {
        this.uDp = uDp;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }
}
