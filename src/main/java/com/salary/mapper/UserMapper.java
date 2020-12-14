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

    /**
     * 插入信息
     * @param record 要修改的User信息
     * @return
     */
    int insert(User record);

    User selectByPrimaryKey(String id);

    /**
     * 模糊查找
     * @param map name和id
     * @return 返回符合条件的用户信息
     */
    List<User> selectByIF(HashMap<String,Object> map);

    /**
     * 获取所有的用户信息
     * @return 所有的用户信息
     */
    List<User> selectAll();

    /**
     * 动态更新用户数据
     * @param map 需要更新的用户信息
     * @return
     */
    int updateByPrimaryKey(HashMap<String, Object> map);

    /**
     * 更新密码
     * @param id 用户id
     * @param psd 新密码
     */
    @Select("update user set psd = #{psd} where id = #{id}")
    void updatePsdById(String id, String psd);

    /**
     * 给用户赋予角色
     * @param userId，用户id
     * @param RoleId，角色id
     * @return
     */
    @Insert("insert into user_role(user_id, role_id) values(#{userId}, #{RoleId})")
    Integer addRoleToUser(@Param("userId") String userId, @Param("RoleId") Integer RoleId);
}