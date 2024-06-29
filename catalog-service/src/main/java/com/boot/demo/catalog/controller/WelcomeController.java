package com.boot.demo.catalog.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {

    @Value("${welcome.message}")
    private String message;


    @GetMapping
    public String catalogWelcome() {
        return message;
    }


}