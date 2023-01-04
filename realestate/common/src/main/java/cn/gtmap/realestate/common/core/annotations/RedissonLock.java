package cn.gtmap.realestate.common.core.annotations;

import cn.gtmap.realestate.common.core.enums.LockType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/24
 * @description  基于Redis分布式框架Redisson的分布式锁注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedissonLock {
    /**
     * 锁标识
     */
    @AliasFor("lockKey")
    String lockKey();

    /**
     * 场景描述
     */
    @AliasFor("description")
    String description() default "不动产登记";

    /**
     * 锁类型，默认可重入锁
     */
    @AliasFor("lockType")
    LockType lockType() default LockType.REENTRANT_LOCK;

    /**
     * 获取锁等待时间，默认120秒
     */
    @AliasFor("waitTime")
    long waitTime() default 120L;

    /**
     * 锁自动释放时间，默认60秒
     */
    @AliasFor("leaseTime")
    long leaseTime() default 60L;

    /**
     * 时间单位 : 秒（获取锁等待时间和持锁时间都用此单位）
     */
    TimeUnit unit() default TimeUnit.SECONDS;
}
