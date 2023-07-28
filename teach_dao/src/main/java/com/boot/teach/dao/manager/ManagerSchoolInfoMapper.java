package com.boot.teach.dao.manager;

import com.boot.teach.model.school.SchoolImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManagerSchoolInfoMapper {
    public int schoolImage(SchoolImage schoolImage);

    public List<SchoolImage> selectImages(String schoolUid);

    public int deleteImages(String uuid);

    public SchoolImage existImage(String schoolUid);

    public int updateImage(SchoolImage schoolImage);
}
