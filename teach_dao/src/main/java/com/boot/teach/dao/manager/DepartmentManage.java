package com.boot.teach.dao.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.model.school.TeachDepartment;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentManage extends BaseMapper<TeachDepartment> {
}
