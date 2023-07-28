package com.boot.teach.service.supermanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.SearchSchoolDTO;
import com.boot.teach.model.school.TeachSchool;

public interface SchoolListService extends IService<TeachSchool> {
    ServerResponseEntity SearchAndListSchool(SearchSchoolDTO searchSchoolDTO);
}
