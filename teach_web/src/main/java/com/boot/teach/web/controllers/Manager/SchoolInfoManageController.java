package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.service.manager.SchoolManageService;
import io.minio.errors.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping("/manager")
public class SchoolInfoManageController {
    private final SchoolManageService schoolManageService;
    private final Logger logger = LoggerFactory.getLogger(SchoolInfoManageController.class);
    public SchoolInfoManageController(SchoolManageService schoolManageService) {
        this.schoolManageService = schoolManageService;
    }

    @ApiOperation(value = "学校首页轮播图编辑")
    @PostMapping("uploadSchoolIm")
    @ApiParam(value = "MultipartFile",type = "Array")
    public ServerResponseEntity uploadSchoolIm(@RequestParam("schoolUid") String schoolUid,
                                               @RequestPart MultipartFile [] files) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManageController-uploadSchoolIm";
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,files));
        return schoolManageService.mulUpload(schoolUid,files);
    }

    @ApiOperation(value = "学校首页轮播图展示")
    @PostMapping("listImages")
    @ApiParam(value = "MultipartFile",type = "Array")
    public ServerResponseEntity listImages(@RequestParam("schoolUid") String schoolUid) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManageController-listImages";
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,schoolUid));
        return schoolManageService.imagesRecord(schoolUid);
    }

    @ApiOperation(value = "学校首页轮播图删除")
    @PostMapping("delImages")
    @ApiParam(value = "schoolUid",type = "String")
    public ServerResponseEntity delImages(@RequestParam("schoolUid") String schoolUid,
                                          @RequestParam("imageUrl") String imageUrl) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "SchoolInfoManageController-delImages";
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,schoolUid+imageUrl));
        return schoolManageService.delImageRecord(schoolUid,imageUrl);
    }


    @ApiOperation(value = "学校详情数据")
    @PostMapping("schoolDetail")
    @ApiParam(value = "schoolUid",type = "String")
    public ServerResponseEntity schoolDetail(@RequestParam("schoolUid") String schoolUid){
        String moduleName = "SchoolInfoManageController-delImages";
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,schoolUid));
        return null;
    }
}
