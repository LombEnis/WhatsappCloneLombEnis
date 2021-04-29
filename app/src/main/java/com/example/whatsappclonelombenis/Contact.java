package com.example.whatsappclonelombenis;

import java.net.URL;
import java.util.ArrayList;

public class Contact {
    private URL profilePicture;
    private String name;
    private int telNumber;
    private String infoText;
    private ArrayList<URL> status;

    public Contact(URL profilePicture, String name, int telNumber, String infoText, ArrayList<URL> status) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.telNumber = telNumber;
        this.infoText = infoText;
        this.status = status;
    }

    public Contact(URL profilePicture, String name, ArrayList<URL> status) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.status = status;
    }

    public URL getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(URL profilePicture) {
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

    public ArrayList<URL> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<URL> status) {
        this.status = status;
    }
}
