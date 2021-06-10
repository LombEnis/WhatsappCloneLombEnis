package com.example.whatsappclonelombenis;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;

public class Story {
    // Date
    private Calendar date;
    // Layout
    private Bitmap storyImageBitmap;
    private String captionTextString;
    // Views
    private ArrayList<Object[]> viewsContacts;

    private Bitmap storyPreviewBitmap;
    private StatusActivity.StoryProgressBar progressBar = null;

    private boolean isSeen;

    // Story constructor
    public Story(Calendar date, Bitmap storyImageBitmap, String captionTextString, Bitmap storyPreviewBitmap) {
        this.date = date;
        this.storyImageBitmap = storyImageBitmap;
        this.captionTextString = captionTextString;
        this.storyPreviewBitmap = storyPreviewBitmap;

        this.isSeen = false;
    }

    // My story constructor
    public Story(Calendar date, Bitmap storyImageBitmap, String captionTextString, Bitmap storyPreviewBitmap, ArrayList<Object[]> viewsContacts) {
        this.date = date;
        this.storyImageBitmap = storyImageBitmap;
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

    public Bitmap getStoryImageBitmap() {
        return storyImageBitmap;
    }

    public void setStoryImageBitmap(Bitmap storyImageBitmap) {
        this.storyImageBitmap = storyImageBitmap;
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

    public ArrayList<Object[]> getViewsContacts() {
        return viewsContacts;
    }

    public void addViewsContact(Contact viewsContact, Calendar viewsDate) {
        Object[] viewsContactItem = {viewsContact, viewsDate};
        this.viewsContacts.add(viewsContactItem);
    }
}
