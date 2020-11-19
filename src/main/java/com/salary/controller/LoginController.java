package com.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/test")
    @PreAuthorize("hasPermission('/test','')")
    public  String test(){
        return "Yes,test";
    }

    @RequestMapping("/test/error")
    @PreAuthorize("hasPermission('/test/error','')")
    public  String errortest(){
        return "No,test";
    }
}
