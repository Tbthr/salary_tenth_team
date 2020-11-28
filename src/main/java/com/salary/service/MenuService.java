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
}
