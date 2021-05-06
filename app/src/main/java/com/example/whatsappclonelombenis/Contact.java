package com.example.whatsappclonelombenis;

import java.net.URL;
import java.util.ArrayList;

public class Contact {
    private String name;
    private int telNumber;
    private String profilePicture;
    private String infoText;
    private ArrayList<Story> status;

    // Main constructor
    public Contact(String name, int telNumber, String profilePicture, String infoText, ArrayList<Story> status) {
        this.name = name;
        this.telNumber = telNumber;
        this.profilePicture = profilePicture;
        this.infoText = infoText;
        this.status = status;
    }

    // Testing constructor
    public Contact(String name, String profilePicture, ArrayList<Story> status) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.status = status;
    }

    // Getter and Setter
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(int telNumber) {
        this.telNumber = telNumber;
    }

    public String getInfoText() {
        return infoText;
    }

    public void setInfoText(String infoText) {
        this.infoText = infoText;
    }

    public ArrayList<Story> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<Story> status) {
        this.status = status;
    }
}
