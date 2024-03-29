package com.boot.teach.model.student;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "学生表")
public class TeachStudent {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "姓名")
    String sudentName;

    @ApiModelProperty(value = "学号")
    String sudentSchoolNumber;

    @ApiModelProperty(value = "班级")
    String sudentClass;

    @ApiModelProperty(value = "学校")
    String sudentCollege;

    @ApiModelProperty(value = "性别")
    String sudentSex;

    @ApiModelProperty(value = "专业")
    String sudentMajor;

    @ApiModelProperty(value = "学院")
    String sudentDepartment;

    @ApiModelProperty(value = "手机号")
    String sudentPhone;

    @ApiModelProperty(value = "邮箱")
    String sudentMail;


    @ApiModelProperty(value = "头像")
    String sudentIcon;

    @ApiModelProperty(value = "账号")
    String sudentAccout;

    @ApiModelProperty(value = "密码")
    String sudentPassword;


    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "修改时间")
    Date updateTime;
}
