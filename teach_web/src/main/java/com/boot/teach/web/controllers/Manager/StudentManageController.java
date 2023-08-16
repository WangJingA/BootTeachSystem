package com.boot.teach.web.controllers.Manager;

import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.template.StudentTemplate;
import com.boot.teach.common.util.ExcelUtil;
import com.boot.teach.model.student.TeachStudent;
import com.boot.teach.service.manager.StudentManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Api(value = "管理员接口",tags = "管理员接口")
@RestController
@RequestMapping("/manager")
public class StudentManageController {
    private final ExcelUtil excelUtil;
    private final MapperFactory mapperFactory;
    private final StudentManageService studentManageService;
    public StudentManageController(ExcelUtil excelUtil, MapperFactory mapperFactory, StudentManageService studentManageService) {
        this.excelUtil = excelUtil;
        this.mapperFactory = mapperFactory;
        this.studentManageService = studentManageService;
    }

    @ApiOperation(value = "批量导入学生数据")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "file",name = "xlsx文件",paramType = "body",dataType = "File"),
    })
    @PostMapping("importStuList")
    public ServerResponseEntity importStudent(MultipartFile file) throws IOException {
        List<StudentTemplate> studentTemplates = excelUtil.importExcel(file, StudentTemplate.class);
        return studentManageService.importStuList(studentTemplates);
    }
}
