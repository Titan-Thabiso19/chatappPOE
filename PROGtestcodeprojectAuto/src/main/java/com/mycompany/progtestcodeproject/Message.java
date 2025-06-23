package com.mycompany.progtestcodeproject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String sender;
    private String recipientName;
    private String recipientNumber;
    private String content;
    private String messageID;
    private String timestamp;

    public Message(String senderName, String senderSurname, String recipientName, String recipientNumber, String content) {
        this.sender = senderName + " " + senderSurname;
        this.recipientName = recipientName;
        this.recipientNumber = recipientNumber;
        this.content = content;
        this.messageID = generateMessageID(senderName, content);
        this.timestamp = getCurrentTimestamp();
    }

    // === Message ID generation using string manipulation ===
    private String generateMessageID(String senderName, String content) {
        String initials = senderName.length() >= 2 ? senderName.substring(0, 2).toUpperCase() : senderName.toUpperCase();
        int hash = Math.abs((content + System.currentTimeMillis()).hashCode());
        return "MSG" + initials + String.format("%04d", hash % 10000);
    }

    // === Timestamp ===
    private String getCurrentTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    // === Getters ===
    public String getSender() {
        return sender;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public String getRecipientNumber() {
        return recipientNumber;
    }

    public String getContent() {
        return content;
    }

    public String getMessageID() {
        return messageID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[SENT] Message ID: " + messageID + "\nTo: " + recipientName + " (" + recipientNumber + ")\nFrom: " + sender +
                "\nTime: " + timestamp + "\nMessage: " + content;
    }
}

