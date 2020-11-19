package com.salary.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping("/test1")
    public  String test1(){
        return "超级管理员";
    }

    @RequestMapping("/test2")
    public  String test2(){
        return "管理员";
    }
    @RequestMapping("/test3")
    public  String test3(){
        return "用户";
    }
}
