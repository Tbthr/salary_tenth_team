package com.salary.model;

import lombok.Data;

import java.util.List;

@Data
public class Menu {
    private Integer id;

    private String url;

    private String path;

    private String component;

    private String name;

    private String iconCls;

    private Boolean keepAlive;

    private Boolean requireAuth;

    private Integer parentId;

    private Boolean enabled;

}