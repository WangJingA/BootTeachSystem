package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.major.CreateMajorDTO;
import com.boot.teach.dto.major.QueryMajorDTO;
import com.boot.teach.service.manager.MajorManageService;
import com.boot.teach.vo.manager.manager.CreateMajorVO;
import com.boot.teach.vo.manager.manager.QueryMajorVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import ma.glasnost.orika.MapperFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping(value = "/manager")
public class MajorManageController {
    private final MajorManageService majorManageService;

    private final MapperFactory mapperFactory;

    public MajorManageController(MajorManageService majorManageService, MapperFactory mapperFactory) {
        this.majorManageService = majorManageService;
        this.mapperFactory = mapperFactory;
    }

    @ApiOperation(value = "增加专业")
    @ApiParam(value = "CreateMajorVO",type = "CreateMajorVO")
    @PostMapping("addMajor")
    public ServerResponseEntity addMajor(@RequestBody CreateMajorVO createMajorVO){
        mapperFactory.classMap(CreateMajorVO.class, CreateMajorVO.class)
                .byDefault()
                .register();
        CreateMajorDTO createMajorDTO = mapperFactory.getMapperFacade()
                .map(createMajorVO, CreateMajorDTO.class);
        return majorManageService.addMajor(createMajorDTO);
    }

    @ApiOperation(value = "删除专业")
    @ApiParam(value = "uuid",type = "String")
    @PostMapping("delMajor")
    public ServerResponseEntity delMajor(@RequestParam("uuid") String uuid){
        if (Objects.isNull(uuid)){
            return ServerResponseEntity.fail(ResponseEnum.HTTP_MESSAGE_NOT_READABLE);
        }
        return majorManageService.delMajor(uuid);
    }

    @ApiOperation(value = "更新专业")
    @ApiParam(value = "CreateMajorVO",type = "CreateMajorVO")
    @PostMapping("updateMajor")
    public ServerResponseEntity updateMajor(@RequestBody CreateMajorVO createMajorVO){
        mapperFactory.classMap(CreateMajorVO.class, CreateMajorVO.class)
                .byDefault()
                .register();
        CreateMajorDTO createMajorDTO = mapperFactory.getMapperFacade()
                .map(createMajorVO, CreateMajorDTO.class);
        return majorManageService.updateMajor(createMajorDTO);
    }

    @ApiOperation(value = "专业列表")
    @ApiParam(value = "CreateMajorVO",type = "CreateMajorVO")
    @PostMapping("listMajor")
    public ServerResponseEntity listMajor(@RequestBody QueryMajorVO queryMajorVO){
        mapperFactory.classMap(QueryMajorVO.class, QueryMajorDTO.class)
                .byDefault()
                .register();
        QueryMajorDTO queryMajorDTO = mapperFactory.getMapperFacade()
                .map(queryMajorVO, QueryMajorDTO.class);
        return majorManageService.listMajor(queryMajorDTO);
    }
}
