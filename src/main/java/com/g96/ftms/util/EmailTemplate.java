package com.g96.ftms.util;

public class EmailTemplate {
    public static String generateVerificationEmailTemplate(String verificationCode, String senderEmail) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<h2>Your Verification Code</h2>" +
                "<p>Your verification code is:</p>" +
                "<h3 style='color:blue;'>" + verificationCode + "</h3>" +
                "<p>You have 15 minutes to enter this code.</p>" +
                "<p>This email was sent from: <strong>" + senderEmail + "</strong></p>" +
                "</body>" +
                "</html>";
    }
}
