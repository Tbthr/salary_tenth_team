package com.salary.controller;

import com.salary.aop.Log;
import com.salary.model.User;
import com.salary.service.DepartmentService;
import com.salary.service.RoleService;
import com.salary.service.UserService;
import com.salary.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = {"员工管理"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @ApiOperation(value = "查看员工信息", notes = "返回所有员工的信息")
    @GetMapping("/info")
    public ApiResult getAllUsers() {
        return ApiResult.builder()
                .code(200)
                .msg("所有员工信息")
                .data(userService.getAllUsers())
                .build();
    }

    @ApiOperation(value = "获取相关信息", notes = "返回所有部门、以及对应职位信息")
    @GetMapping("/add/info")
    public ApiResult getAllDeparts(/*@RequestParam("departId") String departId*/) {
        return ApiResult.builder()
                .code(200)
                .msg("获取成功")
                .data(departmentService.getAllDeparts())
                .build();
    }

    @Log(module = "添加员工")
    @ApiOperation(value = "添加员工", notes = "返回所有员工的信息")
    @PostMapping("/add")
    public ApiResult addUser(@RequestBody User user, @RequestParam("roleName") String roleName) {
        user.setPsd(bCryptPasswordEncoder.encode(user.getPsd()));
        int i = userService.addUser(user);
        int j = userService.addRoleToUser(user.getId(), roleService.selectByName(roleName).getId());
        if (i > 0 && j > 0) {
            return ApiResult.builder()
                    .code(200)
                    .msg("添加成功")
                    .data(userService.getAllUsers())
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("添加失败")
                .data(null)
                .build();
    }

    @Log(module = "删除员工")
    @ApiOperation(value = "删除员工", notes = "返回所有员工的信息")
    @PostMapping("/delete")
    public ApiResult deleteUser(@RequestBody HashMap<String, Object> map) {
        String id = (String) map.get("id");
        int i = userService.deleteUser(id);
        if (i > 0) {
            return ApiResult.builder()
                    .code(200)
                    .msg("删除成功")
                    .data(userService.getAllUsers())
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("删除失败")
                .data(null)
                .build();
    }

    @Log(module = "修改员工")
    @ApiOperation(value = "修改员工", notes = "返回所有员工的信息")
    @PostMapping("/update")
    public ApiResult updateUser(@RequestBody HashMap<String, Object> map) {
        String psd = (String) map.get("psd");
        if (psd != null) {
            map.put("psd", bCryptPasswordEncoder.encode(psd));
        }
        int i = userService.updateUser(map);
        if (i > 0) {
            return ApiResult.builder()
                    .code(200)
                    .msg("更新成功")
                    .data(userService.getAllUsers())
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("更新失败")
                .data(null)
                .build();
    }
}
