//package com.boot.teach.dao.config;
//
//import org.apache.ibatis.mapping.BoundSql;
//import org.apache.ibatis.mapping.MappedStatement;
//import org.apache.ibatis.mapping.ParameterMapping;
//import org.apache.ibatis.mapping.ParameterMode;
//import org.apache.ibatis.plugin.*;
//import org.apache.ibatis.reflection.MetaObject;
//import org.apache.ibatis.session.Configuration;
//import org.apache.ibatis.session.ResultHandler;
//import org.apache.ibatis.type.TypeHandlerRegistry;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.Executor;
//
///**
// * sql日志打印
// */
//@Component
//@Intercepts({
//        @Signature(type = Executor.class,method = "insert",args = {MappedStatement.class,Object.class, BoundSql.class, ResultHandler.class}),
//        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
//        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class,ResultHandler.class}),
//        @Signature(type = Executor.class,method = "delete",args = {MappedStatement.class,Object.class,ResultHandler.class})
//})
//public class LogsPrintExcutor implements Interceptor {
//    Logger logger = LoggerFactory.getLogger(LogsPrintExcutor.class);
//    @Override
//    public Object intercept(Invocation invocation) throws Throwable {
//        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
//
//        int one = 1;
//        //sql参数
//        Object paramter = invocation.getArgs()[one];
//        //sql mapper id
//        String id = mappedStatement.getId();
//        //sql
//        BoundSql boundSql = mappedStatement.getBoundSql(paramter);
//        //configuration
//        Configuration configuration =  mappedStatement.getConfiguration();
//        //执行的sql语句，携带真实参数
//        String sql = sqlHandler(boundSql,configuration);
//        //sql执行的当前时间
//        long currentTime = System.currentTimeMillis();
//        //执行sql
//        invocation.proceed();
//        long endTime = System.currentTimeMillis();
//        //打印
//        logger.info(String.format("SQL_Mapper:%s",id));
//        logger.info(String.format("SQL:%s",sql));
//        logger.info(String.format("SQL执行时间:%s ms",endTime-currentTime));
//        return null;
//    }
//
//    @Override
//    public Object plugin(Object target) {
//        return Interceptor.super.plugin(target);
//    }
//
//    @Override
//    public void setProperties(Properties properties) {
//        Interceptor.super.setProperties(properties);
//    }
//    private String sqlHandler(BoundSql boundSql, Configuration configuration){
//        // 获取mapper里面方法上的参数
//        Object sqlParameter = boundSql.getParameterObject();
//        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
//        // sql原始语句(?还没有替换成我们具体的参数)
//        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
//        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        if (parameterMappings != null) {
//            for (int i = 0; i < parameterMappings.size(); i++) {
//                ParameterMapping parameterMapping = parameterMappings.get(i);
//                if (parameterMapping.getMode() != ParameterMode.OUT) {
//                    Object value;
//                    String propertyName = parameterMapping.getProperty();
//                    if (boundSql.hasAdditionalParameter(propertyName)) {
//                        value = boundSql.getAdditionalParameter(propertyName);
//                    } else if (sqlParameter == null) {
//                        value = null;
//                    } else if (typeHandlerRegistry.hasTypeHandler(sqlParameter.getClass())) {
//                        value = sqlParameter;
//                    } else {
//                        MetaObject metaObject = configuration.newMetaObject(sqlParameter);
//                        value = metaObject.getValue(propertyName);
//                    }
//                    sql = sql.replaceFirst("\\?", value.toString());
//                }
//            }
//        }
//        return sql;
//    }
//}
