package com.boot.teach.web.controllers.SuperManager;

import com.boot.teach.common.annotations.RequestLock;
import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.AddSchoolDTO;
import com.boot.teach.dto.school.supermanager.SchoolEditDTO;
import com.boot.teach.service.supermanager.SchoolInfoService;
import com.boot.teach.vo.school.supermanager.AddSchoolVO;
import com.boot.teach.vo.school.supermanager.SchoolEditVO;
import io.minio.errors.*;
import io.swagger.annotations.*;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


@Api(tags = "超级管理员端接口")
@RestController
@RequestMapping("/super")
public class SchoolInfoManage {
    private Logger logger = LoggerFactory.getLogger(SchoolInfoManage.class);
    private final MapperFactory mapperFactory;
    private final SchoolInfoService schoolInfoService;

    public SchoolInfoManage(MapperFactory mapperFactory, SchoolInfoService schoolInfoService) {
        this.mapperFactory = mapperFactory;
        this.schoolInfoService = schoolInfoService;
    }

    @PostMapping("addSchool")
    @RequestLock(prefix = "addSchool")
    @ApiOperation(value = "增加学校接口",notes = "超级管理员添加学校")
    @ApiImplicitParams({
        @ApiImplicitParam(value = "AddSchoolVO",name = "添加学校信息对象",paramType = "body",dataType = "Object"),
        @ApiImplicitParam(value = "icon",name = "学校图标",paramType = "body",dataType = "MultipartFile")
    })
    public ServerResponseEntity addSchool(@RequestBody AddSchoolVO schoolVO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManage-addSchool";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,schoolVO));
        //转换对象
        AddSchoolDTO schoolDTO = mapperFactory.getMapperFacade().map(schoolVO,AddSchoolDTO.class);
        //参数校验，不通过抛异常，不执行service层
        if (Objects.isNull(schoolVO)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        ServerResponseEntity serverResponseEntity = schoolInfoService.addSchool(schoolDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }


    @ApiOperation(value = "学校信息修改",notes = "学校信息修改")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uuid",dataType = "String",name = "uuid"),
            @ApiImplicitParam(value = "schoolName",name = "学校名",dataType = "String"),
            @ApiImplicitParam(value = "icon",name = "学校图标",dataType = "String"),
            @ApiImplicitParam(value = "status",name="入驻状态",dataType = "String"),
            @ApiImplicitParam(value = "schoolAddress",name = "学校地址",dataType = "String")
    })
    @PostMapping(value = "editSchoolInfo")
    public ServerResponseEntity editSchoolInfo(@RequestBody SchoolEditVO schoolEditVO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManage-editSchoolInfo";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,schoolEditVO));
        //参数为空，非法返回
        if (Objects.isNull(schoolEditVO)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        //对象转换
        mapperFactory.classMap(SchoolEditVO.class,SchoolEditDTO.class)
                        .field("schoolCode","uuid")
                        .byDefault()
                        .register();
        SchoolEditDTO schoolEditDTO = mapperFactory.getMapperFacade().map(schoolEditVO, SchoolEditDTO.class);
        ServerResponseEntity serverResponseEntity = schoolInfoService.updateSchoolInfo(schoolEditDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }


    @ApiOperation(value = "学校信息详情展示",notes = "学校信息详情展示")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uuid",dataType = "String",name = "uuid")
    })
    @PostMapping(value = "showSchoolDetail")
    public ServerResponseEntity showSchoolDetail(@RequestParam("schoolCode") String uuid,
                                                 @RequestParam("schoolName") String schoolName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManage-showSchoolDetail";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,uuid));
        //参数为空，非法返回
        if (Objects.isNull(uuid)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        ServerResponseEntity serverResponseEntity = schoolInfoService.showSchoolDetail(uuid,schoolName);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    @ApiOperation(value = "删除学校信息",notes = "删除学校信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uuid",dataType = "String",name = "uuid")
    })
    @PostMapping(value = "delSchoolDetail")
    public ServerResponseEntity delSchoolDetail(@RequestParam("schoolCode") String uuid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManage-delSchoolDetail";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,uuid));
        //参数为空，非法返回
        if (Objects.isNull(uuid)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        ServerResponseEntity serverResponseEntity = schoolInfoService.delSchoolDetail(uuid);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }
}
