package com.salary.mapper;

import com.salary.model.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    List<Role> selectRoleByMenuId(Integer MenuId);

    @Select("select * from role where name = #{name}")
    Role selectByName(String name);
}