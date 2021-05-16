package com.example.whatsappclonelombenis;

public class Call {
    private Contact contact;
    private String callTime, callDay;
    private boolean callAccepted, isIncomingCall, isVoiceCall;

    public Call(Contact contact, String callDay, String callTime, boolean callAccepted, boolean incomingCall, boolean isVoiceCall) {
        this.contact = contact;
        this.callTime = callTime;
        this.callAccepted = callAccepted;
        this.isIncomingCall = incomingCall;
        this.isVoiceCall= isVoiceCall;
        this.callDay= callDay;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getCallDay() {
        return callDay;
    }

    public void setCallDay(String callDay) {
        this.callDay = callDay;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
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