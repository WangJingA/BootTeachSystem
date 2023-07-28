package com.boot.teach.dao.school.supermanager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.dto.school.supermanager.CreateManagerDTO;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.model.manager.TeachManager;
import com.boot.teach.model.manager.TeachSchoolManage;
import com.boot.teach.model.school.supermanager.TeachAddSchoolManage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SchoolManagerMapper extends BaseMapper<TeachManager> {


    boolean addSchoolManager(TeachManager addSchoolManage);
}
