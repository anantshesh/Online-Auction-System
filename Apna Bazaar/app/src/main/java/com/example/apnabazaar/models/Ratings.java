package com.example.apnabazaar.models;

public class Ratings {

    String ratingId, UserIdRated, pid, pUid, pUname;

    int Rating;

    public Ratings() {
    }

    public Ratings(String ratingId, String userIdRated, String pid, String pUid, String pUname, int rating) {
        this.ratingId = ratingId;
        UserIdRated = userIdRated;
        this.pid = pid;
        this.pUid = pUid;
        this.pUname = pUname;
        Rating = rating;
    }

    public String getRatingId() {
        return ratingId;
    }

    public void setRatingId(String ratingId) {
        this.ratingId = ratingId;
    }

    public String getUserIdRated() {
        return UserIdRated;
    }

    public void setUserIdRated(String userIdRated) {
        UserIdRated = userIdRated;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getpUid() {
        return pUid;
    }

    public void setpUid(String pUid) {
        this.pUid = pUid;
    }

    public String getpUname() {
        return pUname;
    }

    public void setpUname(String pUname) {
        this.pUname = pUname;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }
}
