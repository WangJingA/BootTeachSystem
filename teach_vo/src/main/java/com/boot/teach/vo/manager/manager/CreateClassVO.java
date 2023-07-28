package com.boot.teach.vo.manager.manager;

import com.boot.teach.vo.annotations.RequestKeyParam;
import lombok.Data;

@Data
public class CreateClassVO {
    @RequestKeyParam(name = "clazzName")
    String clazzName;
    String major;
    String department;
    String icon;
    String school;
    String mid;
    String did;
    String desc;
}
