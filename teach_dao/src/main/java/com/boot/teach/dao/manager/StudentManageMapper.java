package com.boot.teach.dao.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.model.auth.TeachUser;
import com.boot.teach.model.manager.TeachSchoolManage;
import com.boot.teach.model.student.TeachStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentManageMapper extends BaseMapper<TeachStudent> {
    /**
     * 批量插入学生表
     * @param stuList
     * @return
     */
    public int inserStuBath(@Param("list") List<TeachStudent> stuList);

    /**
     * 获取学校uid
     * @return
     */
    public String getSchoolUid(@Param("userUid") String userUid);

    public int insertUsBath(@Param("list")List<TeachUser> userList);
}
