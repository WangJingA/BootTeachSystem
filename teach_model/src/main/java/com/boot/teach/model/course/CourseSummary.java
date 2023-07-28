package com.boot.teach.model.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "课程大纲")
public class CourseSummary {
    @ApiModelProperty(value = "id")
    int id;

    @ApiModelProperty(value = "课程uid")
    String courseUid;

    @ApiModelProperty(value = "教师uid")
    String teacherUid;

    @ApiModelProperty(value = "教学目的")
    String teachDirection;

    @ApiModelProperty(value = "教学难点")
    String teachTrap;

    @ApiModelProperty(value = "章节标题")
    String topicTitle;

    @ApiModelProperty(value = "内容id,多个内容用-连接")
    String contentId;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
