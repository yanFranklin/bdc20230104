package cn.gtmap.interchange.core.support.mybatis.page.dialect;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */
public final class DialectFactory {
    private DialectFactory() {
    }

    public static Dialect buildDialect(DatabaseDialectShortName databaseName) {
        switch (databaseName) {
            case MYSQL:
                return new MySQLDialect();
            case ORACLE:
                return new OracleDialect();
            case SQLSERVER:
                return new SqlServerDialect();
            case ORACLEORDER:
                return new OracleOrderDialect();
            default:
                throw new UnsupportedOperationException();
        }
    }
}
