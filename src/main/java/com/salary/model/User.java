package com.salary.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ApiModel("用户实体类")
@Data
public class User implements UserDetails {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("部门id")
    private String departId;

    @ApiModelProperty("职位")
    private String position;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("身份证号码")
    private String idCard;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("密码(默认为身份证后六位)")
    private String psd;

    @ApiModelProperty("工龄")
    private Integer workYear;

    @ApiModelProperty("用户类型(0-系统管理员、1-普通员工、2-部门管理员、3-财务管理员)")
    private Integer userFlag;

    @ApiModelProperty("性别(m、f)")
    private String gender;

//    @ApiModelProperty("部门")
    private Department department;

//    @ApiModelProperty("角色列表")
    private List<Role> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if (roles == null) {
//            return null;
//        }
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return authorities;
        return authorities;
    }

    @Override
    public String getPassword() {
        return psd;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}