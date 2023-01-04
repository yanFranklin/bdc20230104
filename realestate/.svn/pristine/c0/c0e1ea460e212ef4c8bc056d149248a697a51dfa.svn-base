package cn.gtmap.interchange.core.support.mybatis.page.dialect;

import org.apache.commons.lang3.StringUtils;

/**
 * SqlServer方言，支持sqlserver2005及以上
 *
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2015/12/20
 */
public class SqlServerDialect extends Dialect {
    /**
     * 数据库本身是否支持分页查询
     *
     * @return {@code true} 支持分页查询
     */
    @Override
    public boolean supportsLimit() {
        return true;
    }

    /**
     * 将sql包装成数据库支持的特有查询语句
     *
     * @param sql    SQL语句
     * @param offset 开始位置
     * @param limit  每页显示的记录数
     * @return 数据库专属分页查询sql
     */
    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimitStringPrivate(sql, offset, limit);
    }

    private String getLimitStringPrivate(String querySqlString, int offset, int limit) {
        StringBuilder pagingBuilder = new StringBuilder();
        String orderby = getOrderByPart(querySqlString);
        String distinctStr = "";

        String loweredString = querySqlString.toLowerCase();
        String sqlPartString = querySqlString;
        if (loweredString.trim().startsWith("select")) {
            int index = 6;
            if (loweredString.startsWith("select distinct")) {
                distinctStr = "DISTINCT ";
                index = 15;
            }
            sqlPartString = sqlPartString.substring(index);
        }
        pagingBuilder.append(sqlPartString);

        if (StringUtils.isBlank(orderby)) {
            orderby = "ORDER BY CURRENT_TIMESTAMP";
        }

        StringBuilder result = new StringBuilder();
        result.append("WITH query AS (SELECT ")
                .append(distinctStr)
                .append("TOP 100 PERCENT ")
                .append(" ROW_NUMBER() OVER (")
                .append(orderby)
                .append(") as __row_number__, ")
                .append(pagingBuilder)
                .append(") SELECT * FROM query WHERE __row_number__ BETWEEN ")
                .append(offset + 1).append(" AND ").append(offset + limit)
                .append(" ORDER BY __row_number__");

        return result.toString();
    }

    private static String getOrderByPart(String sql) {
        String loweredString = sql.toLowerCase();
        int orderByIndex = loweredString.indexOf("order by");
        if (orderByIndex != -1) {
            return sql.substring(orderByIndex);
        } else {
            return "";
        }
    }
}
