package com.boot.teach.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.manager.CreateClassDTO;
import com.boot.teach.dto.manager.EditClassDTO;
import com.boot.teach.model.school.TeachClass;
import com.boot.teach.vo.manager.manager.QueryClassVO;
import org.springframework.stereotype.Service;

/**
 * @author : hzx
 * @date : 2023/7/28
 * @description : 管理员班级管理
 */

public interface ClassManageService extends IService<TeachClass> {
    ServerResponseEntity createClass(CreateClassDTO createClassDTO);

    ServerResponseEntity classList(QueryClassVO queryClassVO);

    ServerResponseEntity classEdit(EditClassDTO editClassDTO) throws Exception;

    ServerResponseEntity delClass(String uuid);

}
