package com.boot.teach.service.supermanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.CreateManagerDTO;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.model.manager.TeachManager;


public interface SchoolManagerService extends IService<TeachManager> {


    ServerResponseEntity randomAccount();

    ServerResponseEntity randomPass();

    ServerResponseEntity addManager(CreateManagerDTO managerDTO);

    ServerResponseEntity listManagerBySchoolUid(ListManagerDTO listManagerDTO);

    ServerResponseEntity editManager(CreateManagerDTO createManagerDTO);
}
