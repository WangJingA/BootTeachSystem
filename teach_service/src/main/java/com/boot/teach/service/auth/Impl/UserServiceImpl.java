package com.boot.teach.service.auth.Impl;

import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.security.LoginUser;
import com.boot.teach.model.auth.TeachUser;
import com.boot.teach.service.auth.AuthUserService;
import com.boot.teach.service.auth.PermissionService;
import com.boot.teach.service.auth.RoleService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import javax.naming.NoPermissionException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * springsecurity---userDetailService实现类
 * 实现当前用户的角色权限
 */
@Component
public class UserServiceImpl implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final RoleService roleService;

    private final AuthUserService userService;

    private final PermissionService permissionService;

    public UserServiceImpl(RoleService roleService, AuthUserService userService, PermissionService permissionService) {
        this.roleService = roleService;
        this.userService = userService;
        this.permissionService = permissionService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser userDetail = null;
        try {
            TeachUser user = userService.getUserByUsername(username);
            if (Objects.isNull(user)){
                throw new RuntimeException(ExceptionMessageConstance.USER_ACCOUNT_OR_PASSWORD_WRONG);
            }
            Set<String> role = roleService.selectRoleByUserId(user.getUuid()).stream().collect(Collectors.toSet());
            List<String> permission = permissionService.getPermissionByUserId(user.getUuid());
            permission.addAll(role);
            userDetail = new LoginUser(user,permission);
        } catch (AuthenticationException | NoPermissionException e) {
            throw new RuntimeException(e);
        }
        return userDetail;
    }
}
