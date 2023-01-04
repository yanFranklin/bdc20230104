package cn.gtmap.realestate.common.core.support.mybatis.page.helper;

import cn.gtmap.realestate.common.core.support.mybatis.mapper.EntityMapper;
import cn.gtmap.realestate.common.core.support.mybatis.page.dialect.Dialect;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */
public abstract class SqlHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityMapper.class);

    private SqlHelper() {
    }

    public static int getCount(final MappedStatement ms, final Connection connection,
                               final Object parameterObject, Dialect dialect) throws SQLException {
        BoundSql boundSql = ms.getBoundSql(parameterObject);
        String countSql = dialect.getCountString(boundSql.getSql());

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(countSql);
            DefaultParameterHandler handler = new DefaultParameterHandler(ms, parameterObject, boundSql);
            handler.setParameters(preparedStatement);
            resultSet = preparedStatement.executeQuery();

            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }

            return count;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            closeStatement(preparedStatement);
        }
    }

    private static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error(e.getMessage(), e);
                // ignore
            }
        }
    }
}
