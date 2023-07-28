package com.boot.teach.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "班级课程表")
public class TeachClassCourse {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "课程uid")
    String courseUid;

    @ApiModelProperty(value = "班级uid")
    String classUid;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
