package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dao.manager.MajorManage;
import com.boot.teach.dto.major.CreateMajorDTO;
import com.boot.teach.dto.major.QueryMajorDTO;
import com.boot.teach.model.school.TeachMajor;
import com.boot.teach.service.manager.MajorManageService;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 专业管理service
 */
@Service
public class MajorManageServiceImpl extends ServiceImpl<MajorManage, TeachMajor> implements MajorManageService {
    private final MajorManage majorManage;

    private final MapperFactory mapperFactory;

    public MajorManageServiceImpl(MajorManage majorManage, MapperFactory mapperFactory) {
        this.majorManage = majorManage;
        this.mapperFactory = mapperFactory;
    }

    /**
     * add major
     * @param majorDTO
     * @return
     */
    @Override
    public ServerResponseEntity addMajor(CreateMajorDTO majorDTO) {
        LambdaQueryWrapper<TeachMajor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachMajor::getMajorName,majorDTO.getMajorName());
        List<TeachMajor> teachMajors = majorManage.selectList(queryWrapper);
        if (teachMajors != null & teachMajors.size()>0){
            return ServerResponseEntity.fail(ResponseEnum.ALREADY_EXIST_MAJOR);
        }
        mapperFactory.classMap(CreateMajorDTO.class,TeachMajor.class)
                .byDefault()
                .register();
        TeachMajor teachMajor = mapperFactory.getMapperFacade().map(majorDTO, TeachMajor.class);
        teachMajor.setUuid(UUID.randomUUID().toString());
        Date date = new Date();
        teachMajor.setCreateTime(date);
        teachMajor.setUpdateTime(date);
        int insert = majorManage.insert(teachMajor);
        if (insert>0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), ExceptionMessageConstance.UPDATE_SUCCESS);
        }
        return ServerResponseEntity.fail(ResponseEnum.EXCEPTION);
    }

    /**
     * update major
     * @param createMajorDTO
     * @return
     */
    @Override
    public ServerResponseEntity updateMajor(CreateMajorDTO createMajorDTO) {
        mapperFactory.classMap(CreateMajorDTO.class,TeachMajor.class)
                .byDefault()
                .register();
        TeachMajor teachMajor = mapperFactory.getMapperFacade().map(createMajorDTO, TeachMajor.class);
        LambdaUpdateWrapper<TeachMajor> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeachMajor::getUuid,createMajorDTO.getUuid());
        Date date = new Date();
        teachMajor.setUpdateTime(date);
        int insert = majorManage.update(teachMajor,updateWrapper);
        if (insert>0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), ExceptionMessageConstance.UPDATE_SUCCESS);
        }
        return ServerResponseEntity.fail(ResponseEnum.EXCEPTION);

    }

    /**
     * delete major
     * @param uuid
     * @return
     */
    @Override
    public ServerResponseEntity delMajor(String uuid) {
        LambdaQueryWrapper<TeachMajor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachMajor::getUuid,uuid);
        int delete = majorManage.delete(queryWrapper);
        if (delete>0){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), ExceptionMessageConstance.UPDATE_SUCCESS);
        }
        return ServerResponseEntity.fail(ResponseEnum.EXCEPTION);
    }

    /**
     * query major
     * @param majorDTO
     * @return
     */
    @Override
    public ServerResponseEntity listMajor(QueryMajorDTO majorDTO) {
        LambdaQueryWrapper<TeachMajor> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件-【major_name,department_uid,uuid】
        if (!"".equals(majorDTO.getUuid()) && !Objects.isNull(majorDTO.getUuid())){
            queryWrapper.eq(TeachMajor::getUuid,majorDTO.getUuid());
        }
        if (!"".equals(majorDTO.getMajorName()) && !Objects.isNull(majorDTO.getMajorName())){
            queryWrapper.like(TeachMajor::getMajorName,majorDTO.getMajorName());
        }
        if (!"".equals(majorDTO.getDepartmentUid()) && !Objects.isNull(majorDTO.getDepartmentUid())){
            queryWrapper.eq(TeachMajor::getDepartmentUid,majorDTO.getDepartmentUid());
        }
        //分页
        List<TeachMajor> teachMajors = majorManage.selectList(queryWrapper);
        int size = (majorDTO.getPage() - 1) * majorDTO.getPageSize() > 0 ? (majorDTO.getPage() - 1) * majorDTO.getPageSize() : 0;
        List<TeachMajor> majorList = teachMajors.stream()
                .skip(size)
                .limit(majorDTO.getPageSize())
                .collect(Collectors.toList());
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),majorList);
    }
}
