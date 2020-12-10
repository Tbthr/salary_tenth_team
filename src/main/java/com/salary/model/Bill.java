package com.salary.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill {
    private Date date;

    private String userId;

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

    private Date checkTime;

    private Integer checkStatus;
}