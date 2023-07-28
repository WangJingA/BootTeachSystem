package com.boot.teach.model.school;

import com.boot.teach.model.manager.TeachManager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.List;

import java.util.Date;

@Data
@ApiModel("学校")
@NoArgsConstructor
@AllArgsConstructor
public class TeachSchool {
    @ApiModelProperty("uuid")
    String uuid;

    @ApiModelProperty("学校名")
    String schoolName;

    @ApiModelProperty("学校地址")
    String schoolAddress;

    @ApiModelProperty("学校图标")
    String schoolIcon;

    @ApiModelProperty("学校等级")
    String schoolDegree;

    @ApiModelProperty("学校介绍")
    String schoolIntroduction;

    @ApiModelProperty(value = "学校编码")
    String schoolDecode;

    @ApiModelProperty(value = "状态")
    String status;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date createTime;

    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    Date updateTime;
}
