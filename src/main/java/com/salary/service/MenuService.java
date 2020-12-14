package com.salary.service;

import com.salary.mapper.MenuMapper;
import com.salary.model.Menu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MenuService {
    @Resource
    private MenuMapper menuMapper;

    /**
     * 获取用户的权限树
     * @param id，用户id
     * @return id对应用户的权限树
     */
    public List<Menu> getMenuByUserId(String id) {
        return menuMapper.selectByUserId(id);
    }

    /**
     * 根据权限id筛选权限
     * @param id，权限id
     * @return
     */
    public Menu getMenuByPrimaryKey(Integer id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取所有权限
     * @return
     */
    public List<Menu> getAll() {
        return menuMapper.selectAll();
    }

    /**
     * 获取侧边栏的所有权限
     * @return
     */
    public List<Menu> getAllAsc() {
        return menuMapper.selectAllAsc();
    }

    /**
     * 根据权限等级筛选权限
     * @param level 等级
     * @return 对应权限列表
     */
    public List<Menu> getByLevel(Integer level) {
        return menuMapper.selectByLevel(level);
    }

    /**
     * 获取权限树（递归查询）
     * @return 所有的权限树
     */
    public List<Menu> getAllAuth() {
        return menuMapper.selectAllAuth();
    }

    /**
     * 删除menu_role表的信息
     * @param rid，角色id
     * @param mid，权限id
     * @return
     */
    public int deleteMenuRoleByAll(Integer rid, Integer mid) {
        return menuMapper.deleteMenuRoleByAll(rid, mid);
    }

    /**
     * 获取角色没有获取的权限的权限树
     * @param id 角色id
     * @return id对应角色的权限树
     */
    public List<Menu> getNotAuthByRoleId(Integer id) {
        return menuMapper.getNotAuthByRoleId(id);
    }
}
