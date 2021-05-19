package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;

import java.util.Calendar;

public class Story {
    private Calendar date;
    private int backgroundColorResource;
    private String backgroundImageUrlString;
    private String mainTextString;
    private String captionTextString;

    private Bitmap storyPreviewBitmap;
    private StoriesActivity.StoryProgressBar progressBar = null;

    private boolean seen;

    // Main constructor
    public Story(Calendar date, int backgroundColorResource, String backgroundImageString, String mainTextString, String captionTextString, Bitmap storyPreviewBitmap) {
        this.date = date;
        this.backgroundColorResource = backgroundColorResource;
        this.backgroundImageUrlString = backgroundImageString;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
        this.storyPreviewBitmap = storyPreviewBitmap;
        this.seen = false;
    }

    // Getter and setter
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
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

    public StoriesActivity.StoryProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(StoriesActivity.StoryProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public boolean isSeen() {
        return this.seen;
    }
}
