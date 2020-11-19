package com.salary.mapper;

import com.salary.model.Menu;
import com.salary.model.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    @Select("select * from role where id in (select role_id from user_role where user_id = #{id})")
    List<Role> selectAllByUid(String id);

    @Select("select * from role where name =#{name}")
    Role selectByName(String name);
}