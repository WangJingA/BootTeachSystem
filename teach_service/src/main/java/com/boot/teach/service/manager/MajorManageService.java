package com.boot.teach.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.major.CreateMajorDTO;
import com.boot.teach.dto.major.QueryMajorDTO;
import com.boot.teach.model.school.TeachMajor;

/**
 * 专业管理
 */
public interface MajorManageService extends IService<TeachMajor> {
    //增加
    ServerResponseEntity addMajor(CreateMajorDTO createMajorDTO);
    //更新
    ServerResponseEntity updateMajor(CreateMajorDTO createMajorDTO);
    //删除
    ServerResponseEntity delMajor(String uuid);
    //查询
    ServerResponseEntity listMajor(QueryMajorDTO majorDTO);
}
