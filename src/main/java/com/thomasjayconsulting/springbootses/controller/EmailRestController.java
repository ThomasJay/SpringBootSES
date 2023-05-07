package com.thomasjayconsulting.springbootses.controller;

import com.thomasjayconsulting.springbootses.model.NotificationRequest;
import com.thomasjayconsulting.springbootses.model.SendHTMLEmailRequest;
import com.thomasjayconsulting.springbootses.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmailRestController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sendNotification")
    public String sendNotification(@RequestBody NotificationRequest notificationRequest) {

        emailService.simpleSend(notificationRequest.getEmail(), notificationRequest.getSubject(), notificationRequest.getMessage());

        return "Message Queued";
    }

    @PostMapping("/sendHTMLEmail")
    public String sendHTMLEmail(@RequestBody SendHTMLEmailRequest sendHTMLEmailRequest) {

        emailService.htmlSend(sendHTMLEmailRequest);

        return "Message Queued";
    }

}
