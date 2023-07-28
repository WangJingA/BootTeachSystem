package com.boot.teach.service.passuse;

import com.boot.teach.common.response.ServerResponseEntity;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface PassUseService {
    /**
     * 学校下拉列表
     * @return
     */
    public ServerResponseEntity   schoolList();

    /**
     * 单文件上传公共接口
     * @param icon
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
    ServerResponseEntity uploadIcon(MultipartFile icon) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 学校-学院下拉列表数据
     * @param schoolUid
     * @return
     */
    ServerResponseEntity depList(String schoolUid);

    /**
     * 班级下拉列表
     * @param majorUid
     * @return
     */
    ServerResponseEntity classList(String majorUid);

    ServerResponseEntity majorList(String departmentUid);
}
