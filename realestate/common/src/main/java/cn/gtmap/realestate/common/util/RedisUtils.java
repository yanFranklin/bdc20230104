package cn.gtmap.realestate.common.util;

import cn.gtmap.realestate.common.core.ex.AppException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/10
 * @description Redis操作工具类
 */
@Service
public class RedisUtils<E> {
    private static final String NO_FOUND_KEY = "请求key为空！";
    private static final String NO_FOUND_KEY_VALUE = "请求key或value为空！";

    /**
     * Redis操作
     */
    private RedisTemplate redisTemplate;

    /**
     * Redis连接
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 设置Redis序列化形式
     */
    @PostConstruct
    public void setRedisKeySerializer(){
        // 这里需要单独新建一个实例，不要自动注入，避免引用容器中的单例对象，
        // 造成这边设置序列化影响原有对象，造成权限认证失败（因为大云采用默认序列化）
        this.redisTemplate = new RedisTemplate();
        this.redisTemplate.setConnectionFactory(redisConnectionFactory);
        this.redisTemplate.afterPropertiesSet();

        RedisSerializer stringSerializer = new StringRedisSerializer();
        this.redisTemplate.setKeySerializer(stringSerializer);
        this.redisTemplate.setValueSerializer(stringSerializer);
        this.redisTemplate.setHashKeySerializer(stringSerializer);
        this.redisTemplate.setHashValueSerializer(stringSerializer);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键值
     * @param collection 元素集合
     * @param timeout 失效时间
     * @description 新增Set类型键值
     */
    public String addSetValue(String key, Collection collection, long timeout){
        if(StringUtils.isBlank(key) || CollectionUtils.isEmpty(collection)){
            throw new NullPointerException("Redis新增Set类型参数为空！");
        }

        redisTemplate.opsForSet().add(key, collection.toArray());
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键值
     * @description 获取指定key的set类型值
     */
    public Set<E> getSetValue(String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键值
     * @description 删除指定key缓存
     */
    public void deleteKey(String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.delete(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param value 值
     * @description 新增String类型键值
     */
    public String addStringValue(String key, String value, long timeout){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return key;
    }

    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @param key 键
     * @param value 值
     * @description 新增String类型键值 (持久化)
     */
    public String setStringValue(String key, String value){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.persist(key);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param value 值
     * @description 新增String类型键值
     */
    public String addStringValue(String key, String value){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForValue().set(key, value);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 获取指定String类型键值
     */
    public String getStringValue(String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        Object value=redisTemplate.opsForValue().get(key);
        return value==null?null:String.valueOf(value);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param hashkey Hash键
     * @param value 值
     * @description 新增Hash类型数据
     */
    public String addHashValue(String key, String hashkey, String value, long timeout){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashkey)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForHash().put(key, hashkey, value);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param hashkey Hash键
     * @param value 值
     * @description 新增Hash类型数据
     */
    public String addHashValue(String key, String hashkey, String value){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashkey)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForHash().put(key, hashkey, value);
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param hashkey Hash键
     * @description 获取Hash类型数据
     */
    public String getHashValue(String key, String hashkey){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashkey)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        return String.valueOf(redisTemplate.opsForHash().get(key, hashkey));
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 自增String类型键值
     */
    public Long getIncrementValue(String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        return redisTemplate.opsForValue().increment(key, 1L);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 判断是否存在键
     */
    public boolean isExistKey(String key){
        if(StringUtils.isBlank(key)){
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 获取指定Hash的键值
     */
    public Set<String> getHashKeySet(String key){
        if(StringUtils.isBlank(key)){
            return Collections.emptySet();
        }
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param hashkey Hash键
     * @description 判断是否存在指定Hash键值
     */
    public Boolean isExistHashKey(String key, String hashkey){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashkey)){
            return false;
        }
        return redisTemplate.opsForHash().hasKey(key, hashkey);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param value 值
     * @param score 分值
     * @description 新增ZSet键值
     */
    public Boolean addZsetKey(String key, String value, Double score){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(value) || null == score){
            return false;
        }
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param start 开始值
     * @param end 结束分值
     * @description 查询指定区间ZSet键值
     */
    public Set getZsetValue(String key, double start, double end){
        if(StringUtils.isBlank(key)){
            return Collections.emptySet();
        }
        return redisTemplate.opsForZSet().rangeByScore(key, start, end);
    }

    /**
     * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标
     * @description 获取指定有序集合中指定下标区间的元素
     */
    public Set getZsetRangeValue(String key, long start, long end){
        if(StringUtils.isBlank(key)){
            return Collections.emptySet();
        }
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param key 键
     * @param hashkey Hash键
     * @description 删除Hash类型数据
     */
    public void deleteHashValue(String key, String hashkey){
        if(StringUtils.isBlank(key) || StringUtils.isBlank(hashkey)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForHash().delete(key, hashkey);
    }

    /**
     * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
     * @param key 键
     * @description 获取Hash类型数据
     */
    public Map<Object,Object> getHash(String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 自增计数器
     * @param key
     * @return
     */
    public Long incr(String key, Long step) {
        if(StringUtils.isBlank(key)) {
            throw new NullPointerException(NO_FOUND_KEY);
        }

        if(null == step) {
            step = new Long(1);
        }

        return redisTemplate.opsForValue().increment(key, step.longValue());
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param value 值
     * @description 新增String类型键值
     */
    public String addLongValue(String key, Long value){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException(NO_FOUND_KEY);
        }
        redisTemplate.opsForValue().set(key, String.valueOf(value));
        return key;
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param values 值
     * @return 对应key集合长度
     * @description 新增List类型键值(结尾追加)
     */
    public Long addListValue(String key, List<String> values) {
        if(StringUtils.isBlank(key) || null == values || 0 == values.size()) {
            throw new AppException(NO_FOUND_KEY_VALUE);
        }
        return redisTemplate.opsForList().rightPushAll(key, values);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 获取List第一个元素值（并剔除集合）
     */
    public String getListFirstValue(String key) {
        if(StringUtils.isBlank(key)) {
            throw new AppException(NO_FOUND_KEY);
        }
        Object value = redisTemplate.opsForList().leftPop(key);
        if(null == value) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @param values 值
     * @return 对应key集合长度
     * @description 新增Set类型键值
     */
    public Long addSetValue(String key, List<String> values) {
        if(StringUtils.isBlank(key) || null == values) {
            throw new AppException(NO_FOUND_KEY_VALUE);
        }
        return redisTemplate.opsForSet().add(key, values.toArray());
    }

    /**
     * @Author <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
     * @Description // 设置key的过期时间
     * @param key key
     * @param timeout 失效时间
     * @Date 2022/5/19 14:54
     **/
    public Boolean expire(String key, Long timeout){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("Redis的key类型参数为空！");
        }
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param key 键
     * @description 获取Set一个随机元素值（并剔除集合）
     */
    public String getSetRandomValue(String key) {
        if(StringUtils.isBlank(key)) {
            throw new AppException(NO_FOUND_KEY);
        }
        Object value = redisTemplate.opsForSet().pop(key);
        if(null == value) {
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * 从redis中获取key对应的过期时间;
     * 如果该值有过期时间，就返回相应的过期时间;
     *  如果该值没有设置过期时间，就返回-1;
     *  如果没有该值，就返回-2;
     * @param key 键
     * @return 过期时间
     */
    public Long getExpire(final String key){
        if(StringUtils.isBlank(key)){
            throw new NullPointerException("Redis的key类型参数为空！");
        }
        return redisTemplate.opsForValue().getOperations().getExpire(key);
    }
}
