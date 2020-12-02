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
            List<Role> roles = roleService.getAllRoleAuth();
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

    @PostMapping("/role/add/role")
    public Object addRole(@RequestBody HashMap<String ,Object> map ){
        String name = (String) map.get("name");
        name ="ROLE_"+name;
        String nameZh = (String) map.get("nameZh");
        Role role = new Role();
        role.setName(name);
        role.setNameZh(nameZh);
        try{
            roleService.insertRole(role);
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("添加角色成功")
                    .data(roles)
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("添加角色失败")
                    .build();
        }
    }
    @RequestMapping("/role/add/menus")
    public Object addMenus(@RequestParam("roleId")Integer rid,@RequestParam("menuId[]")List<Integer> mid){
        try{
            for(Integer i:mid){
                roleService.insertMenuRole(rid, i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("添加权限成功")
                    .data(roles)
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("添加权限失败")
                    .build();
        }
    }

    @RequestMapping("/role/delete/roles")
    public Object deleteRoles(@RequestParam("roleId[]")List<Integer> rid){
        try{
            for(Integer i:rid){
                roleService.deleteMenuRoleById(i);
                roleService.deleteByPrimaryKey(i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("删除角色成功")
                    .data(roles)
                    .build();
        }
        catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("删除角色失败")
                    .build();
        }
    }

    @RequestMapping("/role/delete/menus")
    public Object deleteMenus(@RequestParam("roleId")Integer rid,@RequestParam("menus[]")List<Integer> mid){
        try{
            for(Integer i:mid){
                menuService.deleteMenuRoleByAll(rid,i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("删除权限成功")
                    .data(roles)
                    .build();
        }catch (Exception e){
            return ApiResult.builder()
                    .code(500)
                    .msg("删除权限失败")
                    .build();
        }
    }


}
