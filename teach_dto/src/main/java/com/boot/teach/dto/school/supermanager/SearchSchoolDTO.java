package com.boot.teach.dto.school.supermanager;

import lombok.Data;

@Data
public class SearchSchoolDTO {
    String uuid;

    String schoolName;

    String status;

    int page;

    int size;
}
