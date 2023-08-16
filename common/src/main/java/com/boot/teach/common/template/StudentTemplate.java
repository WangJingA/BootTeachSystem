package com.boot.teach.common.template;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * excel文件导入导出，学生模板
 * @author : hzx
 * @date : 2023/8/16 10:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StudentTemplate {

    @ExcelProperty(value = "姓名")
    String sudentName;
    @ExcelProperty(value = "学号")
    String sudentSchoolNumber;
    @ExcelProperty(value = "班级")
    String sudentClass;
    @ExcelProperty(value = "学校")
    String sudentCollege;
    @ExcelProperty(value = "性别")
    String sudentSex;
    @ExcelProperty(value = "专业")
    String sudentMajor;
    @ExcelProperty(value = "学院")
    String sudentDepartment;
    @ExcelProperty(value = "手机号")
    String sudentPhone;
    @ExcelProperty(value = "邮箱")
    String sudentMail;
    @ExcelIgnore
    String sudentIcon;
    @ExcelIgnore
    String sudentPassword;
    @ExcelIgnore
    String createTime;
    @ExcelIgnore
    String updateTime;
}
