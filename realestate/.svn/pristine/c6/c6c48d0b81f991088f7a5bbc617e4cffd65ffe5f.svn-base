package cn.gtmap.realestate.exchange.core.support.mybatis.page.dialect;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */
public class MySQLDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(final String sql, final int offset, final int limit) {
        return getLimitString(sql, offset, Integer.toString(offset * limit), Integer.toString(limit));
    }

    private String getLimitString(final String sql, final int offset,
                                  final String offsetPlaceholder, final String limitPlaceholder) {
        StringBuilder stringBuilder = new StringBuilder(sql);
        stringBuilder.append(" limit ");
        if (offset > 0) {
            stringBuilder.append(offsetPlaceholder).append(",").append(limitPlaceholder);
        } else {
            stringBuilder.append(limitPlaceholder);
        }

        return stringBuilder.toString();
    }
}
