package com.boot.teach.model.school;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "班级学生表")
public class TeachClassStudent {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "班级uid")
    String classUid;

    @ApiModelProperty(value = "学生uid")
    String studentUid;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
