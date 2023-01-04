package cn.gtmap.realestate.common.config.logaop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存日志实体
 */
public class LogCommonCacheMap {
    // 日志实体 k-v：方法名-方法功能描述
    public static Map<String,String> methodLogEntity = new ConcurrentHashMap<String,String>();
}
