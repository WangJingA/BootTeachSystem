package com.boot.teach.vo.auth;

import com.boot.teach.vo.annotations.RequestKeyParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "用户登录统一接收类")
public class LoginVO implements Serializable {
    @RequestKeyParam
    @ApiModelProperty(value = "用户名")
    String username;

    @ApiModelProperty(value = "密码")
    String password;

    @ApiModelProperty(value = "验证码")
    String checkCode;
}
