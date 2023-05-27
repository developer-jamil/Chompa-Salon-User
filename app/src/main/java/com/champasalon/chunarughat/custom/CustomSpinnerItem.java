package com.champasalon.chunarughat.custom;

public class CustomSpinnerItem {
    public String spinnerItemName;
    public int spinnerItemImage;

    //constructor
    public CustomSpinnerItem(String spinnerItemName, int spinnerItemImage) {
        this.spinnerItemName = spinnerItemName;
        this.spinnerItemImage = spinnerItemImage;
    }

    //getter
    public String getSpinnerItemName() {
        return spinnerItemName;
    }

    public int getSpinnerItemImage() {
        return spinnerItemImage;
    }
}