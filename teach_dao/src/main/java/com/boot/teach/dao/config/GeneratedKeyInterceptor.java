//package com.boot.teach.dao.config;
//
//import com.boot.teach.dao.annotations.DistributedId;
//import org.apache.ibatis.executor.Executor;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.SqlCommandType;
//import org.apache.ibatis.plugin.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.lang.reflect.Field;
//import java.util.Map;
//
//
//@Component
//@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class,Object.class})})
//public class GeneratedKeyInterceptor implements Interceptor {
//    private static final Logger logger = LoggerFactory.getLogger(GeneratedKeyInterceptor.class);
//
//    /**
//     *单个插入名称
//     */
//    private static final String INSERT = "insert";
//
//    /**
//     * 单个插入名称
//     */
//    private static final String SAVE = "save";
//
//    /**
//     * 批量插入名称
//     */
//    private static final String INSERT_BATCH = "insertBatch";
//
//    /**
//     * 批量插入名称
//     */
//    private static final String SAVE_BATCH = "saveBatch";
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
//        //获取sql
//        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
//        //不是插入语句直接放行，不拦截
//        if (sqlCommandType != SqlCommandType.INSERT){
//            invocation.proceed();
//        }
//        //获取sql参数
//        Object paramter = invocation.getArgs()[1];
//        //获取数据库对象
//        BaseModel dbObj = findDbObj(paramter);
//
//        if (dbObj == null){
//            invocation.proceed();
//        }
//
//        //插入数据库
//        if (mappedStatement.getId().contains(INSERT) || mappedStatement.getId().contains(SAVE)){
//            generatedKey(dbObj);
//        }
//        return null;
//    }
//
//    /**
//     * 获取私有成员变量并设置主键
//     * @param dbObj
//     */
//    public void generatedKey(Object dbObj) throws IllegalAccessException {
//        Field[] declaredFields = dbObj.getClass().getDeclaredFields();
//        for (Field field : declaredFields){
//            if (field.getType().isAssignableFrom(Long.class)){
//                break;
//            }
//            //反射获取注解内容
//            DistributedId annotation = field.getAnnotation(DistributedId.class);
//            if (annotation == null){
//                break;
//            }
//            //访问私有变量
//            field.setAccessible(true);
//            //已经设置了，退出
//            if (field.get(dbObj) != null){
//                break;
//            }
//            //自定义设置内容
//            field.set(dbObj,null);
//        }
//    }
//    @Override
//    public Object plugin(Object target) {
//        if (target instanceof Executor){
//            //代理执行拦截器
//            return Plugin.wrap(target,this);
//        }else {
//            //不进行自定义拦截器处理
//            return target;
//        }
//    }
//    public BaseModel findDbObj(Object paramenter){
//        if (paramenter instanceof BaseModel){
//            return (BaseModel) paramenter;
//        }else if (paramenter instanceof Map){
//            for (Object val : ((Map<?,?>)paramenter).values()){
//                if (val instanceof BaseModel){
//                    return (BaseModel) val;
//                }
//            }
//        }
//        return null;
//    }
//
//}
