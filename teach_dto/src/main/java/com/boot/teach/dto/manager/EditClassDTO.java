package com.boot.teach.dto.manager;

import lombok.Data;

@Data
public class EditClassDTO {
    String uuid;
    String className;
    String classMajor;
    String classSchool;
    String classIcon;
    int classStatus;
    String classDepartment;
    String classDepartmentUid;
    String classMajorUid;
    String classDesc;
}
