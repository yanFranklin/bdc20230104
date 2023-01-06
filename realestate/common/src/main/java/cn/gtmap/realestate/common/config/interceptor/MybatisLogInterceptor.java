package cn.gtmap.realestate.common.config.interceptor;

import cn.gtmap.realestate.common.config.LogSwitchConfig;
import cn.gtmap.server.core.send.SendRedisFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.*;

/**
 * 日志采集 Mybatis 请求拦截器
 * @version 1.0, 2022/4/11
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
//        ,@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class MybatisLogInterceptor implements Interceptor {
    private String appName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisLogInterceptor.class);

    public MybatisLogInterceptor() {
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(!LogSwitchConfig.getInstance().isMybatisSwitch()){
            return invocation.proceed();
        }

        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();
        String sql = this.getSql(configuration, boundSql);
        try {
            SendRedisFactory.sendMsgToRedis(sql, this.getClass().getName(), "intercept", "MYBATIS", this.appName);
        } catch (Exception var10) {
            LOGGER.error("mybatis拦截器日志异常：{}", var10);
        }

        try {
            return invocation.proceed();
        } catch (InvocationTargetException var8) {
            LOGGER.error("mybatis拦截器日志异常：{}", var8);
        } catch (IllegalAccessException var9) {
            LOGGER.error("mybatis拦截器日志异常：{}", var9);
        }
        return null;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.appName = properties.getProperty("appName");
    }

    private String getSql(Configuration configuration, BoundSql boundSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterObject != null && parameterMappings.size() != 0) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", this.getParameterValue(parameterObject));
            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                Iterator i$ = parameterMappings.iterator();

                while(i$.hasNext()) {
                    ParameterMapping parameterMapping = (ParameterMapping)i$.next();
                    String propertyName = parameterMapping.getProperty();
                    Object obj;
                    if (metaObject.hasGetter(propertyName)) {
                        obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", this.getParameterValue(obj));
                        sql = sql.replaceAll("RDS_CHAR_DOLLAR", "\\$");
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", this.getParameterValue(obj));
                        sql = sql.replaceAll("RDS_CHAR_DOLLAR", "\\$");
                    }
                }
            }

            return sql;
        } else {
            return sql;
        }
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(2, 2, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else if (obj != null) {
            value = obj.toString();
        } else {
            value = "";
        }

        value = value.replaceAll("\\$", "RDS_CHAR_DOLLAR");
        return value;
    }
}
