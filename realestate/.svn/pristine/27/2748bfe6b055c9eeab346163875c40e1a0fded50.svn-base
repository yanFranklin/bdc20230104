package cn.gtmap.realestate.inquiry.core.dbs;

import cn.gtmap.realestate.common.util.StringToolUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/06/16
 * @description
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
@Component
public class DyncmicDataSourceInterceptor implements Interceptor {


    @Value("${dynamic.enable:false}")
    boolean dynamicEnable;

    @Value("${changeDataSource.method:}")
    private String changeSourceMethod;

    @Autowired
    SwitchDB switchDB;

    private static final String DB_SLAVE = "salve";

    private static Logger logger = LoggerFactory.getLogger(DyncmicDataSourceInterceptor.class);

    /**
     * 进行拦截操作，增删改和事务操作使用master，查询使用slave，里面有具体的实现代码，感兴趣可以学习mybatis源码去理解
     *
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = null;
        Object[] objects = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement) objects[0];
        String lookupKey = "default";

        try {
            //获取方法名
            String method = mappedStatement.getId().substring(mappedStatement.getId().lastIndexOf(".")+1);
            //如果当前线程中存在数据源类型，说明通过AOP特殊处理了，不需要改变类型
            if (StringUtils.isBlank(DynamicDataSourceContextHolder.getDataSource("aop")) && dynamicEnable) {
                //判断方法是否存在事务
                Boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();
                //如果存在事务，则同一事务内读写都使用主数据源
                if (!synchronizationActive) {
                    //获取sql语句的类型 （UNKNOWN, INSERT, UPDATE, DELETE, SELECT）
                    String sqlCommandType = mappedStatement.getSqlCommandType().toString();
                    //如果是查询，则使用从数据源
                    if (StringUtils.equals(sqlCommandType, "SELECT")) {
                        lookupKey = DB_SLAVE;
                    }
                }
                //设置当前线程的数据源
                switchDB.change(lookupKey);
            }
            logger.debug("设置方法:{};use:{};SqlCommanType:{}",mappedStatement.getId(),lookupKey,mappedStatement.getSqlCommandType().name());
            result = invocation.proceed();
        } catch (Throwable ex) {
            ex.printStackTrace();
            logger.info(String.format("Choose DataSourceType error, method:%s, msg:%s", mappedStatement.getId(), ex.getMessage()));
        } finally {
            //清理连接类型，避免对后续在本线程上执行的操作产生影响
            DynamicDataSourceContextHolder.clearDataSourceKey();
        }
        return result;
    }

    @Override
    public Object plugin(Object target) {
        //Executor表示含有增删改查的操作 的对象
        if (target instanceof Executor) {
            //有增删改查的操作,就调用拦截方法
            return Plugin.wrap(target, this);
        }
        //无增删改查的操作。不做处理
        return target;
    }

    @Override
    public void setProperties(Properties arg0) {

    }
}
