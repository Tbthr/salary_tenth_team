package com.salary.Security;

import com.salary.model.Menu;
import com.salary.model.User;
import com.salary.service.MenuService;
import com.salary.service.RoleService;
import com.salary.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
public class UserPermissionEvaluator implements PermissionEvaluator {

    @Autowired
    private MenuService menuService;


    @Autowired
    private RoleService roleService;

    @Override
    public boolean hasPermission(Authentication authentication,  Object targetUrl, Object targetPermission) {

        User user = (User)authentication.getPrincipal();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) user.getAuthorities();

        for(GrantedAuthority authority : authorities){
            String roleName = authority.getAuthority();
            Integer roleId = roleService.selectByName(roleName).getId();
            List<Menu> menuList = menuService.getAllmenuByrid(roleId);

            for(Menu menu:menuList){
                if(targetUrl.equals(menu.getUrl())){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
