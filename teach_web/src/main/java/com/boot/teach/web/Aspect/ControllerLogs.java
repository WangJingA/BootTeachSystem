package com.boot.teach.web.Aspect;

import com.boot.teach.common.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
public class ControllerLogs {
    private Logger logger = LoggerFactory.getLogger(ControllerLogs.class);
    private final RequestUtil requestUtil;

    public ControllerLogs(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    @Pointcut(value = "execution(public * com.boot.teach.web.controllers.*.*(..))")
    public void pointCut(){
    }

    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        String methodName = requestUtil.methodName(joinPoint);
        String ipAddr = requestUtil.getIpAddr();
        String paramJson = requestUtil.paramJson(joinPoint);
        logger.info(String.format("进入处理方法{%s}",methodName));
        logger.info(String.format("请求地址{%s}",ipAddr));
        logger.info(String.format("参数值{%s}",paramJson));
    }

    @Around(value = "pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature =  (MethodSignature)proceedingJoinPoint.getSignature();
        //获取到方法
        Method method = methodSignature.getMethod();
        //获取方法参数
        Parameter[] parameters = method.getParameters();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < parameters.length; i++) {
            logger.info(String.format("参数为{%s}",parameters[i]));
        }
        Object proceed = proceedingJoinPoint.proceed();
        logger.info(String.format("执行处理方法{%s},共耗时{%s}",method.getName(),System.currentTimeMillis()-startTime));
    }
}
