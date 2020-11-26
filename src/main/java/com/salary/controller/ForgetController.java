package com.salary.controller;


import com.salary.mapper.UserMapper;
import com.salary.service.UserService;
import com.salary.util.ApiResult;
import com.salary.util.SendEmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;

@RestController
public class ForgetController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private String code;
    @RequestMapping("/sendmail")
    public Object sendtomail(@RequestBody HashMap<String,Object> map) throws GeneralSecurityException, MessagingException {
        String id = (String ) map.get("id");
        String mail = userService.getmailaddress(id);
        if(mail==null){
            return ApiResult.builder().code(500).msg("用户不存在").data(null).build();
        }
        code = SendEmailUtil.send(mail);
        return ApiResult.builder().code(200).msg("验证码发送成功").data(null).build();
    }
    @RequestMapping("/forget")
    public Object forget(@RequestBody HashMap<String ,Object> map){
        code="1234";
        String id = (String) map.get("id");
        String psd = (String) map.get("psd");
        psd = bCryptPasswordEncoder.encode(psd);
        String usercode = (String) map.get("con");
        if(usercode.equals(code)){
            if(userMapper.selectByPrimaryKey(id)==null){
                return ApiResult.builder().code(500).msg("用户不存在").data(null).build();
            }else {
                userMapper.updatePsdbyId(id,psd);
                return ApiResult.builder().code(200).msg("更改成功").data(null).build();
            }
        }
        else{
            return ApiResult.builder().code(500).msg("验证码错误").data(null).build();
        }

    }
}