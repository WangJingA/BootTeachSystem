package com.boot.teach.vo.manager.manager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "修改视频VO")
public class EditVideoVO {
    @ApiModelProperty(value = "视频uid")
    String devUid;
    @ApiModelProperty(value = "视频名字")
    String devName;
    @ApiModelProperty(value = "视频描述")
    String devDesc;
    @ApiModelProperty(value = "视频封面")
    String devImage;
    @ApiModelProperty(value = "视频上下架")
    int devStatus;
}
