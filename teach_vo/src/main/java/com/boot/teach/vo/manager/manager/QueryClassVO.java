package com.boot.teach.vo.manager.manager;

import lombok.Data;

@Data
public class QueryClassVO {
    String uuid;
    String clazzName;
    String major;
    String dep;
    String school;
    int page;
    int pageSize;
}
