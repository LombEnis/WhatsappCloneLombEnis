package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;

import java.util.Date;

public class Story {
    private Date date;
    private int backgroundColorResource;
    private String backgroundImageUrlString;
    private String mainTextString;
    private String captionTextString;

    private Bitmap storyPreviewBitmap;

    // Main constructor
    public Story(Date date, int backgroundColorResource, String backgroundImageString, String mainTextString, String captionTextString, Bitmap storyPreviewBitmap) {
        this.date = date;
        this.backgroundColorResource = backgroundColorResource;
        this.backgroundImageUrlString = backgroundImageString;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
        this.storyPreviewBitmap = storyPreviewBitmap;
    }

    // Getter and setter
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getBackgroundColorResource() {
        return backgroundColorResource;
    }

    public void setBackgroundColorResource(int backgroundColorResource) {
        this.backgroundColorResource = backgroundColorResource;
    }

    public String getBackgroundImageString() {
        return backgroundImageUrlString;
    }

    public void setBackgroundImageString(String backgroundImageString) {
        this.backgroundImageUrlString = backgroundImageString;
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

    public Bitmap getStoryPreviewBitmap() {
        return storyPreviewBitmap;
    }

    public void setStoryPreviewBitmap(Bitmap storyPreviewBitmap) {
        this.storyPreviewBitmap = storyPreviewBitmap;
    }
}