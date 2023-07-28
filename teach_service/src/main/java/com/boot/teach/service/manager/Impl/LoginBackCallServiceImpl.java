package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dao.manager.LoginBackCall;
import com.boot.teach.model.manager.TeachSchoolManage;
import com.boot.teach.service.manager.LoginBackCallService;
import org.springframework.stereotype.Service;

@Service
public class LoginBackCallServiceImpl extends ServiceImpl<LoginBackCall,TeachSchoolManage> implements LoginBackCallService {
    private final LoginBackCall loginBackCall;

    public LoginBackCallServiceImpl(LoginBackCall loginBackCall) {
        this.loginBackCall = loginBackCall;
    }

    @Override
    public ServerResponseEntity backCall(String uuid) {
        LambdaQueryWrapper<TeachSchoolManage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachSchoolManage::getSchoolManagerUid,uuid);
        TeachSchoolManage teachSchoolManage = loginBackCall.selectOne(queryWrapper);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),teachSchoolManage);
    }
}
