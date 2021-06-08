package com.example.whatsappclonelombenis;

import java.util.Calendar;

public class Call {
    private Contact contact;
    private Calendar date;
    private boolean callAccepted, isIncomingCall, isVoiceCall;

    public Call(Contact contact, Calendar date, boolean callAccepted, boolean incomingCall, boolean isVoiceCall) {
        this.contact = contact;
        this.date = date;
        this.callAccepted = callAccepted;
        this.isIncomingCall = incomingCall;
        this.isVoiceCall = isVoiceCall;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public boolean isCallAccepted() {
        return callAccepted;
    }

    public void setCallAccepted(boolean callAccepted) {
        this.callAccepted = callAccepted;
    }

    public boolean isIncomingCall() {
        return isIncomingCall;
    }

    public void setIncomingCall(boolean incomingCall) {
        this.isIncomingCall = incomingCall;
    }

    public boolean isVoiceCall() {
        return isVoiceCall;
    }

    public void setVoiceCall(boolean voiceCall) {
        isVoiceCall = voiceCall;
    }
}
