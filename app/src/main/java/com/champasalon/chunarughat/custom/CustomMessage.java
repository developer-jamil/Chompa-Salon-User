package com.champasalon.chunarughat.custom;

public class CustomMessage {

    //empty constructor
    public CustomMessage(){}

    String name;
    String subject;
    String message;
    String uid;

    //constructor
    public CustomMessage(String name, String subject, String message, String uid) {
        this.name = name;
        this.subject = subject;
        this.message = message;
        this.uid = uid;
    }


    //getter and setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
