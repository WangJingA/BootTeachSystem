package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.manager.CreateDepartmentDTO;
import com.boot.teach.dto.school.manager.ListDepartmentDTO;
import com.boot.teach.service.manager.DepartmentManageService;
import com.boot.teach.vo.manager.manager.CreateDepartmentVO;
import com.boot.teach.vo.manager.manager.QueryDepartmentVO;
import io.swagger.annotations.*;
import ma.glasnost.orika.MapperFactory;
import org.springframework.web.bind.annotation.*;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping("/manager")
public class DepartmentManageController {
    private final DepartmentManageService departmentManageService;
    private final MapperFactory mapperFactory;

    public DepartmentManageController(DepartmentManageService departmentManageService, MapperFactory mapperFactory) {
        this.departmentManageService = departmentManageService;
        this.mapperFactory = mapperFactory;
    }


    @ApiOperation(value = "学院创编")
    @PostMapping("createDep")
    @ApiParam(value = "CreateDepartmentVO",type = "Vo")
    public ServerResponseEntity createDepartment(@RequestBody CreateDepartmentVO departmentVO){
        mapperFactory.classMap(CreateDepartmentDTO.class,CreateDepartmentVO.class)
                .byDefault()
                .register();
        CreateDepartmentDTO departmentDTO = mapperFactory.getMapperFacade().map(departmentVO, CreateDepartmentDTO.class);
        return departmentManageService.createDepartment(departmentDTO);
    }

    @ApiOperation(value = "学院表格数据")
    @PostMapping("listDep")
    @ApiParam(value = "ListDepartmentVO",type = "VO")
    public ServerResponseEntity listDepartment(@RequestBody QueryDepartmentVO departmentVO){
        mapperFactory.classMap(ListDepartmentDTO.class, QueryDepartmentVO.class).byDefault()
                .register();
        ListDepartmentDTO departmentDTO = mapperFactory.getMapperFacade().map(departmentVO, ListDepartmentDTO.class);
        return departmentManageService.listDepartment(departmentDTO);
    }

}
