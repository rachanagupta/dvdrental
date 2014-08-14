package com.dvd.rental.capstone.business.service.impl;

import java.util.LinkedHashMap;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * This send mail to the user. Memcache can be used to prevent the multiple
 * people being send mail. once the mail is send, put it in the cache for
 * atleast 1 min. Every time you send the mail, check if the user is in
 * memcache. If it is there, don't send second mail.
 * </p>
 * 
 * @author rachana
 * @since Aug 10, 2014
 * 
 */
@Component
public class EmailManager {
    /**
     * account of the user from where the emails are send
     */
    @Value("${email.username:testemail1180@gmail.com}")
    private String username;
    /**
     * password - Note : it should be encryped
     */
    @Value("${email.password:testing1180}")
    private String password;
    /**
     * Smtp email auth
     */
    @Value("${email.smtp.auth:true}")
    private String auth;
    /**
     * Smtp email starttls
     */
    @Value("${email.smtp.starttls.enable:true}")
    private String starttlsEnable;
    /**
     * Smtp email host
     */
    @Value("$email.host:smtp.gmail.com")
    private String host;
    /**
     * Smtp email port
     */
    @Value("$email.port:587")
    private String port;

    private Properties props = null;

    private Session session = null;
    /**
     * In-momory LRU caching
     */
    private LinkedHashMap<String, Boolean> cache = null;

    private static final String FROM_EMAIL_ID = "donotreply@gmail.com";

    @PostConstruct
    public void init() {
        // setting up the smtp
        props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // setting up authorization
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        // initializing cache
        cache = new LinkedHashMap<String, Boolean>(50, 0.75f, true);
    }

    /**
     * Method to send email
     * 
     * @param toEmailId
     *            - Person to whom the email is to be send
     * @param subject
     *            - Subject of the mail
     * @param body
     *            - Body of the mail
     */
    public void send(String toEmailId, String subject, String body) {
        if (cache.containsKey(toEmailId)) {
            // Already send the mail, doesn't need to throw exception
            return;
        }
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ID));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toEmailId));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            // Add to cache to prevent multiple mails to be send to the same
            // user
            cache.put(toEmailId, true);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
            //OR log and eat the exception if you dont want to hault the process
        }
    }
}
