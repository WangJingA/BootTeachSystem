package com.boot.teach.service.supermanager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.boot.teach.common.constance.UserRoleConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.Random;
import com.boot.teach.dao.auth.AuthUserMapper;
import com.boot.teach.dao.auth.RoleMapper;
import com.boot.teach.dao.school.supermanager.SchooManagerlListMapper;
import com.boot.teach.dao.school.supermanager.SchoolListMapper;
import com.boot.teach.dao.school.supermanager.SchoolManagerLinkMapper;
import com.boot.teach.dao.school.supermanager.SchoolManagerMapper;
import com.boot.teach.dto.school.supermanager.CreateManagerDTO;
import com.boot.teach.dto.school.supermanager.ListManagerDTO;
import com.boot.teach.model.auth.TeachUser;
import com.boot.teach.model.auth.TeachUserRole;
import com.boot.teach.model.manager.TeachManager;
import com.boot.teach.model.manager.TeachSchoolManage;
import com.boot.teach.model.school.TeachSchool;
import com.boot.teach.model.school.TeachSchoolManagerList;
import com.boot.teach.service.supermanager.SchoolManagerService;
import ma.glasnost.orika.MapperFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
public class SchoolManagerServiceImpl extends ServiceImpl<SchoolManagerMapper,TeachManager> implements SchoolManagerService {
    private final MapperFactory mapperFactory;

    private final SchooManagerlListMapper managerlListMapper;

    private final SchoolManagerMapper schoolManagerMapper;

    private final SchoolManagerLinkMapper managerLinkMapper;

    private final AuthUserMapper userMapper;

    private final RoleMapper roleMapper;
    private Logger logger =  LoggerFactory.getLogger(SchoolManagerServiceImpl.class);

    public SchoolManagerServiceImpl(MapperFactory mapperFactory,
                                    SchoolListMapper schoolListMapper,
                                    SchooManagerlListMapper managerlListMapper,
                                    SchoolManagerMapper schoolManagerMapper,
                                    SchoolManagerLinkMapper managerLinkMapper, AuthUserMapper userMapper, RoleMapper roleMapper) {
        this.mapperFactory = mapperFactory;
        this.managerlListMapper = managerlListMapper;
        this.schoolManagerMapper = schoolManagerMapper;
        this.managerLinkMapper = managerLinkMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
    }

    /**
     * 随机账号
     * @return
     */
    @Override
    public ServerResponseEntity randomAccount() {
        String admin = "admin";
        LambdaQueryWrapper<TeachManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(TeachManager::getTeachManager);
        List<TeachManager> managerList = schoolManagerMapper.selectList(queryWrapper);
        if (!Objects.isNull(managerList)){
            //查询出最后一条数据
            TeachManager collect = managerList.get(managerList.size()-1);
            //最后一条数据的账号
            String teachManagerAccount = collect.getTeachManagerAccount();
            //结尾数字
            String num = teachManagerAccount.substring(admin.length(),teachManagerAccount.length());
            //拼接新账号
            admin = admin + (Integer.parseInt(num)+1);
        }
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),admin);
    }

    /**
     * 随机密码
     * @return
     */
    @Override
    public ServerResponseEntity randomPass() {
        String password = Random.randomPassword(8);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),password);
    }

    /**
     * 添加学校管理员
     * @param managerDTO
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServerResponseEntity addManager(CreateManagerDTO managerDTO) {
        //映射规则
        mapperFactory.classMap(TeachManager.class,CreateManagerDTO.class)
                        .byDefault().register();
        TeachManager teachManager = mapperFactory.getMapperFacade().map(managerDTO, TeachManager.class);
        teachManager.setUuid(String.valueOf(UUID.randomUUID()));
        //插入数据-添加到管理员表
        int insert = schoolManagerMapper.insert(teachManager);
        TeachSchoolManage teachSchoolManage = new TeachSchoolManage();
        if (insert>0){
            teachSchoolManage.setUuid(String.valueOf(UUID.randomUUID()));
            teachSchoolManage.setSchoolUid(managerDTO.getSchoolUuid());
            teachSchoolManage.setSchoolManagerUid(teachManager.getUuid());
            Date date = new Date();
            teachSchoolManage.setCreateTime(date);
            teachSchoolManage.setUpdateTime(date);
            managerLinkMapper.insert(teachSchoolManage);
        }
        //添加到用户表
        TeachUser user = new TeachUser();
        user.setUserAccount(teachManager.getTeachManagerAccount());
        user.setUserEmail(teachManager.getTeachManagerMail());
        user.setUuid(teachManager.getUuid());
        user.setUserPassword(new BCryptPasswordEncoder().encode(teachManager.getTeachManagerPassword()));
        user.setUserStatus(1);
        user.setUserPhone(teachManager.getTeachManagerPhone());
        int insertUser = userMapper.insert(user);
        //添加用户角色映射关系
        TeachUserRole userRole = new TeachUserRole();
        userRole.setUserId(user.getUuid());
        userRole.setUserRole(UserRoleConstance.MANAGER_ROLE);
        int insertRole = roleMapper.insert(userRole);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),null);
    }

    /**
     * 依靠学校uid获取管理员列表
     * @param listManagerDTO
     * @return
     */
    @Override
    public ServerResponseEntity listManagerBySchoolUid(ListManagerDTO listManagerDTO) {
        List<TeachSchoolManagerList> teachSchools = managerlListMapper.listSchoolManager(listManagerDTO);
        Stream<TeachSchoolManagerList> managerListStream = teachSchools.stream()
                .skip(((listManagerDTO.getPageIndex() - 1) * listManagerDTO.getPageSize()))
                .limit(listManagerDTO.getPageSize());
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),managerListStream);
    }

    @Override
    public ServerResponseEntity editManager(CreateManagerDTO createManagerDTO) {
        mapperFactory.classMap(TeachManager.class, CreateManagerDTO.class)
                .byDefault()
                .register();
        TeachManager teachManager = mapperFactory.getMapperFacade().map(createManagerDTO, TeachManager.class);
        LambdaUpdateWrapper<TeachManager> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TeachManager::getUuid,createManagerDTO.getSchoolUuid());
        int update = schoolManagerMapper.update(teachManager,updateWrapper);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(), update);
    }
}
