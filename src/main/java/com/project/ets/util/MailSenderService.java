package com.project.ets.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class MailSenderService {
    private JavaMailSender javaMailSender;

    @Async
    public void sendMail(MessageModel messageModel)  {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
            messageHelper.setTo(messageModel.getTo());
            messageHelper.setSubject(messageModel.getSubject());
            messageHelper.setSentDate(messageModel.getSendDate());
            messageHelper.setText(messageModel.getText(), true);
            javaMailSender.send(mimeMessage);
        }
        catch(MessagingException e){
            log.info("failed to send the mail");
        }
    }
}
