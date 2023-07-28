package com.boot.teach.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "课程大纲章节内容")
public class CourseSummaryContent {
    @ApiModelProperty(value = "id")
    int id;

    @ApiModelProperty(value = "章节id")
    int topicId;

    @ApiModelProperty(value = "内容父级id")
    int parentId;

    @ApiModelProperty(value = "内容子标题")
    String contentTitle;

    @ApiModelProperty(value = "内容标题")
    String title;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
