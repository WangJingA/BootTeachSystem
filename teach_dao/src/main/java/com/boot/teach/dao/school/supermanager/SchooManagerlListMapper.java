package com.boot.teach.dao.school.supermanager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.model.school.TeachSchool;
import com.boot.teach.model.school.TeachSchoolManagerList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SchooManagerlListMapper extends BaseMapper<TeachSchoolManagerList> {
    List<TeachSchoolManagerList> listSchoolManager(ListManagerDTO managerDTO);
}
