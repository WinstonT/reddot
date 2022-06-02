package com.example.trial.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"", "/", "/home"})
    public String getHome(){
        return "main";
    }
}
