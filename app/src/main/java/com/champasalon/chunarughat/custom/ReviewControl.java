package com.champasalon.chunarughat.custom;
public class ReviewControl {

    public ReviewControl() {};

    public String modifiedEmail;
    public String userReview;


    public ReviewControl(String modifiedEmail, String userReview) {
        this.modifiedEmail = modifiedEmail;
        this.userReview = userReview;
    }

    public String getModifiedEmail() {
        return modifiedEmail;
    }

    public void setModifiedEmail(String modifiedEmail) {
        this.modifiedEmail = modifiedEmail;
    }

    public String getUserReview() {
        return userReview;
    }

    public void setUserReview(String userReview) {
        this.userReview = userReview;
    }
}