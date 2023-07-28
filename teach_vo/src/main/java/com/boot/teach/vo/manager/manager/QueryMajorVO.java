package com.boot.teach.vo.manager.manager;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "查询专业vo")
public class QueryMajorVO {
    String uuid;
    String majorName;
    String departmentUid;
    int page;
    int pageSize;
}
