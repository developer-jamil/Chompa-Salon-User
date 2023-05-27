package com.champasalon.chunarughat.custom;

public class CustomAdapter {

    //empty construction
    public CustomAdapter(){}


    //for name save
    public String namePerson;

    //serial number
    public String position;

    //construction
    public CustomAdapter(String namePerson, String position) {
        this.namePerson = namePerson;
        this.position = position;
    }

    //getter and setter for name
    public String getNamePerson() {
        return namePerson;
    }


    //position - getter and setter
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }



}
