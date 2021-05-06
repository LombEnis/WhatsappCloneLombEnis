package com.example.whatsappclonelombenis;

public class Story {

    private int backgroundColor;
    private String backgroundImage;
    private String mainTextString;
    private String captionTextString;

    // Main constructor
    public Story(int backgroundColor, String backgroundImage, String mainTextString, String captionTextString) {
        this.backgroundColor = backgroundColor;
        this.backgroundImage = backgroundImage;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
    }

    // Getter and setter
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
}
