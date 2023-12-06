package com.bvrit.cierclibrarymanagementsystem.servicelayer;

public interface MailConfigurationService {
    public void mailSender(String senderEmail, String recipientEmail, String body, String subject);
}
