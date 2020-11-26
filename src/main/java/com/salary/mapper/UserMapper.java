package com.salary.mapper;

import com.salary.model.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(String id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    @Select("update user set psd=#{psd} where id = #{id}")
    void updatePsdbyId(String  id,String psd);
}