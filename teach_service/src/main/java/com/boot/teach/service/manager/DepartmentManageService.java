package com.boot.teach.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.manager.CreateDepartmentDTO;
import com.boot.teach.dto.school.manager.ListDepartmentDTO;
import com.boot.teach.model.school.TeachDepartment;

/**
 * @author : hzx
 * @date : 2023/7/21 11:43
 * @description : 学院管理
 */
public interface DepartmentManageService extends IService<TeachDepartment> {
    ServerResponseEntity createDepartment(CreateDepartmentDTO departmentDTO);

    ServerResponseEntity listDepartment(ListDepartmentDTO departmentDTO);


}
