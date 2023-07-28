package com.boot.teach.service.manager.Impl;

import com.boot.teach.common.constance.ExceptionMessageConstance;
import com.boot.teach.common.response.ResponseEnum;
import com.boot.teach.common.response.ServerResponseEntity;
import com.boot.teach.common.util.MinioClientUtil;
import com.boot.teach.dao.manager.ManagerSchoolInfoMapper;
import com.boot.teach.model.school.SchoolImage;
import com.boot.teach.service.manager.SchoolManageService;
import io.minio.errors.*;
import org.aspectj.apache.bcel.ExceptionConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SchoolManageServiceImpl implements SchoolManageService {

    @Value("${minio.bucket}")
    String bucketName = "bootteach";

    private final MinioClientUtil minioClientUtil;
    private final ManagerSchoolInfoMapper schoolInfoMapper;
    public SchoolManageServiceImpl(MinioClientUtil minioClientUtil, ManagerSchoolInfoMapper schoolInfoMapper) {
        this.minioClientUtil = minioClientUtil;
        this.schoolInfoMapper = schoolInfoMapper;
    }

    @Transactional(rollbackFor ={Exception.class})
    @Override
    public ServerResponseEntity mulUpload(String schooluid,MultipartFile[] files) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, IOException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        StringBuilder stringBuilder = new StringBuilder();
        if (!Objects.isNull(files)){
            for (MultipartFile file:files) {
                String url = minioClientUtil.uploadFile(file, bucketName);
                stringBuilder.append(url);
                stringBuilder.append(";");
            }
        }
        SchoolImage existImage = schoolInfoMapper.existImage(schooluid);
        SchoolImage schoolImage = new SchoolImage();
        schoolImage.setUuid(UUID.randomUUID().toString());
        schoolImage.setImageUrl(stringBuilder.toString());
        schoolImage.setSchoolUid(schooluid);
        Date date = new Date();
        schoolImage.setCreateTime(date);
        schoolImage.setUpdateTime(date);
        //学校首页轮播图存在，更新轮播图链接
        if (!Objects.isNull(existImage)){
            stringBuilder.append(existImage.getImageUrl());
            schoolImage.setImageUrl(stringBuilder.toString());
            schoolInfoMapper.updateImage(schoolImage);
        }else {
            //不存在插入
            schoolInfoMapper.schoolImage(schoolImage);
        }
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),null);
    }

    @Override
    public ServerResponseEntity imagesRecord(String schoolUid) {
        if (Objects.isNull(schoolUid)){
            throw new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<SchoolImage> schoolImages = schoolInfoMapper.selectImages(schoolUid);
        Iterator<SchoolImage> iterator = schoolImages.stream().iterator();
        while (iterator.hasNext()){
            SchoolImage schoolImage = iterator.next();
            //拆分images url
            String imageUrl = schoolImage.getImageUrl();
            String[] split = imageUrl.split(";");
            for (int i = 0; i < split.length; i++) {
                if (!"".equals(split[i])){
                    Map<String,Object> imageMap = new HashMap<>();
                    imageMap.put("name",schoolUid);
                    imageMap.put("url",split[i]);
                    resultList.add(imageMap);
                }
            }
        }
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),resultList);
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public ServerResponseEntity delImageRecord(String schoolUid, String imageUrl) {
        SchoolImage existImage = schoolInfoMapper.existImage(schoolUid);
        //参数校验
        if (Objects.isNull(schoolUid) || Objects.isNull(imageUrl)){
            throw  new IllegalArgumentException(ExceptionMessageConstance.PARAM_ILLEAGEL);
        }
        //judge the existImage images was existed
        String url = existImage.getImageUrl();
        if (Objects.isNull(url)){
            return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),"success");
        }
        //replace url target imageUrl
        String replace = url.replace(imageUrl, ";");
        SchoolImage image = new SchoolImage();
        image.setImageUrl(replace);
        image.setUuid(existImage.getUuid());
        image.setSchoolUid(existImage.getSchoolUid());
        image.setCreateTime(existImage.getCreateTime());
        image.setUpdateTime(new Date());
        schoolInfoMapper.updateImage(image);
        return ServerResponseEntity.success(ResponseEnum.OK.value(),ResponseEnum.OK.getMsg(),"success");
    }
}
