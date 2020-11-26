package com.salary.service;

import com.salary.mapper.MenuMapper;
import com.salary.mapper.RoleMapper;
import com.salary.mapper.UserMapper;
import com.salary.model.Menu;
import com.salary.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class indexService {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    RoleMapper roleMapper;

    public String getall(String uid){
        List<Role> roles = roleMapper.selectAllByUid(uid);
        Set<Menu> menuSet = new HashSet<>();
        for(Role role:roles){
            List<Menu> menus = menuMapper.selectmainbyrid(role.getId());
            if(menus!=null){
                for(Menu menu:menus){
                    menuSet.add(menu);
                }
            }
        }
        String s ="[";
        int length=menuSet.size();
        int i=0;
        for(Menu menu:menuSet){
            s+="{\n" +
                    "            \"id\": "+menu.getId()+",\n" +
                    "            \"authName\": \""+menu.getName()+"\",\n" +
                    "            \"path\":\""+menu.getUrl()+"\",\n" +
                    "            \"children\": [";
            List<Menu> menuList = menuMapper.selectMenubypid(menu.getId());

            if(menuList!=null){
                int length1 = menuList.size();
                int i1=0;
                for(Menu menu1:menuList){
                    i1++;
                    s+="{\n" +
                            "            \"id\": "+menu1.getId()+",\n" +
                            "            \"authName\": \""+menu1.getName()+"\",\n" +
                            "            \"path\": \""+menu1.getUrl()+"\",\n" +
                            "            \"children\": []}";
                    if(i1!=length1){
                        s+=",";
                    }
                }

                s+="]";
            }
            else{
                s+="]";
            }
            i++;
            if(i==length){
                s+="}";
            }
            else{
                s+="},";
            }
        }
        s+="]";
        return s;
    }
}
