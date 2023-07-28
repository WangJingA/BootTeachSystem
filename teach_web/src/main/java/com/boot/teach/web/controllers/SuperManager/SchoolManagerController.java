package com.boot.teach.web.controllers.SuperManager;

import com.boot.teach.common.annotations.RequestLock;
import com.boot.teach.common.constance.ExcutionLogConstance;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.CreateManagerDTO;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.service.supermanager.SchoolInfoService;
import com.boot.teach.service.supermanager.SchoolManagerService;
import com.boot.teach.vo.manager.supermanager.CreateManagerVO;
import com.boot.teach.vo.manager.supermanager.ListManagerVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "超级管理员端接口")
@RequestMapping(value = "/super")
public class SchoolManagerController {
    Logger logger = LoggerFactory.getLogger(SchoolManagerController.class);
    private final MapperFactory mapperFactory;

    private final SchoolManagerService managerService;

    private final SchoolInfoService infoService;

    public SchoolManagerController(MapperFactory mapperFactory, SchoolManagerService managerService, SchoolInfoService infoService) {
        this.mapperFactory = mapperFactory;
        this.managerService = managerService;
        this.infoService = infoService;
    }



    /**
     * 学校管理员创编-随机密码
     * @return
     */
    @PostMapping(value = "randomPass")
    @ApiOperation(value = "管理员创编随机密码")
    public ServerResponseEntity randomPass(){
        String moduleName = "SchoolManagerController-randomPass";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        ServerResponseEntity serverResponseEntity = managerService.randomPass();
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    /**
     * 学校管理员创编-随机账号
     * @return
     */
    @PostMapping(value = "randomAccount")
    @ApiOperation(value = "管理员创编随机账号")
    public ServerResponseEntity randomAccount(){
        String moduleName = "SchoolManagerController-randomAccount";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        ServerResponseEntity serverResponseEntity = managerService.randomAccount();
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    /**
     * 学校管理员创编-添加学校管理员
     * @return
     */
    @PostMapping(value = "createManager")
    @RequestLock(prefix = "addManager")
    @ApiOperation(value = "管理员创编添加学校管理员")
    public ServerResponseEntity addManager(@RequestBody CreateManagerVO managerVO){
        String moduleName = "SchoolManagerController-addManager";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        mapperFactory.classMap(CreateManagerDTO.class, CreateManagerVO.class);
        CreateManagerDTO managerDTO = mapperFactory.getMapperFacade().map(managerVO, CreateManagerDTO.class);
        ServerResponseEntity serverResponseEntity = managerService.addManager(managerDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }


    /**
     * 学校管理员创编-管理员列表
     * @return
     */
    @PostMapping(value = "managerBySchoolUid")
    @ApiOperation(value = "学校管理员列表")
    public ServerResponseEntity listManagerBySchoolUid(@RequestBody ListManagerVO listManagerVO){
        String moduleName = "SchoolManagerController-listManagerBySchoolUid";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,listManagerVO));
        mapperFactory.classMap(ListManagerDTO.class, ListManagerVO.class).byDefault().register();
        ListManagerDTO managerDTO = mapperFactory.getMapperFacade().map(listManagerVO, ListManagerDTO.class);
        ServerResponseEntity serverResponseEntity = managerService.listManagerBySchoolUid(managerDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    /**
     * 学校管理员创编-修改学校管理员
     * @return
     */
    @PostMapping(value = "editManager")
    @RequestLock(prefix = "editManager")
    @ApiOperation(value = "学校管理员编辑")
    public ServerResponseEntity editManager(@RequestBody CreateManagerVO createManagerVO){
        String moduleName = "SchoolManagerController-listManagerBySchoolUid";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.MODULE_PARAM_PRINT,moduleName,createManagerVO));
        mapperFactory.classMap(CreateManagerDTO.class, CreateManagerVO.class).byDefault().register();
        CreateManagerDTO managerDTO = mapperFactory.getMapperFacade().map(createManagerVO, CreateManagerDTO.class);
        ServerResponseEntity serverResponseEntity = managerService.editManager(managerDTO);
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }

    @PostMapping(value = "dashBashInfo")
    @ApiOperation(value = "超级管理员首页展示")
        public ServerResponseEntity dashBashInfo(){
        String moduleName = "SchoolManagerController-listManagerBySchoolUid";
        long startTime = System.currentTimeMillis();
        logger.info(String.format(ExcutionLogConstance.ENTER_MODULE_PRINT,moduleName));
        mapperFactory.classMap(CreateManagerDTO.class, CreateManagerVO.class).byDefault().register();
        ServerResponseEntity serverResponseEntity = infoService.DabashInfo();
        logger.info(String.format(ExcutionLogConstance.EXCUTION_MODULE_PRINT,moduleName,System.currentTimeMillis()-startTime));
        return serverResponseEntity;
    }
}
