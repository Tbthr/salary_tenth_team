package com.salary.mapper;

import com.salary.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Transactional
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(HashMap<String, Object> map);

    @Select("update user set psd = #{psd} where id = #{id}")
    void updatePsdById(String id, String psd);

    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{RoleId})")
    Integer addRoleToUser(@Param("userId") String userId, @Param("RoleId") Integer RoleId);
}