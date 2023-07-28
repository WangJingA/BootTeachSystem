package com.boot.teach.dao.school.supermanager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.model.manager.TeachSchoolManage;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface SchoolManagerLinkMapper extends BaseMapper<TeachSchoolManage> {
    boolean addSchoolManagerLink(TeachSchoolManage teachSchoolManage);
}
