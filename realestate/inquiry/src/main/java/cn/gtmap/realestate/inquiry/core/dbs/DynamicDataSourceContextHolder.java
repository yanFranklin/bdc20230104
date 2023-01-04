package cn.gtmap.realestate.inquiry.core.dbs;

import org.apache.commons.lang3.StringUtils;

/**
 * @description 通过 ThreadLocal 获取和设置线程安全的数据源 key
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {};

    public static synchronized void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static String getDataSource(String aop) {
        String db = contextHolder.get();
        //如果是拦截器查看线程中的数据源时，直接返回（null也返回）
        if(StringUtils.isNotBlank(aop)){
            return db;
        }
        if(db == null) {
            db = "default";
        }
        return db;
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
