package com.Eteryz.ForumBackend.service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

    void sendMessageWithAttachment(
            String to,
            String subject,
            String text,
            String pathToAttachment
    ) throws MessagingException, UnsupportedEncodingException;
}
