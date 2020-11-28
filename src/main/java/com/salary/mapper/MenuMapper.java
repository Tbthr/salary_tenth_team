package com.salary.mapper;

import com.salary.model.Menu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int updateByPrimaryKey(Menu record);

    Menu selectByPrimaryKey(Integer id);

    List<Menu> selectByUserId(String id);

    List<Menu> selectAll();
}