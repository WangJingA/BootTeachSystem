package com.boot.teach.model.teacher;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "教师操作日志表")
public class TeacherLogs {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "教师uid")
    String teacherUid;

    @ApiModelProperty(value = "操作日志")
    String teacherLogs;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
