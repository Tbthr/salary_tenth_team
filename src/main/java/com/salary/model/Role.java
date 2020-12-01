package com.salary.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@ApiModel("角色实体类")
@Data
public class Role implements GrantedAuthority {

    @ApiModelProperty("角色id")
    private Integer id;

    @ApiModelProperty("ROLE_**")
    private String name;

    @ApiModelProperty("权限名称")
    private String nameZh;

    @Override
    public String getAuthority() {
        return name;
    }
}