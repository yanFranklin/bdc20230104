package cn.gtmap.realestate.etl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-05
 * @description
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 删除键为Key的数据项
     */
    public long del(String key){
        long result = 0;
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            LOGGER.error("redis 删除键异常：key {}",key,e);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取键为Key的数据项
     */
    public String get(String key){
        String result = null;
        try {
            result = (String)redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOGGER.error("redis 获取键异常：key {}",key,e);
        }
        return result;
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 不覆盖增加数据项（重复不插入）
     */
    public void setnx(String key,String value){
        try {
            redisTemplate.opsForValue().setIfAbsent(key,value);
        } catch (Exception e) {
            LOGGER.error("redis setIfAbsent 异常：key {},value:{}",key,value,e);
        }
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description key对应的list 右插入一个元素
     */
    public long rpush(String key,String value){
        long result = 0;
        try {
            result = redisTemplate.opsForList().rightPush(key,value);
        } catch (Exception e) {
            LOGGER.error("redis rightPush 异常：key {},value:{}",key,value,e);
        }
        return result;
    }

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param key
     * @return long
     * @description 获取 list长度
     */
    public long getListLength(String key){
        return redisTemplate.opsForList().size(key);
    }


    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 获取i和j区间的list
     */
    public List<String> lrange(String key, int i, int j){
        List<String> result = new ArrayList<>();
        try {
            result = redisTemplate.opsForList().range(key,i,j);
        } catch (Exception e) {
            LOGGER.error("redis range 异常：key {},i:{},j:{}",key,i,j,e);
        }
        return result;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description 根据key获取List
     */
    public List<String> getlist(String key){
        return lrange(key,0,-1);
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
     * @description list中  左出栈一个元素
     */
    public String lpop(String key){
        String result = null;
        try {
            result = (String)redisTemplate.opsForList().leftPop(key);
        } catch (Exception e) {
            LOGGER.error("redis leftPop 异常：key {}",key,e);
        }
        return result;
    }

}
