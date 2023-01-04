package cn.gtmap.interchange.core.support.mybatis.page;


import cn.gtmap.interchange.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.interchange.core.support.mybatis.page.dialect.DatabaseDialectShortName;
import cn.gtmap.interchange.core.support.mybatis.page.dialect.Dialect;
import cn.gtmap.interchange.core.support.mybatis.page.helper.DialectHelper;
import cn.gtmap.interchange.core.support.mybatis.page.helper.SqlHelper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PatternMatchUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0 2017-3-30
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor {
    private static final int MAPPED_STATEMENT_INDEX = 0;
    private static final int PARAMETER_INDEX = 1;
    private static final int ROWBOUNDS_INDEX = 2;
    private static final String LOADTOTAL = "loadTotal";
    private static final ThreadLocal<Integer> PAGINATION_TOTAL = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    private Dialect dialect;
    private String mappedStatementIdRegex;

    /**
     * Get Pagination total
     *
     * @return
     */
    public static int getPaginationTotal() {
        return PAGINATION_TOTAL.get();
    }

    /**
     * Set Pagination total
     * lst 提供set方法在自己定义的PaginationInterceptor拦截器中赋值total
     *
     * @return
     */
    public static void setPaginationTotal(Integer total) {
        PAGINATION_TOTAL.set(total);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];

        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();

        boolean intercept = PatternMatchUtils.simpleMatch(mappedStatementIdRegex, ms.getId());

        if (intercept && dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            final BoundSql boundSql = ms.getBoundSql(parameter);
            //wzj 增加分页查询排序方法
            String initSql = boundSql.getSql().trim();
            Map<String, Object> parameterMap = new HashMap<String, Object>();
            if (parameter != null) {
                parameterMap = JSONObject.parseObject(JSONObject.toJSONString(parameter), Map.class);
            }
            String sql = initSql;
            if (parameterMap.get("sort") != null) {
                String orderBy = parameterMap.get("sort").toString();
                sql = "SELECT * FROM (" + initSql + ") ORDER BY " + orderBy;
            }
            final Executor executor = (Executor) invocation.getTarget();

            HashMap map = JSONObject.parseObject(JSONObject.toJSONString(parameter), HashMap.class);
            if (map == null || !map.containsKey(LOADTOTAL) || map.get(LOADTOTAL) == null || (Boolean) map.get(LOADTOTAL)) {
                Connection connection = executor.getTransaction().getConnection();
                int count = SqlHelper.getCount(ms, connection, parameter, dialect);
                PaginationInterceptor.setPaginationTotal(count);
            } else {
                PaginationInterceptor.setPaginationTotal(-1);
            }
            String limitSql = dialect.getLimitString(sql, offset, limit);
            LOGGER.info("分页查询sql{},查询条件{}", limitSql, JSON.toJSONString(parameterMap));
            MappedStatement newMs = newMappedStatement(ms, boundSql, limitSql);

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialectClass");
        if (StringUtils.isBlank(dialectClass)) {
            String dialectShortName = properties.getProperty("dialect");
            checkDialect(dialectShortName);
            dialect = DialectHelper.getDialect(DatabaseDialectShortName.valueOf(dialectShortName.toUpperCase()));
        } else {
            try {
                dialect = (Dialect) Class.forName(dialectClass).newInstance();
            } catch (Exception e) {
                LOGGER.error("Plug-in [PaginationInterceptor] cannot create dialect instance by dialectClass: " + dialectClass, e);
                throw new ClassCastException("Plug-in [PaginationInterceptor] cannot create dialect instance by dialectClass: " + dialectClass);
            }
        }

        mappedStatementIdRegex = properties.getProperty("stmtIdRegex", "*.*ByPage");

    }

    private void checkDialect(String dialectShortName) {
        DatabaseDialectShortName.valueOf(dialectShortName.toUpperCase());
    }


    private MappedStatement newMappedStatement(final MappedStatement ms,
                                               final BoundSql boundSql,
                                               final String sql) {
        BoundSql newBoundSql = newBoundSql(ms, boundSql, sql);
        RawSqlSource sqlSource = new RawSqlSource(newBoundSql);
        MappedStatement.Builder builder = new MappedStatement.Builder(
                ms.getConfiguration(),
                ms.getId(),
                sqlSource,
                ms.getSqlCommandType()
        );

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        String[] keyProperties = ms.getKeyProperties();
        builder.keyProperty(keyProperties == null ? null : keyProperties[0]);

        builder.timeout(ms.getTimeout());

        builder.parameterMap(ms.getParameterMap());

        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());

        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());

        return builder.build();
    }

    private BoundSql newBoundSql(final MappedStatement ms,
                                 final BoundSql boundSql,
                                 final String sql) {
        BoundSql newBoundSql = new BoundSql(
                ms.getConfiguration(),
                sql,
                boundSql.getParameterMappings(),
                boundSql.getParameterObject()
        );

        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        return newBoundSql;
    }

    public class RawSqlSource implements SqlSource {
        private BoundSql boundSql;

        public RawSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
