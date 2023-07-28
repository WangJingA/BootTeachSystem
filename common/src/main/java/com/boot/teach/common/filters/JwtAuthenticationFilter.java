package com.boot.teach.common.filters;

import cn.hutool.core.util.StrUtil;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.security.LoginUser;
import com.boot.teach.common.util.JWTUtils;
import com.boot.teach.common.util.RedisUtil;
import io.jsonwebtoken.Claims;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 自定义拦截器，首先请求会经过此拦截器进行拦截
 * 在security中会将该拦截器加入拦截器链
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

   private final RedisUtil redisUtil;
   private final MapperFactory mapperFactory;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(RedisUtil redisUtil, MapperFactory mapperFactory) {
        this.redisUtil = redisUtil;
        this.mapperFactory = mapperFactory;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String moduleName = "JwtAuthenticationFilter-doFilterInternal";
        long startTime = System.currentTimeMillis();
        String token = httpServletRequest.getHeader("token");
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,token));
        //token不存在则直接放行，后面的过滤器会执行处理逻辑
        if (StrUtil.isEmptyIfStr(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //存在token，解析token
        String userId = null;
        try {
            Claims claims = JWTUtils.parserToken(token);
            userId = claims.getId();
            logger.info(String.format("用户id：%s",userId));
        }catch (Exception e){
            throw new AuthenticationException(e.getMessage());
        }
        //从redis中获取login user info
        String redisKey = "login:"+userId;
        LoginUser userDetail = (LoginUser)redisUtil.get(redisKey);
        if (Objects.isNull(userDetail)){
            throw new AuthenticationException(ExceptionMessageConstance.USER_NO_LOGIN);
        }
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName, System.currentTimeMillis()-startTime));
         //将用户信息存储在security上下文
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName, System.currentTimeMillis()-startTime));
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
