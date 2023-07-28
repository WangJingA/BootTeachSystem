package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dao.manager.DepartmentManage;
import com.boot.teach.dto.manager.CreateDepartmentDTO;
import com.boot.teach.dto.school.manager.ListDepartmentDTO;
import com.boot.teach.model.school.TeachDepartment;
import com.boot.teach.service.manager.DepartmentManageService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartmentManageServiceImpl extends ServiceImpl<DepartmentManage, TeachDepartment> implements DepartmentManageService {
    private final DepartmentManage departmentManageMapper;
    private final MapperFactory mapperFactory;
    public DepartmentManageServiceImpl(DepartmentManage departmentManageMapper, MapperFactory mapperFactory) {
        this.departmentManageMapper = departmentManageMapper;
        this.mapperFactory = mapperFactory;
    }

    @Override
    public ServerResponseEntity createDepartment(CreateDepartmentDTO departmentDTO) {
        mapperFactory.classMap(TeachDepartment.class,CreateDepartmentDTO.class)
                .byDefault()
                .register();
        TeachDepartment teachDepartment = mapperFactory.getMapperFacade().map(departmentDTO, TeachDepartment.class);
        teachDepartment.setUuid(UUID.randomUUID().toString());
        int insert = departmentManageMapper.insert(teachDepartment);
        if (insert>0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), insert);
        }else {
            return ServerResponseEntity.fail(ResponseEnum.EXCEPTION,insert);
        }
    }

    /**
     * 学校专业表格数据
     * @param departmentDTO
     * @return
     */
    @Override
    public ServerResponseEntity listDepartment(ListDepartmentDTO departmentDTO) {
        Map<String,Object> resultMap = new HashMap<>();
        //参数校验
        if (Objects.isNull(departmentDTO)){
            //非法参数异常
            throw  new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        LambdaQueryWrapper<TeachDepartment> queryWrapper = new LambdaQueryWrapper<>();
        if ((!Objects.isNull(departmentDTO.getUuid()))&&(departmentDTO.getUuid() != "")){
            queryWrapper.eq(TeachDepartment::getUuid,departmentDTO.getUuid());
        }
        if ((!Objects.isNull(departmentDTO.getDepartmentName()))&&(departmentDTO.getDepartmentName() != "")){
            queryWrapper.like(TeachDepartment::getDepartmentName,departmentDTO.getDepartmentName());
        }
        List<TeachDepartment> teachDepartments = departmentManageMapper.selectList(queryWrapper);
        int size = teachDepartments == null ? 0: teachDepartments.size();
        resultMap.put("total",size);
        if (!Objects.isNull(departmentDTO.getPage())&&!Objects.isNull(departmentDTO.getPageSize())){
            int page = departmentDTO.getPage() > 0 ? departmentDTO.getPage() : 1;
            teachDepartments = teachDepartments.stream().skip((page-1)*departmentDTO.getPageSize())
                    .limit(departmentDTO.getPageSize()).collect(Collectors.toList());
        }
        resultMap.put("teachDepartment",teachDepartments);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),resultMap);
    }
}
