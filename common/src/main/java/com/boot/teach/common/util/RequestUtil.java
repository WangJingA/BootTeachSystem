package com.boot.teach.common.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : hzx
 * @date : 2023/7/7 15:19
 * 请求地址、参数处理类
 */
@Component
public class RequestUtil {
    //注入request
    @Autowired
    HttpServletRequest request;

    /**
     * 获取请求的IP地址
     * @return
     */
    public String getIpAddr(){
        String ipAddr = null;
        String ipAddress = request.getHeader("x-forwarded-for");
        //proxy
        if (ipAddress.length()==0 || ipAddress == null || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        //wl proxy
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        //remote ip
        if (ipAddress.length()==0 || ipAddress == null || "unknown".equalsIgnoreCase(ipAddress)){
            ipAddress = request.getRemoteAddr();
        }
        //多个地址的情况是代理地址，取第一个地址
        if (ipAddress.length()>15 && ipAddress != null){
            ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
        }
        //本地地址
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)){
            ipAddress = "127.0.0.1";
        }
        return  ipAddress;
    }

    /**
     * 方法名
     * @param joinPoint
     * @return
     */
    public String methodName(JoinPoint joinPoint){
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String name = method.getName();
        return name;
    }

    /**
     * 参数名和值序列化
     * @return
     */
    public String paramJson(JoinPoint proceedingJoinPoint){
        //参数名
        String[] paramName = ((MethodSignature)proceedingJoinPoint.getSignature()).getParameterNames();
        //参数值
        Object[] paramValue = proceedingJoinPoint.getArgs();
        return this.paramAndValueJson(paramName,paramValue);
    }
    /**
     * 参数和值json化方法
     */
    public String paramAndValueJson(String[] paramName,Object[] paramValue){
        Map<String,Object> requestParam = new HashMap<>();
        for (int i=0;i<paramName.length;i++){
            Object value = paramValue[i];
            if (value instanceof HttpServletRequest || value instanceof HttpServletResponse){
                continue;
            }
            if (value instanceof MultipartFile){
                value = ((MultipartFile) value).getOriginalFilename();
            }
            requestParam.put(paramName[i],value);
        }
        return JSONObject.toJSONString(requestParam);
    }
}
