package com.agency04.heist.event;

public class SendEmailEvent {

    private String recipientAddress;
    private String subject;
    private String message;

    public SendEmailEvent(String recipientAddress, String subject, String message) {
        this.recipientAddress = recipientAddress;
        this.subject = subject;
        this.message = message;
    }

    public String getRecipientAddresses() {
        return recipientAddress;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }
}
