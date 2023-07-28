package com.boot.teach.web.controllers.SuperManager;

import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.SearchSchoolDTO;
import com.boot.teach.service.supermanager.SchoolListService;
import com.boot.teach.vo.school.SearchSchoolVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@Api(tags = "超级管理员端接口")
@RestController
@RequestMapping(value = "/super")
public class SchoolListController {
    private final SchoolListService schoolListService;

    private final MapperFactory mapperFactory;

    private Logger logger =  LoggerFactory.getLogger(SchoolListController.class);
    public SchoolListController(SchoolListService schoolListService, MapperFactory mapperFactory) {
        this.schoolListService = schoolListService;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping("schoolList")
    @ApiOperation(value = "学校搜索列表，返回学校信息",notes = "按uuid，学校名，入驻状态搜索")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uuid",dataType = "String",name = "uuid"),
            @ApiImplicitParam(value = "schoolName",name = "学校名",dataType = "String"),
            @ApiImplicitParam(value = "status",name="入驻状态",dataType = "String"),
            @ApiImplicitParam(value = "size",name = "页的大小",dataType = "int"),
            @ApiImplicitParam(value = "page",name = "当前页",dataType = "int")
    })
    public ServerResponseEntity schoolList(@RequestBody SearchSchoolVO searchSchoolVO){
        String moduleName = "SchoolListController-schoolList";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,searchSchoolVO));
        MapperFacade mapperFacade = mapperFactory.getMapperFacade();
        SearchSchoolDTO searchSchoolDTO = mapperFacade.map(searchSchoolVO, SearchSchoolDTO.class);
        ServerResponseEntity serverResponseEntity = schoolListService.SearchAndListSchool(searchSchoolDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }
}
