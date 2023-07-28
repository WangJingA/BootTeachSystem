package com.boot.teach.vo.school.supermanager;

import com.boot.teach.vo.annotations.RequestKeyParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "超级管理员添加学校接收类")
public class AddSchoolVO {
    @RequestKeyParam(name = "schoolName")
    @ApiModelProperty(value = "学校名")
    String schoolName;

    @ApiModelProperty(value = "学校地址")
    String schoolAddress;

    @ApiModelProperty(value = "学校描述")
    String schoolDesc;

    @ApiModelProperty(value = "学校图标")
    String icon;
}
