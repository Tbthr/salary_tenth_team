package com.salary.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.model.User;
import com.salary.service.UserService;
import com.salary.util.JWTtoken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        //获取token
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = JWTtoken.setToken(user.getId());
//        String s ="登录成功";
//        Msgformat msgformat = new Msgformat(200,s,null);
//        out.write(msgformat.toString());
        String s ="{" +
                "    \"data\": {" +
                "        \"id\": 500," +
                "        \"rid\": 0," +
                "        \"userid\": \"2018110420\"," +
                "        \"password\": \"123456\"," +
                "        \"username\": \"廖晨玥\"," +
                "        \"department\": \"计算机科学学院\"," +
                "        \"email\": \"123@qq.com\"," +
                "        \"token\": \""+token+"\""+
                "    }," +
                "    \"meta\": {" +
                "        \"msg\": \"登录成功\"," +
                "        \"code\": 200" +
                "    }" +
                "}";
        out.write(s);
        out.flush();
        out.close();
    }
}
