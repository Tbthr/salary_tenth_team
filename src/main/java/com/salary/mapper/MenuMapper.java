package com.salary.mapper;

import com.salary.model.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    Menu selectByPrimaryKey(Integer id);

    List<Menu> selectAll();

    @Select("select * from menu where id != 1 and id!= 2 order by id")
    List<Menu> selectAllAsc();

    int updateByPrimaryKey(Menu record);

    List<Menu> selectByUserId(String id);

    @Select("select * from menu where level = #{level}")
    List<Menu> selectByLevel(Integer level);

    List<Menu> selectAuthByPid(Integer id);

    List<Menu> selectAllAuth();

    @Delete("delete from menu_role where role_id = #{rid} and menu_id = #{mid}")
    int deleteMenuRoleByAll(Integer rid, Integer mid);

    List<Menu> getNotAuthByRoleId(Integer id);

}