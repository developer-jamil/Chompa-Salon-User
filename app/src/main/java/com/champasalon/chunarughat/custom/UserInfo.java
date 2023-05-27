package com.champasalon.chunarughat.custom;

public class UserInfo {

    public  String userName, userEmail, userMobile, userGender, userDOB, userProfileBudgetStatus, profile_pic_link, userCode, userToken;

    //Empty construction for firebase
    public UserInfo(){};

    //construction
    public UserInfo(String userName, String userEmail, String userMobile, String userGender, String userDOB, String userProfileBudgetStatus, String profile_pic_link, String userCode, String userToken) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userMobile = userMobile;
        this.userGender = userGender;
        this.userDOB = userDOB;
        this.userProfileBudgetStatus = userProfileBudgetStatus;
        this.profile_pic_link = profile_pic_link;
        this.userCode = userCode;
        this.userToken = userToken;
    }
}
