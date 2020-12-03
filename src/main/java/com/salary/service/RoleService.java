package com.salary.service;

import com.salary.mapper.RoleMapper;
import com.salary.model.Role;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    public List<Role> getAllRoleAuth(){
        List<Role> roles = getAll();
        List<Role> roleList = new ArrayList<>();
        for(Role role:roles){
            Role role1 = roleMapper.selectAuthByPrimaryKey(role.getId());
            roleList.add(role1);
        }
        return roleList;
    }

    public int insertMenuRole(Integer rid,Integer mid){
        return roleMapper.insertMenuRole(rid, mid);
    }

    public int deleteMenuRoleById(Integer id){
        return roleMapper.deleteMenuRoleByRid(id);
    }
    public int deleteByPrimaryKey(Integer id){
        return roleMapper.deleteByPrimaryKey(id);
    }

    public Integer selectRoleIdByName(String name){
        return roleMapper.selectIdByName(name);
    }

    public void init(Integer id){
        roleMapper.insertMenuRole(id,3);
        roleMapper.insertMenuRole(id,7);
    }
    public int updateRole(Role role){
        return roleMapper.updateByPrimaryKey(role);
    }

    public Role selectByPrimaryKey(Integer id){
        return roleMapper.selectByPrimaryKey(id);
    }
}
