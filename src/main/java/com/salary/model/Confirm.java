package com.salary.model;

import lombok.Data;

import java.util.Date;

@Data
public class Confirm {
    private Date date;

    private String userId;

    private Date confirmDate;

    private Integer confirmFlag;
}