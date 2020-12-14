package com.salary.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel("账单信息实体类")
@Data
public class Bill {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;

    private String userId;
    private User user;
    private Department department;

    private BigDecimal basePay;

    private BigDecimal seniorityPay;

    private BigDecimal postPay;

    private BigDecimal foodAllow;

    private BigDecimal trafficAllow;

    private BigDecimal travelAllow;

    private BigDecimal overtimeAllow;

    private BigDecimal otherAllow;

    private BigDecimal fiof;

    private BigDecimal absenceCut;

    private BigDecimal otherCut;

    private BigDecimal incomeTax;

    private BigDecimal shouldPay;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date checkTime;

    private Integer checkStatus;

    private String mark;

}