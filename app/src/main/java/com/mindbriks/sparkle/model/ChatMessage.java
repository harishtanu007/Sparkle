package com.mindbriks.sparkle.model;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private boolean messageUser;
    private long messageTime;
    private String senderID;

    public ChatMessage(String messageText, boolean messageUser, Date date, String senderID) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = date.getTime();
        this.senderID = senderID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public boolean getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(boolean messageUser) {
        this.messageUser = messageUser;
    }

    public String getMessageTime() {
        return "10:40 PM";
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }
}
