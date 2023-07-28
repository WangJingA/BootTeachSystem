package com.boot.teach.model.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "超级管理员表")
public class SuperManager {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "手机号")
    String phone;

    @ApiModelProperty(value = "邮箱地址")
    String mail;

    @ApiModelProperty(value = "超级管理员名")
    String superManager;

    @ApiModelProperty(value = "账号")
    String account;

    @ApiModelProperty(value = "密码")
    String password;

    @ApiModelProperty(value = "头像")
    String icon;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
