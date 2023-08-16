package com.boot.teach.web.controllers.login;

import com.boot.teach.common.annotations.RequestLock;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.exception.CaptchaException;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.auth.LoginDTO;
import com.boot.teach.dto.auth.UserDto;
import com.boot.teach.service.auth.LoginAndLogout;
import com.boot.teach.service.manager.LoginBackCallService;
import com.boot.teach.vo.auth.LoginVO;
import com.boot.teach.vo.auth.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFactory;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "管理员登录接口类")
@RestController
@RequestMapping("/manager")
public class ManagerLogin {

    private final LoginAndLogout loginAndLogout;
    private final LoginBackCallService backCallService;
    private final MapperFactory mapperFactory;

    private final LoginAndLogout managerLogin;

    public ManagerLogin(LoginAndLogout loginAndLogout, LoginBackCallService backCallService, MapperFactory mapperFactory, LoginAndLogout managerLogin) {
        this.loginAndLogout = loginAndLogout;
        this.backCallService = backCallService;
        this.mapperFactory = mapperFactory;
        this.managerLogin = managerLogin;
    }

    @PostMapping("login")
    @RequestLock(prefix = "userLogin")
    @ApiOperation(value = "管理员登录接口")
    @ApiParam(name = "LoginVo",value = "username,password,checkCode")
    public ServerResponseEntity userLogin(HttpServletRequest request,@RequestBody LoginVO loginVO) throws AuthenticationException {
        HttpSession session = request.getSession();
        String superManagerCaptcha = (String) session.getAttribute("managerCaptcha");
        if (!loginVO.getCheckCode().equalsIgnoreCase(superManagerCaptcha)){
            return ServerResponseEntity.fail(ResponseEnum.CAPTCHA_EXCEPTION,"验证码错误");
        }
        mapperFactory.classMap(UserDto.class, UserVo.class);
        LoginDTO loginDTO = mapperFactory.getMapperFacade().map(loginVO, LoginDTO.class);
        return loginAndLogout.login(loginDTO);
    }

    /**
     * 管理员登录回调，获取到管理员的信息-管理的学校
     * @param uuid
     * @return
     * @throws AuthenticationException
     */
    @PostMapping("backCall")
    @ApiOperation(value = "管理员登录回调接口")
    @ApiParam(name = "uuid",value = "uuid",type = "String")
    public ServerResponseEntity managerLoginBackCall(@RequestParam("uuid") String uuid) throws AuthenticationException {
        return backCallService.backCall(uuid);
    }

    @PostMapping("loginOut")
    @ApiOperation(value = "管理员退出登录")
    public ServerResponseEntity loginOut() throws AuthenticationException {
        return managerLogin.logout();
    }
}
