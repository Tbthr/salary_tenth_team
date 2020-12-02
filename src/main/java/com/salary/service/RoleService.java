package com.salary.service;

import com.salary.mapper.RoleMapper;
import com.salary.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleService {
    @Resource
    private RoleMapper roleMapper;

    public Role selectByName(String name) {
        return roleMapper.selectByName(name);
    }

    public List<Role> getAll(){
        return roleMapper.selectAll();
    }
    public int insertRole(Role role){
        return roleMapper.insert(role);
    }
}
