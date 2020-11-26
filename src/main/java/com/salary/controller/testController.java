package com.salary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @RequestMapping("/test")
    public Object test(){
        return "test";
    }
    @RequestMapping("/test/admin")
    public Object testadmin(){
        return "testadmin";
    }
    @RequestMapping("/test/minadmin")
    public Object testminadmin(){
        return "testminadmin";
    }
    @RequestMapping("/test/minadmin/re")
    public Object testminadminre(){
        return "testminadmin/re";
    }
    @RequestMapping("/test/admin/re")
    public Object testadminre(){
        return "testadmin/re";
    }
}
