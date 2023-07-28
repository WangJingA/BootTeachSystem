package com.boot.teach.service.passuse.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.MinioClientUtil;
import com.boot.teach.dao.passuse.PassUseMapper;
import com.boot.teach.model.school.TeachClass;
import com.boot.teach.model.school.TeachDepartment;
import com.boot.teach.model.school.TeachMajor;
import com.boot.teach.model.school.TeachSchool;
import com.boot.teach.service.passuse.PassUseService;
import io.minio.errors.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;


@Service
public class PassUseServiceImpl implements PassUseService {

    private final PassUseMapper passUseMapper;

    private final MinioClientUtil minioClientUtil;
    String bucket = "bootteach";

    public PassUseServiceImpl(PassUseMapper passUseMapper, MinioClientUtil minioClientUtil) {
        this.passUseMapper = passUseMapper;
        this.minioClientUtil = minioClientUtil;
    }

    /**
     * 学校下拉列表
     * @return
     */
    @Override
    public ServerResponseEntity schoolList() {
        List<TeachSchool> teachSchools = passUseMapper.schoolList();
        List<Map<String,Object>> reusultList = new ArrayList<>();
        teachSchools.stream().forEach(school->{
            Map<String,Object> map = new HashMap<>();
            map.put("uuid",school.getUuid());
            map.put("schoolName",school.getSchoolName());
            reusultList.add(map);
        });
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),reusultList);
    }

    /**
     * 单个文件上传
     * @param icon
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    @Override
    public ServerResponseEntity uploadIcon(MultipartFile icon) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url =  minioClientUtil.uploadFile(icon, bucket);
        if (!"".equals(url)){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), url);
        }
        return ServerResponseEntity.fail(ResponseEnum.UPLOAD_FAIL,url);
    }

    /**
     * 学院下拉列表数据
     * @param schoolUid
     * @return
     */
    @Override
    public ServerResponseEntity depList(String schoolUid) {
        //参数校验
        if (Objects.isNull(schoolUid)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        List<TeachDepartment> teachDepartments = passUseMapper.depList(schoolUid);
        List<Map<String,Object>> reslutList = new ArrayList<>();
        teachDepartments.stream().forEach(dep->{
            Map<String,Object> map = new HashMap<>();
            map.put("uuid",dep.getUuid());
            map.put("departmentName",dep.getDepartmentName());
            reslutList.add(map);
        });
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),reslutList);
    }

    @Override
    public ServerResponseEntity classList(String majorUid) {
        if (Objects.isNull(majorUid)){
            return ServerResponseEntity.fail(ResponseEnum.HTTP_MESSAGE_NOT_READABLE);
        }
        List<TeachClass> teachClasses = passUseMapper.classList(majorUid);
        List<Map<String,Object>> reslutList = new ArrayList<>();
        teachClasses.stream().forEach(cla->{
            Map<String,Object> map = new HashMap<>();
            map.put("uuid",cla.getUuid());
            map.put("className",cla.getClassName());
            reslutList.add(map);
        });
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),reslutList);
    }

    @Override
    public ServerResponseEntity majorList(String departmentUid) {
        if (Objects.isNull(departmentUid)){
            return ServerResponseEntity.fail(ResponseEnum.HTTP_MESSAGE_NOT_READABLE);
        }
        List<TeachMajor> teachMajors = passUseMapper.majorList(departmentUid);
        List<Map<String,Object>> reslutList = new ArrayList<>();
        teachMajors.stream().forEach(major->{
            Map<String,Object> map = new HashMap<>();
            map.put("uuid",major.getUuid());
            map.put("majorName",major.getMajorName());
            reslutList.add(map);
        });
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),reslutList);
    }
}
