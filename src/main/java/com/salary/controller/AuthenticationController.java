package com.salary.controller;


import com.salary.model.Menu;
import com.salary.model.Role;
import com.salary.service.MenuService;
import com.salary.service.RoleService;
import com.salary.util.ApiResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Api(tags = {"权限管理"})
@RestController
@RequestMapping("/authority")
public class AuthenticationController {
    @Resource
    MenuService menuService;
    @Resource
    RoleService roleService;

    @GetMapping("/menu")
    public Object getAllMenu(){
        try{
            List<Menu> menus = menuService.getAllAsc();
            return ApiResult.builder()
                    .code(200)
                    .msg("获取成功")
                    .data(menus)
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("获取失败")
                    .build();
        }
    }

    @GetMapping("/role")
    public Object getAllRole(){
        try{
            List<Role> roles = roleService.getAll();
            return ApiResult.builder()
                    .code(200)
                    .msg("获取成功")
                    .data(roles)
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("获取失败")
                    .build();
        }
    }

    @PostMapping("/role/add")
    public Object addRole(@RequestBody HashMap<String ,Object> map ){
        String name = (String) map.get("name");
        String nameZh = (String) map.get("nameZh");
        Role role = new Role();
        role.setName(name);
        role.setNameZh(nameZh);
        try{
            roleService.insertRole(role);
            return ApiResult.builder()
                    .code(200)
                    .msg("添加成功")
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("添加失败")
                    .build();
        }

    }
}
