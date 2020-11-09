package com.salary.model;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorRec {
    private Date date;

    private String userId;

    private Integer errorId;

    private Double before;

    private Double after;

    private Date checkTime;

    private Integer errorFlag;
}