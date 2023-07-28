package com.boot.teach.model.school;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("班级表")
public class TeachClass {
    @ApiModelProperty("uuid")
    String uuid;

    @ApiModelProperty("班级名")
    String className;

    @ApiModelProperty("班级所在专业")
    String classMajor;

    @ApiModelProperty(value = "学校uid")
    String classSchool;

    @ApiModelProperty(value = "班级所在学院")
    String classDepartment;

    @ApiModelProperty(value = "班级人数")
    int classPersonNumber;

    @ApiModelProperty(value = "学院uid")
    String classDepartmentUid;

    @ApiModelProperty(value = "专业uid")
    String classMajorUid;

    @ApiModelProperty(value = "班级创建人")
    String classCreateMan;

    @ApiModelProperty(value = "班级状态:0创建，1：销毁")
    int classStatus;

    @ApiModelProperty(value = "班级图标")
    String classIcon;

    @ApiModelProperty(value = "班级描述")
    String classDesc;

    @ApiModelProperty("创建时间")
    Date  createTime;

    @ApiModelProperty("更新时间")
    Date updateTime;
}
