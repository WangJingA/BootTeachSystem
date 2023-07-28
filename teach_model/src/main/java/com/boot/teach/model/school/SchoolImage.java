package com.boot.teach.model.school;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "学校首页轮播图")
public class SchoolImage {
    @ApiModelProperty(value = "uuid")
    String uuid;
    @ApiModelProperty(value = "学校uid")
    String schoolUid;
    @ApiModelProperty(value = "图片地址，多个图片使用 ; 分隔")
    String imageUrl;
    Date createTime;
    Date updateTime;
}
