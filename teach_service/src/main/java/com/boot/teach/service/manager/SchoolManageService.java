package com.boot.teach.service.manager;

import com.boot.teach.common.response.ServerResponseEntity;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


public interface SchoolManageService {
    ServerResponseEntity mulUpload(String schooluid,MultipartFile [] files) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    ServerResponseEntity imagesRecord(String schoolUid);
    ServerResponseEntity delImageRecord(String schoolUid,String iamgeUrl);
}
