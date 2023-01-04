package cn.gtmap.realestate.certificate.core.service.redisson;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
public interface BdcDzzzRedissionService {

    /**
     *
     * @param rMapKey
     * @param key
     * @return
     * @description redission map查询
     */
    <V> V getMapCacheValue(String rMapKey, String key, Class<V> vClass);

    /**
     *
     * @param rMapKey
     * @param key
     * @param value
     * @description redission map增加
     */
    <V> V setMapCacheValue(String rMapKey, String key, V value);
}
