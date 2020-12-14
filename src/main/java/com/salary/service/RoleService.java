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

    public List<Role> getAll() {
        return roleMapper.selectAll();
    }

    public int insertRole(Role role) {
        return roleMapper.insert(role);
    }

    /**
     * 获取所有角色对应的权限树
     * @return id对应角色的权限树
     */
    public List<Role> getAllRoleAuth() {
        List<Role> roles = getAll();
        List<Role> roleList = new ArrayList<>();
        for (Role role : roles) {
            Role role1 = roleMapper.selectAuthByPrimaryKey(role.getId());
            roleList.add(role1);
        }
        return roleList;
    }

    /**
     * 插入menu_role表
     * @param rid，角色id
     * @param mid，权限id
     * @return
     */
    public int insertMenuRole(Integer rid, Integer mid) {
        return roleMapper.insertMenuRole(rid, mid);
    }

    /**
     * 删除menu_role中角色的所有权限
     * @param id，角色id
     * @return
     */
    public int deleteMenuRoleById(Integer id) {
        return roleMapper.deleteMenuRoleByRid(id);
    }

    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据name获取角色信息
     * @param name，角色的name
     * @return
     */
    public Integer selectRoleIdByName(String name) {
        return roleMapper.selectIdByName(name);
    }

    /**
     * 初始化角色的权限
     * @param id 角色id
     * @param menuId，权限id集合
     */
    public void init(Integer id,List<Integer> menuId) {
        for(Integer i:menuId){
            roleMapper.insertMenuRole(id, i);
        }
    }

    public int updateRole(Role role) {
        return roleMapper.updateByPrimaryKey(role);
    }

    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }
}
