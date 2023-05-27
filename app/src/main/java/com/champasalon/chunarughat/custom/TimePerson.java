package com.champasalon.chunarughat.custom;

public class TimePerson {

    public TimePerson(){}

    public String timeM;
    public String personM;


    public TimePerson(String timeM, String personM) {
        this.timeM = timeM;
        this.personM = personM;
    }

    public String getTimeM() {
        return timeM;
    }

    public void setTimeM(String timeM) {
        this.timeM = timeM;
    }

    public String getPersonM() {
        return personM;
    }

    public void setPersonM(String personM) {
        this.personM = personM;
    }
}
