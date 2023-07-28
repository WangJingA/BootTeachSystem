package com.boot.teach.vo.manager.manager;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "创建专业VO")
public class CreateMajorVO {
    String uuid;

    String majorName;

    String departmentUid;

    String majorIcon;

    String description;
}
