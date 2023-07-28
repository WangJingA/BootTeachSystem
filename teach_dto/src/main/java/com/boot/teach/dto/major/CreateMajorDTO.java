package com.boot.teach.dto.major;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "创建专业DTO")
public class CreateMajorDTO {
    String uuid;

    String majorName;

    String departmentUid;

    String majorIcon;

    String description;
}
