package com.boot.teach.dao.passuse;

import com.boot.teach.model.school.TeachClass;
import com.boot.teach.model.school.TeachDepartment;
import com.boot.teach.model.school.TeachMajor;
import com.boot.teach.model.school.TeachSchool;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PassUseMapper {
    List<TeachSchool> schoolList();

    List<TeachDepartment> depList(String schoolUid);

    List<TeachMajor> majorList(String departmentUid);

    List<TeachClass> classList(String majorUid);
}
