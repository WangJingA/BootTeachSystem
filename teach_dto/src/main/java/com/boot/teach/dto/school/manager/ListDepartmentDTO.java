package com.boot.teach.dto.school.manager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "列举学院DTO")
public class ListDepartmentDTO {
    @ApiModelProperty(value = "uuid")
    String uuid;
    @ApiModelProperty(value = "学院名")
    String departmentName;
    @ApiModelProperty(value = "当前页")
    int page;
    @ApiModelProperty(value = "页的大小")
    int pageSize;
}
