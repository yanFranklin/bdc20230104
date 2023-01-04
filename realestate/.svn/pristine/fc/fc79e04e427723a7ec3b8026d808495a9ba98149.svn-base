package cn.gtmap.realestate.common.config.interceptor;
/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/2.
 * @description
 */

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class }),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }) })
public class SQLErrorContextInterceptor implements Interceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(SQLErrorContextInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        } catch (Exception e) {
            // 取得各种值
            MappedStatement statement = (MappedStatement) invocation
                    .getArgs()[0];
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = statement.getBoundSql(parameter);
            LOGGER.error("sql方法：{}",statement.getId());
            LOGGER.error("sql语句：{}",boundSql.getSql());
            LOGGER.error("执行参数：{}",parameter);
            // 抛出异常
            throw e;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}
