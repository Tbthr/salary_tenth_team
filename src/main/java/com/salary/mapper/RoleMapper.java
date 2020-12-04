package com.salary.mapper;

import com.salary.model.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
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

    Role selectAuthByPrimaryKey(Integer id);

    @Insert("insert into menu_role(role_id,menu_id) values(#{rid},#{mid})")
    int insertMenuRole(Integer rid, Integer mid);

    @Delete("delete from menu_role where role_id =#{rid}")
    int deleteMenuRoleByRid(Integer rid);

    @Select("select id from role where name = #{name}")
    int selectIdByName(String name);

}