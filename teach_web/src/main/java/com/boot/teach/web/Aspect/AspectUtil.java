package com.boot.teach.web.Aspect;

import com.alibaba.fastjson.JSONObject;
import com.boot.teach.common.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * 日志打印切面
 * 切入到所有的service方法中
 */
@Aspect
@Component
public class AspectUtil {
    private final RequestUtil requestUtil;
    private Logger logger = LoggerFactory.getLogger(AspectUtil.class);
    public AspectUtil(RequestUtil requestUtil) {
        this.requestUtil = requestUtil;
    }

    /**
     * 切入点
     */
    @Pointcut("execution(public * com.boot.teach.service.*.impl(..))")
    public void pointCut(){
    }
    /**
     * 环绕通知-前置通知
     */
    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint){
        logger.info(String.format("请求路径：{%s}",requestUtil.getIpAddr()));
        logger.info(String.format("进入处理方法：{%s}",requestUtil.methodName(joinPoint)));
        logger.info(String.format("请求参数：{%s}",requestUtil.paramJson(joinPoint)));
    }
    @Around(value = "pointCut()")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //执行返回结果
        Object proceed = proceedingJoinPoint.proceed();
        logger.info(String.format("执行处理方法：{%s},共耗时：{%s}",
                requestUtil.methodName(proceedingJoinPoint),
                System.currentTimeMillis()-startTime));
        logger.info(String.format("执行处理方法：{%s},处理结果：{%s}",
                requestUtil.methodName(proceedingJoinPoint),
                JSONObject.toJSONString(proceed)));
    }
    /**
     * 异常处理
     */
    @AfterThrowing(throwing = "ex",value = "pointCut()")
    public void afterThrow(Throwable ex){
        logger.error(String.format("发送异常：{%s}",ex.toString()));
    }
}
