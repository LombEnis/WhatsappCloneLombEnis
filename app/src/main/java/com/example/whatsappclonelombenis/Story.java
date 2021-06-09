package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;

public class Story {
    // Date
    private Calendar date;
    // Layout
    private int backgroundColorResource;
    private String storyImageUrlString;
    private String mainTextString;
    private String captionTextString;
    // Views
    private ArrayList<Object[]> viewsContacts;

    private Bitmap storyPreviewBitmap;
    private StatusActivity.StoryProgressBar progressBar = null;

    private boolean isSeen;

    // Story constructor
    public Story(Calendar date, int backgroundColorResource, String backgroundImageString, String mainTextString, String captionTextString, Bitmap storyPreviewBitmap) {
        this.date = date;
        this.backgroundColorResource = backgroundColorResource;
        this.storyImageUrlString = backgroundImageString;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
        this.storyPreviewBitmap = storyPreviewBitmap;

        this.isSeen = false;
    }

    // My story constructor
    public Story(Calendar date, int backgroundColorResource, String backgroundImageString, String mainTextString, String captionTextString, Bitmap storyPreviewBitmap, ArrayList<Object[]> viewsContacts) {
        this.date = date;
        this.backgroundColorResource = backgroundColorResource;
        this.storyImageUrlString = backgroundImageString;
        this.mainTextString = mainTextString;
        this.captionTextString = captionTextString;
        this.storyPreviewBitmap = storyPreviewBitmap;
        this.viewsContacts = viewsContacts;
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

    public String getStoryImageUrlString() {
        return storyImageUrlString;
    }

    public void setStoryImageUrlString(String storyImageUrlString) {
        this.storyImageUrlString = storyImageUrlString;
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

    public StatusActivity.StoryProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(StatusActivity.StoryProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public void setSeen(boolean seen) {
        this.isSeen = seen;
    }

    public boolean isSeen() {
        return this.isSeen;
    }

    public String getBackgroundImageUrlString() {
        return storyImageUrlString;
    }

    public void setBackgroundImageUrlString(String backgroundImageUrlString) {
        this.storyImageUrlString = backgroundImageUrlString;
    }

    public ArrayList<Object[]> getViewsContacts() {
        return viewsContacts;
    }

    public void addViewsContact(Contact viewsContact, Calendar viewsDate) {
        Object[] viewsContactItem = {viewsContact, viewsDate};
        this.viewsContacts.add(viewsContactItem);
    }
}
