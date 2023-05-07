package com.thomasjayconsulting.springbootses.model;

import lombok.Data;

import java.util.List;

@Data
public class SendHTMLEmailRequest {
    private String email;
    private String subject;
    private String name;
    private String templateName = "newCustomer.html";
    private List<Offer> offerings;
}
