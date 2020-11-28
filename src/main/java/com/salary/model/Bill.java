package com.salary.model;

import lombok.Data;

import java.util.Date;

@Data
public class Bill {
    private Date date;

    private String userId;

    private Integer itemId;

    private Integer money;
}