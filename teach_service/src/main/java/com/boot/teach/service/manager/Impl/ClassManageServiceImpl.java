package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.PublicUtils;
import com.boot.teach.dao.manager.ClassManage;
import com.boot.teach.dto.manager.CreateClassDTO;
import com.boot.teach.dto.manager.EditClassDTO;
import com.boot.teach.model.school.TeachClass;
import com.boot.teach.service.manager.ClassManageService;
import com.boot.teach.vo.manager.manager.QueryClassVO;
import ma.glasnost.orika.MapperFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ServerResponseEntity classList(QueryClassVO queryClassVO) {
        LambdaQueryWrapper<TeachClass> queryWrapper = new LambdaQueryWrapper<>();
        if (!Objects.isNull(queryClassVO.getSchool()) && !"".equals(queryClassVO.getSchool())){
            queryWrapper.eq(TeachClass::getClassSchool,queryClassVO.getSchool());
        }else{
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        if (!Objects.isNull(queryClassVO.getUuid()) && !"".equals(queryClassVO.getUuid())){
            queryWrapper.eq(TeachClass::getUuid,queryClassVO.getUuid());
        }
        if (!Objects.isNull(queryClassVO.getClazzName()) && !"".equals(queryClassVO.getClazzName())){
            queryWrapper.like(TeachClass::getClassName,queryClassVO.getClazzName());
        }
        if (!Objects.isNull(queryClassVO.getMajor()) && !"".equals(queryClassVO.getMajor())){
            queryWrapper.eq(TeachClass::getClassMajorUid,queryClassVO.getMajor());
        }
        if (!Objects.isNull(queryClassVO.getDep()) && !"".equals(queryClassVO.getDep())){
            queryWrapper.eq(TeachClass::getClassDepartmentUid,queryClassVO.getDep());
        }
        List<TeachClass> teachClasses = classManage.selectList(queryWrapper);
        int skip = queryClassVO.getPage() > 0 ? (queryClassVO.getPage()-1)*queryClassVO.getPageSize() : 0;
        List<TeachClass> collect = teachClasses.stream().skip(skip).limit(queryClassVO.getPageSize()).collect(Collectors.toList());
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),collect);
    }

    @Override
    public ServerResponseEntity classEdit(EditClassDTO editClassDTO) throws Exception {
        if (Objects.isNull(editClassDTO)){
            throw  new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        LambdaQueryWrapper<TeachClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachClass::getUuid,editClassDTO.getUuid());
        TeachClass teachClass = classManage.selectOne(queryWrapper);
        //不存在即不能编辑
        if(Objects.isNull(teachClass)){
            throw  new Exception(ExceptionMessageConstance.NULL_POINTER_EXCEPTION);
        }
        teachClass.setClassDepartment(editClassDTO.getClassDepartment());
        teachClass.setClassDesc(editClassDTO.getClassDesc());
        teachClass.setClassSchool(editClassDTO.getClassSchool());
        teachClass.setClassStatus(editClassDTO.getClassStatus());
        teachClass.setClassMajor(editClassDTO.getClassMajor());
        teachClass.setClassName(editClassDTO.getClassName());
        teachClass.setClassIcon(editClassDTO.getClassIcon());
        teachClass.setClassDepartmentUid(editClassDTO.getClassDepartmentUid());
        teachClass.setClassMajorUid(editClassDTO.getClassMajorUid());
        teachClass.setUpdateTime(new Date());
        //更新
        LambdaUpdateWrapper<TeachClass> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeachClass::getUuid,teachClass.getUuid());
        int update = classManage.update(teachClass,updateWrapper);
        return PublicUtils.judegeChange(update);
    }

    @Override
    public ServerResponseEntity delClass(String uuid) {
        if (Objects.isNull(uuid)){
            throw  new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        LambdaQueryWrapper<TeachClass> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachClass::getUuid,uuid);
        int delete = classManage.delete(queryWrapper);
        return PublicUtils.judegeChange(delete);
    }
}
