package com.boot.teach.vo.manager.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员创建学院VO")
public class CreateDepartmentVO {
    @ApiModelProperty(value = "schoolUUID")
    String schoolUid;
    @ApiModelProperty(value = "departmentName")
    String departmentName;
    @ApiModelProperty(value = "departmentIcon")
    String departmentIcon;
    @ApiModelProperty(value = "description")
    String description;
}
