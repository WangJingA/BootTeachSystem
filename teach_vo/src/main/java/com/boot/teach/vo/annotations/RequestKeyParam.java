package com.boot.teach.vo.annotations;

import java.lang.annotation.*;

/**
 * @author : hzx
 * @date : 2023/7/7 16:32
 * 表单防止重复提交-redis-key,默认空
 */
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestKeyParam {
    String name() default "";
}
