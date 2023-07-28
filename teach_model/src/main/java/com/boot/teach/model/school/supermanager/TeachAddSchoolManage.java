package com.boot.teach.model.school.supermanager;

import com.boot.teach.model.manager.TeachManager;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@ApiModel(value = "学校管理员映射")
public class TeachAddSchoolManage {
    @ApiModelProperty(value = "uuid")
    String uuid;

    @ApiModelProperty(value = "学校uid")
    String schoolUid;

    @ApiModelProperty(value = "管理员列表")
    List<TeachManager> list;

    @ApiModelProperty(value = "创建时间")
    Date createTime;

    @ApiModelProperty(value = "更新时间")
    Date updateTime;
}
