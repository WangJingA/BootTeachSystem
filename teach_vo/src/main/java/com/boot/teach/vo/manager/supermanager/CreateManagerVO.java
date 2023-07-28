package com.boot.teach.vo.manager.supermanager;

import com.boot.teach.vo.annotations.RequestKeyParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "超级管理员添加学校管理员")
public class CreateManagerVO {
    @ApiModelProperty(value = "学校管理员名")
    String teachManager;

    @ApiModelProperty(value = "学校管理员电话")
    String teachManagerPhone;

    @ApiModelProperty(value = "学校管理员邮箱")
    String teachManagerMail;

    @ApiModelProperty(value = "学校uuid")
    String schoolUuid;

    @RequestKeyParam
    @ApiModelProperty(value = "管理员uuid")
    String uuid;

    @ApiModelProperty(value = "学校管理员账号")
    String teachManagerAccount;

    @ApiModelProperty(value = "学校管理员密码")
    String teachManagerPassword;

    @ApiModelProperty(value = "学校管理员状态")
    int teachManagerStatus;
}
