package com.boot.teach.web.Aspect;

import cn.hutool.core.util.StrUtil;
import com.boot.teach.common.annotations.RequestLock;
import com.boot.teach.common.util.RedisUtil;
import com.boot.teach.service.formlock.RequestKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author : hzx
 * @date : 2023/7/7 18:00
 * @RequestLock注解的控制类 环绕监听
 */
@Aspect
@Configuration
public class RequestLockUtil {
    //切入点为使用了@RequestLock注解的控制类
    final RedisUtil redisUtil;
    final RequestKeyGenerator requestKeyGenerator;
    final StringRedisTemplate stringRedisTemplate;
    final Logger logger = LoggerFactory.getLogger(RequestLockUtil.class);

    @Autowired
    public RequestLockUtil(RedisUtil redisUtil, RequestKeyGenerator requestKeyGenerator, StringRedisTemplate stringRedisTemplate) {
        this.redisUtil = redisUtil;
        this.requestKeyGenerator = requestKeyGenerator;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Pointcut("execution (public * * (..))&& @annotation(com.boot.teach.common.annotations.RequestLock)")
    public void pointCut(){
    }

    @Around(value = "pointCut()")
    public Object interceptor(ProceedingJoinPoint proceedingJoinPoint){
        logger.info(String.format("进入处理方法{%s}","RequestLock"));
        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        if (StrUtil.isEmptyIfStr(requestLock.prefix())){
            throw new  IllegalArgumentException("重复提交前缀不能为空");
        }
        String lockKey = requestKeyGenerator.getLockKey(proceedingJoinPoint);
        final Boolean success = stringRedisTemplate.execute(
                (RedisCallback<Boolean>) connection -> connection.set(lockKey.getBytes(), new byte[0],
                        Expiration.from(requestLock.expire(), requestLock.timeUnit())
                        , RedisStringCommands.SetOption.SET_IF_ABSENT));
        if (!success) {
            throw new RuntimeException("您的操作太快了,请稍后重试");
        }
        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("系统异常");
//            return "系统异常";
        }
    }
}
