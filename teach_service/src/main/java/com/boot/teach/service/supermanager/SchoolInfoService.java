package com.boot.teach.service.supermanager;

import com.baomidou.mybatisplus.extension.service.IService;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.dto.school.supermanager.AddSchoolDTO;
import com.boot.teach.dto.school.supermanager.SchoolEditDTO;
import com.boot.teach.model.school.TeachSchool;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 超级管理员管理学校信息
 */
public interface SchoolInfoService extends IService<TeachSchool> {
    /**
     * 添加学校
     * @param addSchoolDTO
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
    ServerResponseEntity addSchool(AddSchoolDTO addSchoolDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;


    ServerResponseEntity updateSchoolInfo(SchoolEditDTO schoolEditDTO) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    ServerResponseEntity showSchoolDetail(String uuid,String schoolName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    ServerResponseEntity delSchoolDetail(String uuid) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    ServerResponseEntity DabashInfo();
}
