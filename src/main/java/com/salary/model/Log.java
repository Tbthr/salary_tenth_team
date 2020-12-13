package com.salary.model;

import lombok.Data;

@Data
public class Log {
    private Integer id;

    private String date;

    private String username;

    private String ip;

    private String module;

    private String info;

    private String content;
}