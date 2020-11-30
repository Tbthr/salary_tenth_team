package com.salary.controller;

import com.salary.model.User;
import com.salary.service.UserService;
import com.salary.util.ApiResult;
import com.salary.util.SendEmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = {"个人信息"})
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    UserService userService;

    @ApiOperation(value = "查看个人信息", notes = "")
    @GetMapping("/info")
    public Object getInfo(@RequestBody String id) {
        User user = userService.getUserByPrimaryKey(id);
        if (user != null) {
            return ApiResult.builder()
                    .code(200)
                    .msg("获取成功")
                    .data(user)
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("操作失败")
                .build();
    }

    @ApiOperation(value = "修改个人信息", notes = "")
    @PostMapping("/edit/info")
    public Object editInfo(HashMap<String, Object> map) {
        String id = (String) map.get("id");
        String code = (String) map.get("code");
        if (!SendEmailUtil.codeMap.get(id).equals(code)) {
            return ApiResult.builder()
                    .code(500)
                    .msg("验证码错误")
                    .build();
        }
        int i = userService.updateUser(map);
        if (i > 0) {
            return ApiResult.builder()
                    .code(200)
                    .msg("更改成功")
                    .data(userService.getUserByPrimaryKey(id))
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("操作失败")
                .build();
    }

    @ApiOperation(value = "修改密码", notes = "")
    @PostMapping("/edit/psd")
    public Object editPsd(HashMap<String, Object> map) {
        String id = (String) map.get("id");
        User user = userService.getUserByPrimaryKey(id);
        String oldPsd = (String) map.get("oldPsd");
        if (!oldPsd.equals(user.getPsd())) {
            return ApiResult.builder()
                    .code(500)
                    .msg("旧密码错误")
                    .build();
        }
        int i = userService.updateUser(map);
        if (i > 0) {
            return ApiResult.builder()
                    .code(200)
                    .msg("更改成功")
                    .data(userService.getUserByPrimaryKey(id))
                    .build();
        }
        return ApiResult.builder()
                .code(500)
                .msg("操作失败")
                .build();
    }
}

