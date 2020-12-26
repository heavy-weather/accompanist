package com.anthonyguidotti.accompanist.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    private Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @GetMapping("/")
    public String getHome() {
        return "index.html";
    }

}
