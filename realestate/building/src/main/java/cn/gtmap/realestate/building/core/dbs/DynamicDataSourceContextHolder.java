package cn.gtmap.realestate.building.core.dbs;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/6/3
 * @description 通过 ThreadLocal 获取和设置线程安全的数据源 key
 */
public class DynamicDataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>() {
        /**
         * 将 default 数据源的 key 作为默认数据源的 key
         */
        @Override
        protected String initialValue() {
            return "default";
        }
    };

    public static synchronized void setDataSourceKey(String key) {
        contextHolder.set(key);
    }

    public static String getDataSourceKey() {
        return contextHolder.get();
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }
}
