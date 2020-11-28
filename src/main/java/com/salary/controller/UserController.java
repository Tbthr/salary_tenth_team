package com.salary.controller;

import com.salary.model.User;
import com.salary.service.DepartmentService;
import com.salary.service.UserService;
import com.salary.util.ApiResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/info")
    public ApiResult getAllUsers() {
        return ApiResult.builder()
                .code(200)
                .msg("所有员工信息")
                .data(userService.getAllUsers())
                .build();
    }

    @PostMapping("/add")
    public ApiResult addUser() {
        return ApiResult.builder()
                .code(200)
                .msg("所有部门信息")
                .data(departmentService.getAllDeparts())
                .build();
    }

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

    @PostMapping("/update")
    public ApiResult updateUser(@RequestBody User user) {
        int i = userService.updateUser(user);
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
