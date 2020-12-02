package com.salary.mapper;

import com.salary.model.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MenuMapper {
    public  int deleteByPrimaryKey(Integer id);

    public  int insert(Menu record);

    public  Menu selectByPrimaryKey(Integer id);

    public  List<Menu> selectAll();

    @Select("select * from menu where id != 1 and id!= 2 order by id")
    public  List<Menu> selectAllAsc();

    public  int updateByPrimaryKey(Menu record);

    public  List<Menu> selectByUserId(String id);

    @Select("select * from menu where level = #{level}")
    public  List<Menu> selectByLevel(Integer level);

    List<Menu> selectAuthByPid(Integer id);

    List<Menu> selectAllAuth();

    @Delete("delete from menu_role where role_id = #{rid} and menu_id = #{mid}")
    int deleteMenuRoleByAll(Integer rid,Integer mid);

//    List<Menu> selectRoleAuthByPid(Integer rid);
//
//    List<Menu> selectRoleAllAuthByRId(Integer rid);
}