package com.boot.teach.web.controllers.login;

import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.auth.LoginDTO;
import com.boot.teach.dto.auth.UserDto;
import com.boot.teach.service.auth.LoginAndLogout;
import com.boot.teach.vo.auth.LoginVO;
import com.boot.teach.vo.auth.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFactory;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "学生登录接口类")
@RestController
@RequestMapping("/student")
public class StudentLogin {

    private final LoginAndLogout loginAndLogout;

    private final MapperFactory mapperFactory;

    public StudentLogin(LoginAndLogout loginAndLogout, MapperFactory mapperFactory) {
        this.loginAndLogout = loginAndLogout;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping("login")
    @ApiOperation(value = "学生登录接口")
    @ApiParam(name = "LoginVo",value = "username,password,checkCode")
    public ServerResponseEntity userLogin(HttpServletRequest request,LoginVO loginVO) throws AuthenticationException {
        HttpSession session = request.getSession();
        String superManagerCaptcha = (String) session.getAttribute("studentCaptcha");
        if (!loginVO.getCheckCode().equalsIgnoreCase(superManagerCaptcha)){
            throw new IllegalArgumentException(ExceptionMessageConstance.CAPTCHA_ERROR);
        }
        mapperFactory.classMap(UserDto.class, UserVo.class);
        LoginDTO loginDTO = mapperFactory.getMapperFacade().map(loginVO, LoginDTO.class);
        return loginAndLogout.login(loginDTO);
    }
}
