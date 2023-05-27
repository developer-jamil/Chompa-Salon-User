package com.champasalon.chunarughat.custom;

public class Custom_Position {

    //empty construction
    public Custom_Position(){}

    //type sent here and re-sent
    public static String chooseType;

    //serial number confirmed
    public String userId;
    public String position;
    public String userType;

    //construction
    public Custom_Position(String userId, String position, String userType) {
        this.userId = userId;
        this.position = position;
        this.userType = userType;
    }


    //position - getter and setter
    public static String getChooseType() {
        return chooseType;
    }

    public static void setChooseType(String chooseType) {
        Custom_Position.chooseType = chooseType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
