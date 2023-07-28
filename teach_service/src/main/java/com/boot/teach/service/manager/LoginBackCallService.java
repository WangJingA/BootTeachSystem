package com.boot.teach.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.model.manager.TeachSchoolManage;

public interface LoginBackCallService extends IService<TeachSchoolManage> {
    public ServerResponseEntity backCall(String uuid);
}
