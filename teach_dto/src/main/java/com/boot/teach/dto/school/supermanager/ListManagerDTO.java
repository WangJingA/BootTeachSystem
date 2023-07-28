package com.boot.teach.dto.school.supermanager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "学校管理员条件筛选")
public class ListManagerDTO {
    @ApiModelProperty(value = "学校管理员uuid")
    String uuid;
    @ApiModelProperty(value = "管理员名")
    String managerName;
    @ApiModelProperty(value = "学校uid")
    String schoolUid;
    @ApiModelProperty(value = "页码")
    int pageIndex;
    @ApiModelProperty(value = "页大小")
    int pageSize;
}
