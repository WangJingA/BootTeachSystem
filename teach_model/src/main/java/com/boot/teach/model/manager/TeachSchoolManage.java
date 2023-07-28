package com.boot.teach.model.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "学校管理员表")
public class TeachSchoolManage {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "学校uid")
    String schoolUid;

    @ApiModelProperty(value = "管理员uid")
    String schoolManagerUid;

    @ApiModelProperty(value = "状态")
    int status;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
