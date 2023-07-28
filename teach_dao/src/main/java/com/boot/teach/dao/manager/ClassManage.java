package com.boot.teach.dao.manager;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.teach.model.school.TeachClass;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClassManage extends BaseMapper<TeachClass> {
}
