package com.project.ets.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailSender {
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(MessageModel messageModel) throws MessagingException {
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
        messageHelper.setTo(messageModel.getTo());
        messageHelper.setSubject(messageModel.getSubject());
        messageHelper.setSentDate(messageModel.getSendDate());
        messageHelper.setText(messageModel.getText(),true);
        javaMailSender.send(mimeMessage);
    }
}
