package com.boot.teach.model.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


@Data
@ApiModel(value = "教师课程表")
public class TeacherCourse {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "教师uid")
    String teacherUid;

    @ApiModelProperty(value = "课程uid")
    String courseUid;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
