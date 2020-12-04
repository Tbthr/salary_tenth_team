package com.salary.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Bill {
    private Date date;

    private String userId;

    private BigDecimal basePay;

    private BigDecimal postPay;

    private BigDecimal seniorityPay;

    private BigDecimal trafficAllow;

    private BigDecimal heatAllow;

    private BigDecimal holidaySympathy;

    private BigDecimal otherAllow;

    private BigDecimal shouldPay;

    private BigDecimal fiof;

    private BigDecimal absenceCut;

    private BigDecimal otherCut;

    private BigDecimal pay;
}