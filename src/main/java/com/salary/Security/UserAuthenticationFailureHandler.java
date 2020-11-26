package com.salary.Security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class UserAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
//        String sb;
//        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
//            sb="用户名或密码输入错误，登录失败!";
//        } else if (e instanceof DisabledException) {
//            sb="账户被禁用，登录失败，请联系管理员!";
//        } else {
//            sb="登录失败!";
//        }
//        Msgformat msgformat = new Msgformat(500,sb,null);
//        out.write(msgformat.toString());
        String s ="{" +
                "    \"data\":null," +
                "    \"meta\": {" +
                "        \"msg\": \"登录失败\"," +
                "        \"code\": 500" +
                "    }" +
                "}";
        out.write(s);
        out.flush();
        out.close();
    }
}
