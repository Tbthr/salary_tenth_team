package com.salary.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.model.Menu;
import com.salary.model.User;
import com.salary.service.MenuService;
import com.salary.util.ApiResult;
import com.salary.util.JWTtoken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    MenuService menuService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        //获取token
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = JWTtoken.setToken(user.getId());

        List<Menu> menus = menuService.getMenuByUserId(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("token", token);
        map.put("menus", menus);
        ApiResult result = ApiResult.builder().code(200).msg("登录成功").data(map).build();
        out.write(objectMapper.writeValueAsString(result));
        out.flush();
        out.close();
    }
}
