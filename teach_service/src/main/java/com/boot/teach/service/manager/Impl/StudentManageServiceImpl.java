package com.boot.teach.service.manager.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.security.LoginUser;
import com.boot.teach.common.template.StudentTemplate;
import com.boot.teach.dao.manager.StudentManageMapper;
import com.boot.teach.model.auth.TeachUser;
import com.boot.teach.model.student.TeachStudent;
import com.boot.teach.service.manager.StudentManageService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentManageServiceImpl extends ServiceImpl<StudentManageMapper,TeachStudent> implements StudentManageService {
    private final StudentManageMapper studentManageMapper;
    private final MapperFactory mapperFactory;
    private Logger logger = LoggerFactory.getLogger(StudentManageService.class);
    public StudentManageServiceImpl(StudentManageMapper studentManageMapper, MapperFactory mapperFactory) {
        this.studentManageMapper = studentManageMapper;
        this.mapperFactory = mapperFactory;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ServerResponseEntity importStuList(List<StudentTemplate> stuList) {
        logger.info(String.format("参数【%s】",stuList));
        //获取学校uid
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uuid = loginUser.getUserModel().getUuid();
        String schoolUid = studentManageMapper.getSchoolUid(uuid);
        //批量插入list集合
        List<TeachStudent> insertBath = new LinkedList<>();
        List<TeachUser> users = new LinkedList<>();
        //加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        //为list数据设置uuid
        Date date = new Date();
        stuList.stream().forEach(stu->{
            //转换
            mapperFactory.classMap(StudentTemplate.class, TeachStudent.class)
                    .byDefault()
                    .register();
            TeachStudent student = mapperFactory.getMapperFacade().map(stu, TeachStudent.class);
            student.setUuid(UUID.randomUUID().toString());
            student.setUpdateTime(date);
            student.setCreateTime(date);
            student.setSudentCollege(schoolUid);
            //设置账号，统一密码
            student.setSudentAccout(stu.getSudentSchoolNumber());
            student.setSudentPassword("123456");
            insertBath.add(student);
            //同时设置user
            TeachUser user = new TeachUser();
            user.setUuid(student.getUuid());
            user.setUserPhone(student.getSudentPhone());
            user.setUserStatus(0);
            user.setUserAccount(student.getSudentAccout());
            user.setUserPassword(bCryptPasswordEncoder.encode(student.getSudentPassword()));
            user.setUserEmail(student.getSudentMail());
            user.setCreateTime(date);
            user.setUpdateTime(date);
            users.add(user);
        });
        //同时插入用户表
        studentManageMapper.insertUsBath(users);
        //foreach 批量插入学生表
        int bath = studentManageMapper.inserStuBath(insertBath);
        return ServerResponseEntity.success(ResponseEnum.OK);
    }
}
