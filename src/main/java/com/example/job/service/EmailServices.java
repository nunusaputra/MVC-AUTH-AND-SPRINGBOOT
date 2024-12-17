package com.example.job.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServices {

    @Autowired
    private JavaMailSender javaMailSender;

    private String from = "nunusaputra17@gmail.com";

    public boolean sendMail(String to, String subject, String body) {
        try {
            String timeStampCode = getTimeStampCode();

            String subjetMail = subject + " - " + timeStampCode;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subjetMail);
            message.setText(body);
            message.setFrom(from);

            javaMailSender.send(message);
            System.out.println("Success send email to: " + to);

            return true;
        } catch (Exception e) {
            System.out.println("Failed send email: " + e.getMessage());
            return false;
        }
    }

    public String getTimeStampCode() {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
        return "TS-" + LocalDateTime.now().format(formater);
    }
}
