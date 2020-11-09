package com.salary.model;

import lombok.Data;

import java.util.Date;

@Data
public class Bill {
    private Date date;

    private String userId;

    private Double basicPay;

    private Double positionPay;

    private Double seniorityPay;

    private Double trafficAllowance;

    private Double otherAllowance;

    private Double fiof;

    private Double shouldPay;

    private Double otherCutpay;

    private Double tax;

    private Double pay;

    private Date confirmDate;

    private Integer confirmFlag;
}