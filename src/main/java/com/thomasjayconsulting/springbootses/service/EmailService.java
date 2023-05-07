package com.thomasjayconsulting.springbootses.service;

import com.thomasjayconsulting.springbootses.model.SendHTMLEmailRequest;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${fromEmail}")
    private String fromEmail;

    @Value("${fromName}")
    private String fromName;

    @Async
    public void simpleSend(String toEmail, String subJect, String emailMessage) {

        log.info("simpleSend toEmail: {} subJect: {} emailMessage: {}", toEmail, subJect, emailMessage);

        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromEmail, fromName);
            helper.setTo(toEmail);
            helper.setSubject(subJect);
            helper.setText(emailMessage, true);

            emailSender.send(message);
            log.info("simpleSend: Email Queued");

        }
        catch (Exception e) {
            log.error("Exception: " + e.getMessage());
        }

    }

    @Async
    public void htmlSend(SendHTMLEmailRequest sendHTMLEmailRequest) {

        log.info("simpleSend toEmail: {} subJect: {} templateName: {}", sendHTMLEmailRequest.getEmail(), sendHTMLEmailRequest.getSubject(), sendHTMLEmailRequest.getTemplateName());

        try {
            MimeMessage message = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(fromEmail, fromName);
            helper.setTo(sendHTMLEmailRequest.getEmail());
            helper.setSubject(sendHTMLEmailRequest.getSubject());


            // Thymeleaf Context
            Context context = new Context();

            // Properties to show up in Template after stored in Context
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put("name", sendHTMLEmailRequest.getName());
            properties.put("offerings", sendHTMLEmailRequest.getOfferings());

            context.setVariables(properties);

            String html = templateEngine.process("emails/" + sendHTMLEmailRequest.getTemplateName(), context);


            helper.setText(html, true);

            log.info(html);

            emailSender.send(message);
            log.info("simpleSend: Email Queued");

        }
        catch (Exception e) {
            log.error("Exception: " + e.getMessage());
        }

    }
}
