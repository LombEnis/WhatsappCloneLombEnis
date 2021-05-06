package com.example.whatsappclonelombenis;

import java.util.Date;

public class Story {

    private Date date;
    private int backgroundColor;
    private String backgroundImage;
    private String mainTextString;
    private int mainTextColor;
    private String captionTextString;

    // Main constructor
    public Story(int backgroundColor, String backgroundImage, String mainTextString, int mainTextColor, String captionTextString) {
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
        this.mainTextColor = mainTextColor;
    }

    // Getter and setter
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getMainTextString() {
        return mainTextString;
    }

    public void setMainTextString(String mainTextString) {
        this.mainTextString = mainTextString;
    }

    public String getCaptionTextString() {
        return captionTextString;
    }

    public void setCaptionTextString(String captionTextString) {
        this.captionTextString = captionTextString;
    }

    public int getMainTextColor() {
        return mainTextColor;
    }

    public void setMainTextColor(int mainTextColor) {
        this.mainTextColor = mainTextColor;
    }
}
