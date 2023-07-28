package com.boot.teach.web.controllers.login;

import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "超级管理员登录接口类")
@RestController
@RequestMapping("/super")
public class SuperManagerLogin {
    Logger logger = LoggerFactory.getLogger(SuperManagerLogin.class);

    private final LoginAndLogout loginAndLogout;

    private final MapperFactory mapperFactory;

    public SuperManagerLogin(LoginAndLogout loginAndLogout, MapperFactory mapperFactory) {
        this.loginAndLogout = loginAndLogout;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping("login")
    @ApiOperation(value = "超级管理员登录接口")
    @ApiParam(name = "LoginVo",value = "username,password,checkCode")
    public ServerResponseEntity userLogin(HttpSession session,@RequestBody LoginVO loginVO) throws AuthenticationException {
        String moduleName = "SuperManagerLogin-userLogin";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,loginVO));
        String superManagerCaptcha = (String) session.getAttribute("superManagerCaptcha");
        if (!loginVO.getCheckCode().equalsIgnoreCase(superManagerCaptcha)){
            throw new IllegalArgumentException(ExceptionMessageConstance.CAPTCHA_ERROR);
        }
        mapperFactory.classMap(UserDto.class, UserVo.class);
        LoginDTO loginDTO = mapperFactory.getMapperFacade().map(loginVO, LoginDTO.class);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return loginAndLogout.login(loginDTO);
    }
}
