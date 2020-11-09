package com.salary.model;

import lombok.Data;

@Data
public class User {
    private String userId;

    private String departId;

    private String name;

    private String email;

    private String psd;

    private Integer workYear;

    private Integer userFlag;

    private String gender;
}