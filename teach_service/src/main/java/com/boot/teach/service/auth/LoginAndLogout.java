package com.boot.teach.service.auth;


import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.auth.LoginDTO;
import com.boot.teach.dto.auth.UserDto;
import org.apache.tomcat.websocket.AuthenticationException;

import javax.servlet.http.HttpServletRequest;


public interface LoginAndLogout {
    ServerResponseEntity login(LoginDTO loginDTO) throws AuthenticationException;

    ServerResponseEntity logout();
}
