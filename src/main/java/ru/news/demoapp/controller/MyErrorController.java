package ru.news.demoapp.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

public class MyErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        return "error";
    }

}
