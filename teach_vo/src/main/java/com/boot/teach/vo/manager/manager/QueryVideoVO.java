package com.boot.teach.vo.manager.manager;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "视频搜索")
public class QueryVideoVO {
    @ApiModelProperty(value = "学校uid")
    String schoolUId;
    @ApiModelProperty(value = "学院uid")
    String depUid;
    @ApiModelProperty(value = "专业uid")
    String majUid;
    @ApiModelProperty(value = "课程uid")
    String courseUid;
    @ApiModelProperty(value = "视频上下架状态")
    int status;
    @ApiModelProperty(value = "视频口令")
    String devUid;
}
