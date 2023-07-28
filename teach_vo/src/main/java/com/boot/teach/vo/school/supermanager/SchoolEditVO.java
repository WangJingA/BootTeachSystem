package com.boot.teach.vo.school.supermanager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SchoolEditVO-管理员学校信息修改类")
public class SchoolEditVO {
    @ApiModelProperty(value = "uuid")
     String schoolCode;

    @ApiModelProperty(value = "学校地址")
     String schoolAddress;

    @ApiModelProperty(value = "学校图标")
     String icon;

    @ApiModelProperty(value = "状态")
     String status;

    @ApiModelProperty(value = "学校名")
     String schoolName;
}
