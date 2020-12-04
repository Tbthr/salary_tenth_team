package com.salary.controller;

import com.salary.service.BillService;
import com.salary.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;

@Api(tags = "账单信息")
@RestController
@RequestMapping("/bills")
public class BillController {
    @Resource
    private BillService billService;

    @ApiOperation(value = "查看账单", notes = "")
    @PostMapping("/info")
    public ApiResult getInfo(@RequestBody HashMap<String, Object> map,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "10") int rows) {

        return ApiResult.builder()
                .code(200)
                .msg("获取成功")
                .data(billService.getInfo(map, page, rows))
                .build();
    }

    @ApiOperation(value = "提交账单", notes = "")
    @PostMapping("/submit")
    public ApiResult submit(@RequestBody HashMap<String, Object> map) {
        return ApiResult.builder()
                .code(200)
                .msg("提交成功")
                .data(null)
                .build();
    }

    @ApiOperation(value = "审批账单", notes = "")
    @PostMapping("/check")
    public ApiResult check(@RequestBody HashMap<String, Object> map) {
        return ApiResult.builder()
                .code(200)
                .msg("操作成功")
                .data(null)
                .build();
    }
}
