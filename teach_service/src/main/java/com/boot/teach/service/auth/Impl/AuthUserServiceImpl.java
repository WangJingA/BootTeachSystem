package com.boot.teach.service.auth.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.dao.auth.AuthUserMapper;
import com.boot.teach.model.auth.TeachUser;
import com.boot.teach.service.auth.AuthUserService;
import org.apache.tomcat.websocket.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthUserServiceImpl extends ServiceImpl<AuthUserMapper, TeachUser> implements AuthUserService {
    private Logger logger = LoggerFactory.getLogger(AuthUserServiceImpl.class);
    private final AuthUserMapper userMapper;

    public AuthUserServiceImpl(AuthUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public TeachUser getUserByUsername(String username) throws AuthenticationException {
        TeachUser user = userMapper.selectOne(new QueryWrapper<TeachUser>().lambda()
                .eq(TeachUser::getUserAccount, username)
                .eq(TeachUser::getUserStatus,0));
        if (Objects.isNull(user)){
            throw new AuthenticationException("用户信息不存在");
        }
        return user;
    }
}
