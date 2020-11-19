package com.salary.service;

import com.salary.mapper.RoleMapper;
import com.salary.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;

    public Role selectByName(String name){
        return roleMapper.selectByName(name);
    }
}
