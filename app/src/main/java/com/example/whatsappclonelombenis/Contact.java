package com.example.whatsappclonelombenis;

import java.util.ArrayList;
import java.util.Calendar;

public class Contact {
    private String profilePicture;
    private String name;
    private int telNumber;
    private String infoText;
    private ArrayList<String> status;
    private String message;
    private Calendar date;
    private boolean isArchived;

    public Contact(String profilePicture, String name, int telNumber, String infoText, ArrayList<String> status) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.telNumber = telNumber;
        this.infoText = infoText;
        this.status = status;
    }

    public Contact(String profilePicture, String name, String message) {
        this.profilePicture=profilePicture;
        this.name=name;
        this.message=message;
    }

    public Contact(String profilePicture, String name, String message, Calendar date, boolean isArchived) {
        this.profilePicture=profilePicture;
        this.name=name;
        this.message=message;
        this.date=date;
        this.isArchived=isArchived;
    }

    public Contact(String profilePicture, String name) {
        this.profilePicture=profilePicture;
        this.name=name;
    }

    public Contact(String profilePicture, String name, ArrayList<String> status) {
        this.profilePicture = profilePicture;
        this.name = name;
        this.status = status;
    }


    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public void setArchived(boolean archived) {
        isArchived = archived;
    }

    public String getMessage() {
        return message;
    }

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

    public ArrayList<String> getStatus() {
        return status;
    }

    public void setStatus(ArrayList<String> status) {
        this.status = status;
    }
}
