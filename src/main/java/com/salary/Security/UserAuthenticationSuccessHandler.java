package com.salary.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.mapper.DepartmentMapper;
import com.salary.model.Department;
import com.salary.model.User;
import com.salary.service.UserService;
import com.salary.service.indexService;
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
    @Autowired
    indexService indexService;
    @Autowired
    DepartmentMapper departmentMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        //获取token
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Department department = departmentMapper.selectByPrimaryKey(user.getDepartId());
        String token = JWTtoken.setToken(user.getId());
        String s ="{\n" +
                "    \"data\": {\n" +
                "        \"id\":\""+user.getId()+"\",\n" +
                "        \"name\": \""+user.getName()+"\",\n" +
                "        \"depart_id\": \""+department.getId()+"\",\n" +
                "        \"email\": \""+user.getEmail()+"\",\n" +
                "        \"gender\":\""+user.getGender()+"\",\n" +
                "        \"work_year\":"+user.getWorkYear()+"\n" +
                "    },\n" +
                "    \"msg\": \"登录成功\",\n" +
                "    \"code\": 200,\n" +
                "    \"token\": \""+token+"\",\n" +
                "    \"index\":";
        String menuSet = indexService.getall(user.getId());
        s+=menuSet+"}";
        out.write(s);
        out.flush();
        out.close();
    }
}
