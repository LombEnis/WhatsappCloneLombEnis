package com.example.whatsappclonelombenis;

import java.net.URL;
import java.util.ArrayList;

public class Contact {
    private String name;
    private String telNumber;
    private String profilePicture;
    private String infoText;

    private ArrayList<Story> statusStories;
    private int currentStoriesPos;
    private int lastStoriesPos;
    private boolean allStoriesSeen;
    private boolean isStatusDisabled;

    // Main constructor
    public Contact(String name, String telNumber, String profilePicture, String infoText, ArrayList<Story> statusStories) {
        this.name = name;
        this.telNumber = telNumber;
        this.profilePicture = profilePicture;
        this.infoText = infoText;
        this.statusStories = statusStories;
        this.currentStoriesPos = -1;
        this.lastStoriesPos = 0;
        allStoriesSeen = false;
        isStatusDisabled = false;
    }

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public ArrayList<Story> getStatusStories() {
        return statusStories;
    }

    public void setStatusStories(ArrayList<Story> statusStories) {
        this.statusStories = statusStories;
    }

    public int getCurrentStoriesPos() {
        return currentStoriesPos;
    }

    public void setCurrentStoriesPos(int currentStoriesPos) { this.currentStoriesPos = currentStoriesPos; }

    public void increaseCurrentStoriesPos() {
        this.currentStoriesPos++;
    }

    public void decreaseCurrentStoriesPos() {
        this.currentStoriesPos--;
    }

    public int getLastStoriesPos() {
        return lastStoriesPos;
    }

    public void setLastStoriesPos(int lastStoriesPos) {
        this.lastStoriesPos = lastStoriesPos;
    }

    public void increaseLastStoriesPos() {
        if (lastStoriesPos != (statusStories.size() - 1)) {
            lastStoriesPos++;
        } else {
            setAllStoriesSeen(true);
            setCurrentStoriesPos(0);
        }
    }

    public boolean isAllStoriesSeen() {
        return allStoriesSeen;
    }

    public void setAllStoriesSeen(boolean allStoriesSeen) {
        this.allStoriesSeen = allStoriesSeen;
    }

    public boolean isStatusDisabled() {
        return isStatusDisabled;
    }

    public void setStatusDisabled(boolean statusDisabled) {
        this.isStatusDisabled = statusDisabled;
        setLastStoriesPos(0);
    }
}
