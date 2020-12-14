package com.salary.service;

import com.salary.mapper.UserMapper;
import com.salary.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new UsernameNotFoundException("工号不存在！");
        }
        return user;
    }


    /**
     * 模糊查找
     * @param map name和id
     * @return 返回符合条件的用户信息
     */
    public List<User> getUserByIF(HashMap<String, Object> map) {
        return userMapper.selectByIF(map);
    }

    public User getUserByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取所有的用户信息
     * @return 所有的用户信息
     */
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    public int deleteUser(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 动态更新用户数据
     * @param map 需要更新的用户信息
     * @return
     */
    public int updateUser(HashMap<String, Object> map) {
        return userMapper.updateByPrimaryKey(map);
    }

    /**
     * 插入信息
     * @param user 要修改的User信息
     * @return
     */
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    /**
     * 给用户赋予角色
     * @param userId，用户id
     * @param RoleId，角色id
     * @return
     */
    public int addRoleToUser(String userId, Integer RoleId) {
        return userMapper.addRoleToUser(userId, RoleId);
    }

}
