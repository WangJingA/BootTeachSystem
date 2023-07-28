package com.boot.teach.vo.school;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "SearchSchoolVO")
public class SearchSchoolVO {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "学校名")
    String schoolName;

    @ApiModelProperty(value = "状态")
    String status;

    @ApiModelProperty(value = "当前页")
    int page;

    @ApiModelProperty(value = "页的大小")
    int size;
}
