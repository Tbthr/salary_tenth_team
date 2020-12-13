package com.salary.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salary.aop.Log;
import com.salary.model.Bill;
import com.salary.service.BillService;
import com.salary.util.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "账单信息")
@RestController
@RequestMapping("/bills")
public class BillController {
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private BillService billService;

    @Log(info = "SELECT",module = "查看账单")
    @ApiOperation(value = "查看账单", notes = "根据条件筛选(分页)")
    @GetMapping("/info")
    public ApiResult getInfo(@RequestParam HashMap<String, Object> map,
                             @RequestParam(required = false, defaultValue = "1") int page,
                             @RequestParam(required = false, defaultValue = "10") int rows) {

        return ApiResult.builder()
                .code(200)
                .msg("获取成功")
                .data(billService.getInfo(map, page, rows))
                .build();
    }

    @Log(info = "INSERT",module = "提交账单")
    @ApiOperation(value = "提交账单", notes = "")
    @PostMapping("/submit")
    public ApiResult submit(@RequestBody Map<String, Object> map) throws ParseException {
        int i, j;
        String s = (String) map.get("date");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
        Date date = simpleDateFormat.parse(s);
//        System.out.println(date);   //Thu Feb 01 00:00:00 CST 2018
//        System.out.println(simpleDateFormat.format(date));  //2018-02-01
        List<Bill> temp = (List<Bill>) map.get("bills");
        List<Bill> bills = objectMapper.convertValue(temp, new TypeReference<List<Bill>>() {
        }); // 解决 class java.util.LinkedHashMap cannot be cast to class com.salary.model.Bill
        for (Bill bill : bills) {
            bill.setDate(date);
            if (billService.getBillById(bill.getDate(), bill.getUserId()) != null) {
                i = billService.updateBill(bill);
                if (i <= 0) {
                    return ApiResult.builder()
                            .code(500)
                            .msg("提交错误")
                            .data(null)
                            .build();
                }
            } else {
                j = billService.insertBill(bill);
                if (j <= 0) {
                    return ApiResult.builder()
                            .code(500)
                            .msg("提交错误")
                            .data(null)
                            .build();
                }
            }
        }
        return ApiResult.builder()
                .code(200)
                .msg("提交成功")
                .data(null)
                .build();
    }

    @Log(info = "UPDATE",module = "审批账单")
    @ApiOperation(value = "审批账单", notes = "")
    @PostMapping("/check")
    public ApiResult check(@RequestBody List<Bill> list) {
        for (Bill bill : list) {
            int i = billService.updateBill(bill);
            if (i <= 0) {
                return ApiResult.builder()
                        .code(500)
                        .msg("操作失败")
                        .data(null)
                        .build();
            }
        }
        return ApiResult.builder()
                .code(200)
                .msg("操作成功")
                .data(null)
                .build();
    }
}
