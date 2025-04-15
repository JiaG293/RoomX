package com.roomx.infrastructure.notification;

import com.roomx.shared.enums.EmailTemplateType;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String emailFrom;


    public void sendHtmlEmail(String to, String subject, EmailTemplateType templateName, Map<String, Object> params) {
        try {

            Context context = new Context();
            context.setVariables(params);

            String body = templateEngine.process(templateName.toString(), context);


            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);


            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);


            helper.setFrom(emailFrom);


            mailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error("Error sending email", e);
            e.printStackTrace();
        }
    }

    public void sendHtmlEmailToMultipleRecipients(List<String> to, String subject, String templateName, Map<String, Object> params) {
        try {

            Context context = new Context();
            context.setVariables(params);


            String body = templateEngine.process(templateName, context);


            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);


            helper.setTo(to.toArray(new String[0]));
            helper.setSubject(subject);
            helper.setText(body, true);


            helper.setFrom(emailFrom);


            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Error sending email to multiple recipients", e);
            e.printStackTrace();
        }
    }

}

