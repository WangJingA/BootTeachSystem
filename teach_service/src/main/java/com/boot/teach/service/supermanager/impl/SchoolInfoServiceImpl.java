package com.boot.teach.service.supermanager.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.MinioClientUtil;
import com.boot.teach.dao.school.supermanager.SchooManagerlListMapper;
import com.boot.teach.dao.school.supermanager.SchoolInfoMapper;
import com.boot.teach.dao.school.supermanager.SchoolManagerMapper;
import com.boot.teach.dto.school.supermanager.AddSchoolDTO;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.dto.school.supermanager.SchoolEditDTO;
import com.boot.teach.model.manager.TeachManager;
import com.boot.teach.model.school.TeachSchool;
import com.boot.teach.model.school.TeachSchoolManagerList;
import com.boot.teach.service.supermanager.SchoolInfoService;
import io.minio.errors.*;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class SchoolInfoServiceImpl extends ServiceImpl<SchoolInfoMapper, TeachSchool> implements SchoolInfoService {
    private Logger logger =  LoggerFactory.getLogger(SchoolInfoServiceImpl.class);
    private final SchoolInfoMapper schoolInfoMapper;

    private final SchoolManagerMapper managerMapper;
    private final MinioClientUtil minioClientUtil;
    private final SchooManagerlListMapper schooManagerlListMapper;
    private final MapperFactory mapperFactory;

    public SchoolInfoServiceImpl(SchoolInfoMapper schoolInfoMapper,
                                 SchoolManagerMapper managerMapper,
                                 MinioClientUtil minioClientUtil,
                                 SchooManagerlListMapper schooManagerlListMapper, MapperFactory mapperFactory) {
        this.schoolInfoMapper = schoolInfoMapper;
        this.managerMapper = managerMapper;
        this.minioClientUtil = minioClientUtil;
        this.schooManagerlListMapper = schooManagerlListMapper;
        this.mapperFactory = mapperFactory;
    }


    String bucket = "bootteach";

    @Transactional
    @Override
    public ServerResponseEntity addSchool(AddSchoolDTO schoolDTO) {
        //定义映射规则
        mapperFactory.classMap(TeachSchool.class,AddSchoolDTO.class)
                        .field("schoolIcon","icon")
                        .field("schoolIntroduction","schoolDesc")
                        .byDefault()
                        .register();
        //转化
        TeachSchool schoolVo = mapperFactory.getMapperFacade().map(schoolDTO, TeachSchool.class);
        //学校入驻唯一性校验
        LambdaQueryWrapper<TeachSchool> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(TeachSchool::getSchoolName,schoolDTO.getSchoolName());
        TeachSchool teachSchool = schoolInfoMapper.selectOne(queryWrapper);
        if (!Objects.isNull(teachSchool)){
            return ServerResponseEntity.fail(ResponseEnum.SCHOOL_EXIST);
        }
        //保存信息到数据库
            String uuid = String.valueOf(UUID.randomUUID());
            schoolVo.setUuid(uuid);
            schoolVo.setSchoolDecode(uuid);
            schoolVo.setStatus("0");
            schoolInfoMapper.insert(schoolVo);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), null);
    }


    @Override
    public ServerResponseEntity updateSchoolInfo(SchoolEditDTO editDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        //查询学校信息
        LambdaQueryWrapper<TeachSchool> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachSchool::getUuid,editDTO.getUuid());
        TeachSchool teachSchool = schoolInfoMapper.selectOne(queryWrapper);
        //删除学校图标
        minioClientUtil.delFileObject(bucket,teachSchool.getSchoolIcon());
        //更新
        LambdaUpdateWrapper<TeachSchool> updateWrapper = new LambdaUpdateWrapper<>();
        LambdaUpdateWrapper<TeachSchool> lambdaUpdateWrapper = updateWrapper.eq(TeachSchool::getUuid, editDTO.getUuid())
                .set(TeachSchool::getSchoolName, editDTO.getSchoolName())
                .set(TeachSchool::getSchoolAddress, editDTO.schoolAddress)
                .set(TeachSchool::getSchoolIcon, editDTO.getIcon());
        int update = schoolInfoMapper.update(null, lambdaUpdateWrapper);
        if (update > 0){
            ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), ExceptionMessageConstance.UPDATE_SUCCESS);
        }
        return ServerResponseEntity.fail(ResponseEnum.UPLOAD_FAIL,ExceptionMessageConstance.UPDATE_FAIL);
    }

    @Override
    public ServerResponseEntity showSchoolDetail(String uuid,String schoolName) {
        //查询学校信息
        LambdaQueryWrapper<TeachSchool> queryWrapper = new LambdaQueryWrapper<>();
        if (!"".equals(uuid)) {
            queryWrapper.eq(TeachSchool::getUuid, uuid);
        }
        if (!"".equals(schoolName)){
            queryWrapper.like(TeachSchool::getSchoolName,schoolName);
        }
        TeachSchool teachSchool = schoolInfoMapper.selectOne(queryWrapper);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),teachSchool);
    }

    @Override
    public ServerResponseEntity delSchoolDetail(String uuid) {
        //查询学校信息
        schoolInfoMapper.deleteById(uuid);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),ExceptionMessageConstance.UPDATE_SUCCESS);
    }

    /**
     * 超级管理员首页数据接口
     * @return
     */
    @Override
    public ServerResponseEntity DabashInfo() {
        LambdaQueryWrapper<TeachManager> managerQueryWrapper = new LambdaQueryWrapper<>();
        //管理员数
        Long aLong = managerMapper.selectCount(managerQueryWrapper);
        Map<String,Object> resultMap = new HashMap<>();
        LambdaQueryWrapper<TeachSchool> teachSchoolLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<TeachSchool> teachSchools = schoolInfoMapper.selectList(teachSchoolLambdaQueryWrapper);
        //入驻学校数
        long inSchool = teachSchools.stream().filter(s -> s.getStatus().equals("0")).count();
        //出驻学校数
        long outSchool = teachSchools.stream().filter(s -> s.getStatus().equals("1")).count();
        List<String> label = new ArrayList<>();
        //学校列表
        Iterator<TeachSchool> iterator = teachSchools.stream().iterator();
        while(iterator.hasNext()){
            label.add(iterator.next().getSchoolName());
        }
        //学校对应的管理员
        List<TeachSchoolManagerList> teachSchoolManagerLists = schooManagerlListMapper.listSchoolManager(new ListManagerDTO());
        List<Integer> data = new ArrayList<>();
        int sum = 0;
        for (TeachSchoolManagerList managerList :teachSchoolManagerLists) {
            long countManager = managerList.getManagerList().stream().count();
            sum += countManager;
            data.add((int)countManager);
        }
        resultMap.put("inSchool",inSchool);
        resultMap.put("outSchool",outSchool);
        resultMap.put("managerCount",sum);
        resultMap.put("label",label);
        resultMap.put("data",data);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),resultMap);
    }
}
