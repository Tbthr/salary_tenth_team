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

    public List<Menu> getMenuByUserId(String id) {
        return menuMapper.selectByUserId(id);
    }

    public Menu getMenuByPrimaryKey(Integer id){
        return menuMapper.selectByPrimaryKey(id);
    }
    public List<Menu> getAll(){
        return menuMapper.selectAll();
    }

    public List<Menu> getAllAsc(){
        return menuMapper.selectAllAsc();
    }

    public List<Menu> getByLevel(Integer level){
        return menuMapper.selectByLevel(level);
    }

    public List<Menu> getAllAuth(){
        return menuMapper.selectAllAuth();
    }

    public int deleteMenuRoleByAll(Integer rid,Integer mid){
        return menuMapper.deleteMenuRoleByAll(rid, mid);
    }
}
