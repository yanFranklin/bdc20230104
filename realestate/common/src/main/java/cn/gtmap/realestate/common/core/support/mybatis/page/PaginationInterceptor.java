package cn.gtmap.realestate.common.core.support.mybatis.page;


import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.dialect.DatabaseDialectShortName;
import cn.gtmap.realestate.common.core.support.mybatis.page.dialect.Dialect;
import cn.gtmap.realestate.common.core.support.mybatis.page.helper.DialectHelper;
import cn.gtmap.realestate.common.core.support.mybatis.page.helper.SqlHelper;
import cn.gtmap.realestate.common.util.CommonConstantUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
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
import java.util.*;

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

    // SQL查询限定数量上限
    public static final int SQL_MAX_LIMIT_1 = 10000;
    public static final int SQL_MAX_LIMIT_5 = 50000;
    // 程序装饰后的分页查询SQL开头语句
    // 对应 OracleDialect.getLimiString or  OracleOrderDialect.getLimiString
    public static final String PAGE_SQL_START = "select * from ( select row_.*, rownum rownum_ from";
    // 分页查询SQL ID 格式
    public static final String[] PAGE_SQL_FLAG = {"*.*ByPage", "*.*ByPageOrder", "*.*ByTdPage", "*.*ByTdPageOrder"};

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
     * 需要超出默认查询数量上限的SQL，例如默认最多查询1万条，但是这个SQL需要查询2万条，则需要单独配置声明，否则程序报错不允许查询
     * 配置格式：{'cn.gtmap.realestate.inquiry.core.mapper.BdcZszmMapper.listBdcZszmByPageOrder':150, 'B-SQL-ID': 5}
     */
    private Map<String, Integer> pageSqlLimit;

    /**
     * 不需要加查询数量限制的SQL，例如获取序列的SQL：select BDC_ZfCX_LSH_SEQ.nextVal from dual
     */
    private List<String> notLimitSqlSet;

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

        // 需要判断SQL是否有超出1万条记录查询需求，根据配置来提升查询数量范围
        boolean isSetSqlLimit = MapUtils.isNotEmpty(pageSqlLimit) && pageSqlLimit.containsKey(ms.getId());
        limit = getSqlLimit(ms.getId(), isSetSqlLimit, limit);

        final BoundSql boundSql = ms.getBoundSql(parameter);
        String initSql = boundSql.getSql().trim();

        boolean intercept = PatternMatchUtils.simpleMatch(mappedStatementIdRegex, ms.getId());
        if (intercept && dialect.supportsLimit() && (offset != RowBounds.NO_ROW_OFFSET || limit != RowBounds.NO_ROW_LIMIT)) {
            // 分页查询
			//wzj 增加分页查询排序方法
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

            String limitSql = sql;
            if(!StringUtils.startsWith(initSql, PAGE_SQL_START)) {
                limitSql = dialect.getLimitString(sql, offset, limit);
            }
            LOGGER.info("分页查询sql：{},查询条件：{}",  limitSql.replaceAll("\\s*[\\r\\n]+\\s*", " ").trim(), JSON.toJSONString(parameterMap));
            MappedStatement newMs = newMappedStatement(ms, boundSql, limitSql);

            queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
            queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        } else {
            // 非分页查询
            if(!isPageSql(ms.getId())  && !StringUtils.startsWith(initSql, PAGE_SQL_START) && isNeedLimit(ms.getId())) {
                String limitSql = dialect.getLimitString(initSql, offset, limit);
//                LOGGER.info("查询sql：{},查询条件：{}", limitSql.replaceAll("\\s*[\\r\\n]+\\s*", " ").trim(), JSON.toJSONString(parameter));

                MappedStatement newMs = newMappedStatement(ms, boundSql, limitSql);
                queryArgs[ROWBOUNDS_INDEX] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
                queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
            }
        }

        Object result = invocation.proceed();
        if(hasReachMaxLimit(result, ms.getId(), isSetSqlLimit)) {
            LOGGER.error("当前查询SQL结果数量达到限制10000条，大概率未限定查询条件，请核查功能，对应SQL：{}", ms.getId());
            throw new Exception("当前查询SQL结果数量达到限制10000条，大概率未限定查询条件，请核查功能，对应SQL：" + ms.getId());
        }
        return result;
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

    /**
     * 获取SQL查询限定记录数量
     * 1、分页查询：按照SQL指定的数量查询（默认上限不超过1万，如果确实需要超过1万条则单独配置 page.sql.limit）
     * 2、非分页查询：
     * 2.1 配置了 page.sql.limit 则在指定上限数量内进行查询（但是 0 <= 数量 <= SQL_MAX_LIMIT_5 ，否则设置5万条为上限。
     *     5万上限几乎能涵盖业务场景查询需求，根据淮安权利人房产数量，上限在4万多，即使批量查询也能满足。）
     * 2.2 没配置则默认上限 1万，此数值正常满足绝大部分业务场景。
     *
     * @param sqlId 当前查询SQL ID （对应Mapper中ID）
     * @param limit SQL默认限定查询记录数量
     * @return {int} SQL查询限定记录数量
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    public int getSqlLimit(String sqlId, boolean isSetSqlLimit, int limit) {
        if(RowBounds.NO_ROW_LIMIT == limit) {
            // 非分页查询
            if(isSetSqlLimit) {
                Integer sqlItemLimit = pageSqlLimit.get(sqlId);
                limit = Math.min(sqlItemLimit, SQL_MAX_LIMIT_5);
            } else {
                limit = SQL_MAX_LIMIT_1;
            }
        } else {
            // 分页查询
            if(MapUtils.isNotEmpty(pageSqlLimit) && pageSqlLimit.containsKey(sqlId)) {
                Integer sqlItemLimit = pageSqlLimit.get(sqlId);
                limit = Math.min(limit, sqlItemLimit);
                if(limit > SQL_MAX_LIMIT_5) {
                    limit = SQL_MAX_LIMIT_5;
                }
            } else {
                if(limit > SQL_MAX_LIMIT_1) {
                    limit = SQL_MAX_LIMIT_1;
                }
            }
        }
        return limit;
    }

    /**
     * SQL查询结果数量是否达到最大限定数量
     * @param sqlResult SQL查询结果
     * @param sqlId SQL ID
     * @param isSetSqlLimit 是否配置了限定数量
     * @return {boolean} 达到：true 未达到或不需要验证：false
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private boolean hasReachMaxLimit(Object sqlResult, String sqlId, boolean isSetSqlLimit) {
        if(!(sqlResult instanceof Collection)) {
            return false;
        }

        int sqlSize = CollectionUtils.size(sqlResult);
        if(isSetSqlLimit) {
            if(sqlSize >= SQL_MAX_LIMIT_5) {
                // 分页  ：SQL指定数量 >=5万 并且 配置声明需要查询达到5万
                // 不分页：配置声明需要查询达到5万
                // 这种情况下认为是业务场景实际需要查询这么多数据
                LOGGER.warn("当前SQL配置查询数量或传参limit不合理，请检查！对应SQL：{}", sqlId);
            }
            return false;
        } else {
            // 只有SQL未配置限定数量，且查询结果达到1万条，才抛异常提示。
            // 不管本身SQL是否分页，需要查询超过1万条，需要配置放开。
            return sqlSize >= SQL_MAX_LIMIT_1;
        }
    }

    /**
     * 判断是否是分页查询语句 （根据config-mybatis的 <prop key="stmtIdRegex">/prop>配置来确定分页SQL特征）
     * @param sqlId SQL ID
     * @return {boolean} true 是 ； false 否
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private boolean isPageSql(String sqlId) {
        for(String sqlIdItem : PAGE_SQL_FLAG) {
            if(PatternMatchUtils.simpleMatch(sqlIdItem, sqlId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断SQL是否需要加查询数量限制
     * @param sqlId SQL ID
     * @return {boolean} true 需要 ； false 不需要
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     */
    private boolean isNeedLimit(String sqlId) {
        if(PageNoLimitConstants.NO_LIMIT_SQL.contains(sqlId)) {
            return false;
        }

        if(null == notLimitSqlSet || notLimitSqlSet.isEmpty()) {
            return true;
        }
        return !notLimitSqlSet.contains(sqlId);
    }

    /**
     * **********  读取配置注入参数  ************
     */
    private String pageSqlLimitStr;

    private String notLimitSqlSetStr;

    public String getPageSqlLimitStr() {
        return pageSqlLimitStr;
    }

    public void setPageSqlLimitStr(String pageSqlLimitStr) {
        this.pageSqlLimitStr = pageSqlLimitStr;
        if(StringUtils.isNotBlank(pageSqlLimitStr)) {
            pageSqlLimit = JSONObject.parseObject(pageSqlLimitStr, Map.class);
        }
    }

    public String getNotLimitSqlSetStr() {
        return notLimitSqlSetStr;
    }

    public void setNotLimitSqlSetStr(String notLimitSqlSetStr) {
        this.notLimitSqlSetStr = notLimitSqlSetStr;
        if(StringUtils.isNotBlank(notLimitSqlSetStr)) {
            notLimitSqlSet = Arrays.asList(notLimitSqlSetStr.split(CommonConstantUtils.ZF_YW_DH));
        }
    }
}
