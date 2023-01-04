package cn.gtmap.realestate.certificate.core.service.impl.redisson;

import cn.gtmap.realestate.certificate.core.service.redisson.BdcDzzzRedissionService;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:chenyongqiang@gtmap.cn">chenyongqiang</a>
 * @Version 1.0
 * @description 1.0，2019/3/21
 */
@Service
public class BdcDzzzRedissionServiceImpl implements BdcDzzzRedissionService {

    @Autowired
    private RedissonClient redissonClient;
    @Value("${token.expiryTime.city}")
    protected long tokenExpiryTimeCity;

    @Override
    public <V> V getMapCacheValue(String rMapKey, String key, Class<V> vClass) {
        V value = null;
        RMapCache<String, V> rMapCache = redissonClient.getMapCache(rMapKey);
        if (null != rMapCache) {
            value = rMapCache.get(key);
        }
        return value;
    }

    @Override
    public <V> V setMapCacheValue(String rMapKey, String key, V value) {
        V v = null;
        RMapCache<String, V> rMapCache = redissonClient.getMapCache(rMapKey);
        if (null != rMapCache) {
            v = rMapCache.put(key, value, tokenExpiryTimeCity, TimeUnit.MILLISECONDS);
        }

        return v;
    }
}
