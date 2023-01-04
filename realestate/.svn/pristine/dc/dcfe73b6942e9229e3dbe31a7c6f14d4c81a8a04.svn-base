package cn.gtmap.realestate.exchange.core.support.mybatis.page.helper;


import cn.gtmap.realestate.exchange.core.support.mybatis.page.dialect.DatabaseDialectShortName;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.dialect.Dialect;
import cn.gtmap.realestate.exchange.core.support.mybatis.page.dialect.DialectFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:zjh527@gmail.com">loafer</a>
 * @version 1.0 2014-2-17
 */
public abstract class DialectHelper {
    private static Map<DatabaseDialectShortName, Dialect> MAPPERS = new HashMap();

    public static Dialect getDialect(DatabaseDialectShortName dialectShortName) {
        if (MAPPERS.containsKey(dialectShortName)) {
            return MAPPERS.get(dialectShortName);
        } else {
            Dialect dialect = DialectFactory.buildDialect(dialectShortName);
            MAPPERS.put(dialectShortName, dialect);
            return dialect;
        }
    }
}
