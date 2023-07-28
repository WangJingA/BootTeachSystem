package com.boot.teach.service.supermanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.PageToSize;
import com.boot.teach.dao.school.supermanager.SchoolListMapper;
import com.boot.teach.dto.school.supermanager.SearchSchoolDTO;
import com.boot.teach.model.school.TeachSchool;
import com.boot.teach.service.supermanager.SchoolListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class SchoolListServiceImpl extends ServiceImpl<SchoolListMapper, TeachSchool> implements SchoolListService {
    private final SchoolListMapper schoolListMapper;

    private Logger logger =  LoggerFactory.getLogger(SchoolInfoServiceImpl.class);
    public SchoolListServiceImpl(SchoolListMapper schoolListMapper) {
        this.schoolListMapper = schoolListMapper;
    }

    @Override
    public ServerResponseEntity SearchAndListSchool(SearchSchoolDTO searchSchoolDTO) {
        Map<String,Object> resultMap = new HashMap<>();
        List<TeachSchool> filterList = null;
        //条件查询
        QueryWrapper queryWrapper = new QueryWrapper();
        List<TeachSchool> teachSchools = schoolListMapper.selectList(queryWrapper);
        resultMap.put("totalData",teachSchools.size());
        if (!"".equals(searchSchoolDTO.getSchoolName()) && !Objects.isNull(teachSchools)){
            filterList = teachSchools.stream()
                    .filter(sc->sc.getSchoolName()
                            .contains(searchSchoolDTO.getSchoolName())).collect(Collectors.toList());
            teachSchools = filterList;

        }
        if (!"".equals(searchSchoolDTO.getUuid()) && !Objects.isNull(teachSchools)){
            filterList = teachSchools.stream()
                    .filter(sc->sc.getUuid()
                            .equals(searchSchoolDTO.getUuid())).collect(Collectors.toList());
            teachSchools = filterList;
        }
        if (!"".equals(searchSchoolDTO.getStatus()) && !Objects.isNull(teachSchools)){
            filterList = teachSchools.stream()
                    .filter(sc->sc.getStatus()
                            .equals(searchSchoolDTO.getStatus())).collect(Collectors.toList());
            teachSchools = filterList;
        }
        if (objectIsNull(teachSchools)){
            return ServerResponseEntity.success();
        }
        //分页
        filterList = teachSchools.stream().skip(PageToSize.pageToLine(searchSchoolDTO.getPage(),searchSchoolDTO.getSize()))
                        .limit(searchSchoolDTO.getSize())
                                .collect(Collectors.toList());
        resultMap.put("schoolList",filterList);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), resultMap);
    }

    private boolean objectIsNull(Object obj){
        if (Objects.isNull(obj)){
            return true;
        }
        return false;
    }

    private ServerResponseEntity objIsNullRet(Object obj){
        if (objectIsNull(obj)){
            return ServerResponseEntity.success();
        }
        return ServerResponseEntity.success();
    }
}
