package com.boot.teach.common.util;

import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;

public class PublicUtils {
    public static ServerResponseEntity judegeChange(int status){
        if (status > 0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),null);
        }
        return ServerResponseEntity.fail(ResponseEnum.EXCEPTION);
    }

    public static ServerResponseEntity judegeChange(int status,Object data){
        if (status > 0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),data);
        }
        return ServerResponseEntity.fail(ResponseEnum.EXCEPTION);
    }
}
