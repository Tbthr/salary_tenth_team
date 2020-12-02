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

    public User getUserByPrimaryKey(String id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    public int deleteUser(String id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int updateUser(HashMap<String, Object> map) {
        return userMapper.updateByPrimaryKey(map);
    }

    public int addUser(User user) {
        return userMapper.insert(user);
    }

    public int addRoleToUser(String userId, Integer RoleId) {
        return userMapper.addRoleToUser(userId, RoleId);
    }

}
