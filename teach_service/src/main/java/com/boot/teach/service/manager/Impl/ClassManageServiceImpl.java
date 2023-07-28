package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.PublicUtils;
import com.boot.teach.dao.manager.ClassManage;
import com.boot.teach.dto.manager.CreateClassDTO;
import com.boot.teach.model.school.TeachClass;
import com.boot.teach.service.manager.ClassManageService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class ClassManageServiceImpl extends ServiceImpl<ClassManage, TeachClass> implements ClassManageService {
    private final MapperFactory mapperFactory;
    private final ClassManage classManage;

    public ClassManageServiceImpl(MapperFactory mapperFactory, ClassManage classManage) {
        this.mapperFactory = mapperFactory;
        this.classManage = classManage;
    }

    @Override
    public ServerResponseEntity createClass(CreateClassDTO createClassDTO) {
        //参数校验
        if (Objects.isNull(createClassDTO)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        //转换
        mapperFactory.classMap(TeachClass.class,CreateClassDTO.class)
                .field("className","clazzName")
                .field("classMajor","major")
                .field("classSchool","school")
                .field("classIcon","icon")
                .field("classDesc","desc")
                .field("classDepartmentUid","did")
                .field("classMajorUid","mid")
                .field("classDepartment","department")
                .byDefault()
                .register();
        TeachClass teachClass = mapperFactory.getMapperFacade().map(createClassDTO, TeachClass.class);
        teachClass.setUuid(UUID.randomUUID().toString());
        teachClass.setClassStatus(0);
        Date date = new Date();
        teachClass.setCreateTime(date);
        teachClass.setUpdateTime(date);
        int insert = classManage.insert(teachClass);
        return PublicUtils.judegeChange(insert,ExceptionMessageConstance.UPDATE_SUCCESS);
    }

    @Override
    public ServerResponseEntity classList() {
        return null;
    }
}
