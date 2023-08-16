package com.boot.teach.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

/**
 * excel 导入导出工具类
 * @author : hzx
 * @date : 2023/8/16 10:41
 * @param <T>
 */
@Component
public class ExcelUtil<T>{

    /**
     * 导入解析
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public List<T> importExcel(MultipartFile multipartFile,Class<T> tClass) throws IOException {
        List<T> excelList = EasyExcel.read(multipartFile.getInputStream())
                .head(tClass)
                .sheet()
                .doReadSync();
        return  excelList;
    }

    /**
     * excel导出
     * @param response
     * @param exportFileName : 导出文件名
     * @param exportList : 需要导出的数据
     * @param tClass : 模板类
     * @return
     * @throws UnsupportedEncodingException
     */
    public void exportExcel(HttpServletResponse response,String exportFileName,List<T> exportList,Class<T> tClass){
        try {
            setExportHeader(response, exportFileName);
            EasyExcel.write()
                    .head(tClass)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(exportFileName)
                    .doWrite(exportList.stream().collect(Collectors.toList()));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 设置excel导出响应头
     * @param response
     * @param exportName
     * @throws UnsupportedEncodingException
     */
    public void setExportHeader(HttpServletResponse response,String exportName) throws UnsupportedEncodingException {
        String fileName = URLEncoder.encode(exportName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition","attachment;filename=*''"+fileName+".xlsx");
    }

}
