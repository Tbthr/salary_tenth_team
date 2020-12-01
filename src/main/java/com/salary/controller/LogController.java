package com.salary.controller;

import com.salary.service.LogService;
import com.salary.util.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sys")
public class LogController {
    @Resource
    private LogService logService;

    @GetMapping("/log")
    public ApiResult getAllLogs(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int rows) {

        return ApiResult.builder().code(200).msg("获取日志成功").data(logService.getAllLog(page, rows)).build();
    }
}
