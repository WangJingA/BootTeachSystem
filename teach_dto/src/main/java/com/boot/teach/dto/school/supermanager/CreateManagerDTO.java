package com.boot.teach.dto.school.supermanager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "超级管理员添加学校管理员")
public class CreateManagerDTO {
    @ApiModelProperty(value = "学校管理员名")
    String teachManager;

    @ApiModelProperty(value = "学校管理员电话")
    String teachManagerPhone;

    @ApiModelProperty(value = "学校管理员邮箱")
    String teachManagerMail;

    @ApiModelProperty(value = "学校uuid")
    String schoolUuid;

    @ApiModelProperty(value = "状态")
    int teachManagerStatus;

    @ApiModelProperty(value = "学校管理员账号")
    String teachManagerAccount;

    @ApiModelProperty(value = "学校管理员密码")
    String teachManagerPassword;
}
