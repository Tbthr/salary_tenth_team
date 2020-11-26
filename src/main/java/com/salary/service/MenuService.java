package com.salary.service;

import com.salary.mapper.MenuMapper;
import com.salary.model.Menu;
import com.salary.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;

    public List<Menu> getAllMenusWithRole() {
        return menuMapper.selectMenubyRole();
    }

    public List<Menu> getAllmenuByrid(Integer id){
        return menuMapper.selectallbyrid(id);
    }

    public List<Role> getALLRole(Integer id) {
        return menuMapper.selectRoleByMenu(id);
    }

    public List<Menu> getAll(){
        return menuMapper.selectAll();
    }

}
