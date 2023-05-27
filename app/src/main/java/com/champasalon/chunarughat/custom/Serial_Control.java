package com.champasalon.chunarughat.custom;

public class Serial_Control {

    //empty construction
    public Serial_Control(){}

    public String position, email, type;

    //type sent here and re-sent
    public static String chooseType;

    //construction
    public Serial_Control(String position, String email, String type) {
        this.position = position;
        this.email = email;
        this.type = type;
    }


    //position - getter and setter
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
