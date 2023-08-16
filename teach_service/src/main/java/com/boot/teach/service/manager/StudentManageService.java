package com.boot.teach.service.manager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.template.StudentTemplate;
import com.boot.teach.model.student.TeachStudent;

import java.util.List;

public interface StudentManageService extends IService<TeachStudent> {
    /**
     * 导入
     * @param stuList
     * @return
     */
    public ServerResponseEntity importStuList(List<StudentTemplate> stuList);
}
