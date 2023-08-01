package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.manager.CreateClassDTO;
import com.boot.teach.dto.manager.EditClassDTO;
import com.boot.teach.service.manager.ClassManageService;
import com.boot.teach.vo.manager.manager.CreateClassVO;
import com.boot.teach.vo.manager.manager.EditClassVO;
import com.boot.teach.vo.manager.manager.QueryClassVO;
import io.swagger.annotations.*;
import ma.glasnost.orika.MapperFactory;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "班级列表")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "uuid",name = "班级uid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "className",name = "班级名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "major",name = "专业uid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "page",name = "页码",paramType = "body",dataType = "int"),
            @ApiImplicitParam(value = "pageSize",name = "页大小",paramType = "body",dataType = "int")
    })
    @PostMapping(value = "listClass")
    public ServerResponseEntity listClass(@RequestBody QueryClassVO classVO){
        ServerResponseEntity serverResponseEntity = classManageService.classList(classVO);
        return serverResponseEntity;
    }

    @ApiOperation(value = "删除班级")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "clazz",name = "班级uid",dataType = "String")
    })
    @PostMapping(value = "delClass")
    public ServerResponseEntity delClass(@RequestParam("clazz") String clazz){
        return  classManageService.delClass(clazz);
    }


    @ApiOperation(value = "编辑班级信息")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "clazzName",name = "班级名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "Icon",name = "班级图标",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "majName",name = "专业名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "schName",name = "学校名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "depName",name = "学院名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "clazzName",name = "班级名",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "depUid",name = "学院uid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "majUid",name = "专业uid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "uuid",name = "uuid",paramType = "body",dataType = "String"),
            @ApiImplicitParam(value = "status",name = "状态",paramType = "body",dataType = "int"),
            @ApiImplicitParam(value = "desc",name = "描述",paramType = "body",dataType = "String")
    })
        @PostMapping(value = "editClass")
    public ServerResponseEntity editClass(@RequestBody EditClassVO editClassVO) throws Exception {
        mapperFactory.classMap(EditClassVO.class, EditClassDTO.class)
                .field("uuid","uuid")
                .field("school","classSchool")
                .field("clazzName","className")
                .field("majName","classMajor")
                .field("icon","classIcon")
                .field("depUid","classDepartmentUid")
                .field("majUid","classMajorUid")
                .field("depName","classDepartment")
                .field("desc","classDesc")
                .byDefault()
                .register();
        EditClassDTO editClassDTO = mapperFactory.getMapperFacade().map(editClassVO, EditClassDTO.class);
        return  classManageService.classEdit(editClassDTO);
    }
}
