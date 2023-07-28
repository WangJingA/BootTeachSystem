package com.boot.teach.service.formlock.impl;

import cn.hutool.core.util.StrUtil;
import com.boot.teach.common.annotations.RequestKeyParam;
import com.boot.teach.common.annotations.RequestLock;
import com.boot.teach.service.formlock.RequestKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author : hzx
 * @date : 2023/7/7 16:46
 * redis-key 生成器:防止表单重复提交
 */
@Service
public class RequestKeyGeneratorImpl implements RequestKeyGenerator {
    @Override
    public String getLockKey(ProceedingJoinPoint proceedingJoinPoint) {
        StringBuilder stringBuilder = new StringBuilder();
        //获取到加了RequestLock注解的方法
        Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
        //获取方法上的RequestLock类型的注解
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        //获取方法里面的所有参数值
        Object[] args = proceedingJoinPoint.getArgs();
        //获取方法里面的所有参数实际名
        Parameter[] parameters = method.getParameters();
        for (int i=0;i<parameters.length;i++){
            RequestKeyParam requestKeyParam = parameters[i].getAnnotation(RequestKeyParam.class);
            //为空不拼接
            if (requestKeyParam == null){
                continue;
            }
            stringBuilder.append(requestLock.delimiter()+requestKeyParam.name());
        }
        //拼接后为空，自定义key
        if (StrUtil.isEmptyIfStr(stringBuilder)){
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int j=0;j<parameterAnnotations.length;j++){
                Object ob = args[j];
                Field[] fields = ob.getClass().getFields();
                for (int f =0;f<fields.length;f++){
                    RequestKeyParam annotation = fields[f].getAnnotation(RequestKeyParam.class);
                    if (annotation == null){
                        continue;
                    }
                    //设置可以访问私有变量
                    fields[f].setAccessible(true);
                    stringBuilder.append(requestLock.delimiter()+fields[f]);
                }
            }
        }
        return requestLock.prefix()+stringBuilder;
    }
}
