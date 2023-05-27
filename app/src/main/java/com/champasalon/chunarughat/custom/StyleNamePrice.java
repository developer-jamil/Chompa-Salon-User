package com.champasalon.chunarughat.custom;

public class StyleNamePrice {
    //empty constructor for firebase
    public StyleNamePrice(){}

    public String styleName;
    public String stylePrice;

    //constructor
    public StyleNamePrice(String styleName, String stylePrice) {
        this.styleName = styleName;
        this.stylePrice = stylePrice;
    }

    //getter and setter
    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getStylePrice() {
        return stylePrice;
    }

    public void setStylePrice(String stylePrice) {
        this.stylePrice = stylePrice;
    }
}
