package cn.gtmap.realestate.common.core.support.mybatis.page.dialect;

/**
 * 将分页嵌套2层处理
 * @author <a href="mailto:lisongtao@gmail.com">lst</a>
 * @version 1.0 2017-3-29
 */
public class OracleOrderDialect extends Dialect {
    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, int offset, int limit) {
        return getLimiString(sql, offset, Integer.toString(offset), Integer.toString(limit));
    }

    private String getLimiString(final String sql, final int offset,
                                 final String offsetPlaceholder, final String limitPlaceholder) {

        StringBuilder pagingSelect = new StringBuilder(sql.length() + 100);
        if (offset >= 0) {
            pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
        } else {
            pagingSelect.append("select * from ( ");
        }
        pagingSelect.append(sql);
        if (offset >= 0) {
            String endString = String.valueOf(Integer.parseInt(offsetPlaceholder) * Integer.parseInt(limitPlaceholder) + Integer.parseInt(limitPlaceholder));
            String startString = String.valueOf(Integer.parseInt(offsetPlaceholder) * Integer.parseInt(limitPlaceholder));
            pagingSelect.append(" ) row_   where rownum <=  ").append(endString).append(" )  where rownum_ > ").append(startString);
        } else {
            pagingSelect.append(" ) where rownum <= ").append(limitPlaceholder);
        }

        return pagingSelect.toString();
    }
}
