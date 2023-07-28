package com.boot.teach.web.controllers.PassUse;

import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.service.passuse.PassUseService;
import io.minio.errors.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @author : hzx
 * @description : 系统公共接口
 * @date : 2023/7/21 11:09
 */
@Api(value = "公共接口")
@RequestMapping(value = "/passUse")
@RestController
public class PassUseController {
    private final Logger logger = LoggerFactory.getLogger(PassUseController.class);

    private final PassUseService passUseService;

    public PassUseController(PassUseService passUseService) {
        this.passUseService = passUseService;
    }

    /**
     * 学校管理员创编-学校下拉列表数据
     * @return
     */
    @PostMapping(value = "schoolList")
    @ApiOperation(value = "学校下拉列表数据")
    public ServerResponseEntity schoolList(){
        String moduleName = "PassUse-schoolList";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        ServerResponseEntity serverResponseEntity = passUseService.schoolList();
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    /**
     * 单个文件上传公共接口
     * @param file
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
    @Async
    @PostMapping("uploadIcon")
    @ApiOperation(value = "单个文件上传",notes = "单个文件图标上传,返回上传路径")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "file",name = "文件",paramType = "body",dataType = "MultipartFile")
    })
    public ServerResponseEntity uploadIcon(@RequestParam("file") MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String moduleName = "PassUse-uploadIcon";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,file));

        //参数校验，不通过抛异常，不执行service层
        if (Objects.isNull(file)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        ServerResponseEntity serverResponseEntity = passUseService.uploadIcon(file);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    @ApiOperation(value = "学院下拉列表")
    @PostMapping("distinctDep")
    @ApiParam(value = "schoolUid",type = "String")
    public ServerResponseEntity distinctDep(@RequestParam("schoolUid") String schoolUid){
        return passUseService.depList(schoolUid);
    }


    @ApiOperation(value = "班级下拉列表")
    @PostMapping("classList")
    @ApiParam(value = "majorUid",type = "String")
    public ServerResponseEntity classList(@RequestParam("majorUid") String majorUid){
        return passUseService.classList(majorUid);
    }

    @ApiOperation(value = "专业下拉列表")
    @PostMapping("majorList")
    @ApiParam(value = "departmentUid",type = "String")
    public ServerResponseEntity majorList(@RequestParam("departmentUid") String departmentUid){
        return passUseService.majorList(departmentUid);
    }
}
