package com.boot.teach.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "用户登录统一接收类")
public class LoginDTO implements Serializable {
    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "密码")
    String password;

    @ApiModelProperty(value = "验证码")
    String checkCode;
}
