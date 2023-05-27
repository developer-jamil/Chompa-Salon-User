package com.champasalon.chunarughat.custom;

public class ReadWriteUserDetails {

    //Empty cons
    public ReadWriteUserDetails(){};

    public  String fullName, email, mobile, dob, gender;

    //construction
    public ReadWriteUserDetails(String fullName, String email, String mobile, String dob, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.dob = dob;
        this.gender = gender;

    }
}
