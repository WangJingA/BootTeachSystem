package com.boot.teach.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.DeleteObject;
import io.minio.messages.Item;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.net.URLEncoder;
import java.rmi.NoSuchObjectException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class MinioClientUtil {
    private Logger logger = LoggerFactory.getLogger(MinioClientUtil.class);
   private final MinioClient minioClient;

    private String defaultBucketName = "bootteach";
    @Value(value = "${minio.accessHost}")
    private String customDomain = "http://192.168.219.137:9000";

    public MinioClientUtil(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    /**
     * 判断bucket是否存在
     * @param name
     * @return
     */
    public boolean existBucket(String name) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!StringUtils.isEmpty(name)) {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        }else{
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(defaultBucketName).build());
        }
        return false;
    }

    /**
     * 创建bucket
     * @param bucketName
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
    public boolean createBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
       if (this.existBucket(bucketName)){
           return false;
       }
        minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
       return true;
    }

    /**
     * 删除bucket
     * @param bucketName
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
    public boolean delBucket(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!this.existBucket(bucketName)){
           return false;
        }
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        return true;
    }

    /**
     * 上传文件
     * @param file
     * @param bucketName
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
    public String uploadFile(MultipartFile file, String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, IOException {
        // 存储桶名称为空则使用默认的存储桶
        if (StrUtil.isBlank(bucketName)) {
            bucketName = defaultBucketName;
        }

        createBucket(bucketName);

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        String fileName = IdUtil.simpleUUID() + "." + suffix;

        InputStream inputStream = file.getInputStream();
        PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                .bucket(bucketName)
                .object(fileName)
                .contentType(file.getContentType())
                .stream(inputStream, inputStream.available(), -1)
                .build();

        minioClient.putObject(putObjectArgs);

        String fileUrl;
        if (StrUtil.isBlank(customDomain)) { // 没有自定义文件路径域名
            GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .build();

            fileUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
            fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
        } else { // 自定义文件路径域名，Nginx配置方向代理转发MinIO
            fileUrl = customDomain +'/'+ bucketName + "/" + fileName;
        }
        return fileUrl;
    }

    /**
     * 删除桶中的对象
     * @param bucketName
     * @param fileName
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
    public boolean delFileObject(String bucketName,String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (existBucket(bucketName)){
            if ("".equals(fileName)){
                return false;
            }
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
            return true;
        }
        return false;
    }

    /**
     * 批量删除桶中的对象
     * @param bucketName
     * @param  fileNames 不重复文件名
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
    public boolean delFileObjects(String bucketName,Set<String> fileNames) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (existBucket(bucketName)){
            List<DeleteObject> deleteObjects = new LinkedList<>();
            fileNames.stream().map(file ->deleteObjects.add(new DeleteObject(file)));
            minioClient.removeObjects(RemoveObjectsArgs.builder().bucket(bucketName).objects(deleteObjects).build());
            return true;
        }
        return false;
    }

    /**
     * 列表bucket-object
     * @param bucketName
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
    public List<Map<String,Object>> listBucketObject(String bucketName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        if (!this.existBucket(bucketName)){
            throw  new NoSuchObjectException("bucket no exist");
        }
        List<Map<String,Object>> resultList = new ArrayList<>();
        Map<String,Object> map = new HashMap<>();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        for (Result<Item> itemResult : results) {
            Item item = itemResult.get();
            map.put("filename",item.objectName());
            map.put("filesize",item.size());
            resultList.add(map);
        }
     return resultList;
    }

    /**
     * 下载
     * @param filename
     * @param bucketName
     * @param response
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
    public void download(String filename, String bucketName, HttpServletResponse response) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        InputStream in = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(filename).build());
        HttpHeaders httpHeaders = new HttpHeaders();
        response.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(filename,"UTF-8"));
        ServletOutputStream outputStream = response.getOutputStream();
        IOUtils.copy(in,outputStream);
    }

    /**
     * 获取文件预览的绝对地址
     * @param bucketName
     * @param filename 文件夹名+文件名
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
    public String getFileUrl(String bucketName,String filename) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean existBucket = existBucket(bucketName);
        if (existBucket){
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().bucket(bucketName).object(filename).build());
        }
        return "";
    }
}
