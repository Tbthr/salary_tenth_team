package com.salary.mapper;

import com.salary.model.Menu;
import com.salary.model.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Integer id);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);

    List<Menu> selectMenubyRole();

    List<Role> selectRoleByMenu(Integer Menuid);

    @Select("select * from menu where id in(select menu_id from menu_role where role_id = #{id} )")
    List<Menu> selectallbyrid(Integer id);

    @Select("select * from menu where id in(select menu_id from menu_role where role_id = #{id} and parent_id=1)")
    List<Menu> selectmainbyrid(Integer id);

    @Select("select * from menu where parent_id=#{id}")
    List<Menu> selectMenubypid(Integer id);
}