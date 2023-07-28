package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.manager.CreateClassDTO;
import com.boot.teach.service.manager.ClassManageService;
import com.boot.teach.vo.manager.manager.CreateClassVO;
import io.swagger.annotations.*;
import ma.glasnost.orika.MapperFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping("/manager")
public class ClassManageController {
    private  final ClassManageService classManageService;
    private final MapperFactory mapperFactory;
    public ClassManageController(ClassManageService classManageService, MapperFactory mapperFactory) {
        this.classManageService = classManageService;
        this.mapperFactory = mapperFactory;
    }

    @PostMapping("createClass")
    @ApiOperation(value = "管理员创建班级")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "clazzName",name = "班级名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "icon",name = "班级图标",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "major",name = "班级uuid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "school",name = "学校uuid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "department",name = "学院uuid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "desc",name = "描述",paramType = "body",dataType = "String")
    })
    public ServerResponseEntity createClass(@RequestBody CreateClassVO createClassVO){
        mapperFactory.classMap(CreateClassVO.class, CreateClassDTO.class)
                .byDefault()
                .register();
        CreateClassDTO classDTO = mapperFactory.getMapperFacade().map(createClassVO, CreateClassDTO.class);
        ServerResponseEntity responseEntity = classManageService.createClass(classDTO);
        return responseEntity;
    }
}
