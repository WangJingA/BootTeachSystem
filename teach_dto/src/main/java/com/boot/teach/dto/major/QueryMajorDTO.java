package com.boot.teach.dto.major;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "查询专业DTO")
public class QueryMajorDTO {
    String uuid;
    String majorName;
    String departmentUid;
    int page;
    int pageSize;
}
