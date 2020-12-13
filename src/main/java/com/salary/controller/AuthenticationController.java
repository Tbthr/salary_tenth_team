package com.salary.controller;

import com.salary.aop.Log;
import com.salary.model.Menu;
import com.salary.model.Role;
import com.salary.service.MenuService;
import com.salary.service.RoleService;
import com.salary.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Api(tags = "权限管理")
@RestController
@RequestMapping("/authority")
public class AuthenticationController {
    @Resource
    MenuService menuService;
    @Resource
    RoleService roleService;

    @Log(info = "SELECT",module = "获取权限列表")
    @ApiOperation(value = "获取权限列表", notes = "")
    @GetMapping("/menu")
    public Object getAllMenu() {
        try {
            List<Menu> menus = menuService.getAllAsc();
            return ApiResult.builder()
                    .code(200)
                    .msg("获取成功")
                    .data(menus)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("获取失败")
                    .build();
        }
    }

    @Log(info = "SELECT",module = "获取角色列表")
    @ApiOperation(value = "获取角色列表", notes = "")
    @GetMapping("/role")
    public Object getAllRole() {
        try {
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("获取成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("获取失败")
                    .build();
        }
    }

    @Log(info = "INSERT",module = "增加角色")
    @ApiOperation(value = "增加角色", notes = "")
    @PostMapping("/role/add/role")
    public Object addRole(@RequestBody HashMap<String, Object> map) {
        String name = (String) map.get("name");
        name = "ROLE_" + name;
        String nameZh = (String) map.get("nameZh");
        Role role = new Role();
        role.setName(name);
        role.setNameZh(nameZh);
        try {
            roleService.insertRole(role);
            int id = roleService.selectRoleIdByName(name);
            roleService.init(id);
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("添加角色成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("添加角色失败")
                    .build();
        }
    }

    @Log(info = "SELECT",module = "获取权限树")
    @ApiOperation(value = "获取权限树", notes = "")
    @PostMapping("/role/add/menutree")
    public Object getMenuTree(@RequestBody HashMap<String, Object> map) {
        int id = (int) map.get("id");
        return ApiResult.builder()
                .code(200)
                .msg("获取权限树成功")
                .data(menuService.getNotAuthByRoleId(id))
                .build();
    }

    @Log(info = "INSERT",module = "添加权限")
    @ApiOperation(value = "添加权限", notes = "")
    @PostMapping("/role/add/menu")
    public Object addMenus(@RequestBody HashMap<String, Object> map) {
        int rid = (int) map.get("roleId");
        List<Integer> mid = (List<Integer>) map.get("menuId");
        try {
            for (Integer i : mid) {
                roleService.insertMenuRole(rid, i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("添加权限成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("添加权限失败")
                    .build();
        }
    }

    @Log(info = "UPDATE",module = "修改角色")
    @ApiOperation(value = "修改角色", notes = "")
    @PostMapping("/role/update")
    public Object updateRole(@RequestBody HashMap<String, Object> map) {
        try {
            int id = (int) map.get("id");
            String name = (String) map.get("name");
            name = "ROLE_" + name;
            String nameZh = (String) map.get("nameZh");
            Role role = roleService.selectByPrimaryKey(id);
            role.setName(name);
            role.setNameZh(nameZh);
            roleService.updateRole(role);
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("修改角色成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("修改角色失败")
                    .build();
        }
    }

    @Log(info = "DELETE",module = "删除角色")
    @ApiOperation(value = "删除角色", notes = "")
    @PostMapping("/role/delete/roles")
    public Object deleteRoles(@RequestBody HashMap<String, Object> map) {
        List<Integer> rid = (List<Integer>) map.get("id");
        try {
            for (Integer i : rid) {
                roleService.deleteMenuRoleById(i);
                roleService.deleteByPrimaryKey(i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("删除角色成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("删除角色失败")
                    .build();
        }
    }

    @Log(info = "DELETE",module = "删除权限")
    @ApiOperation(value = "删除权限", notes = "")
    @PostMapping("/role/delete/menus")
    public Object deleteMenus(@RequestBody HashMap<String, Object> map) {
        int rid = (int) map.get("roleId");
        List<Integer> mid = (List<Integer>) map.get("menuId");
        try {
            for (Integer i : mid) {
                menuService.deleteMenuRoleByAll(rid, i);
            }
            List<Role> roles = roleService.getAllRoleAuth();
            return ApiResult.builder()
                    .code(200)
                    .msg("删除权限成功")
                    .data(roles)
                    .build();
        } catch (Exception e) {
            return ApiResult.builder()
                    .code(500)
                    .msg("删除权限失败")
                    .build();
        }
    }
}
