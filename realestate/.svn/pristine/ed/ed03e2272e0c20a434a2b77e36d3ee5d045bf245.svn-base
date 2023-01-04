package cn.gtmap.realestate.common.util.redisson;

import cn.gtmap.realestate.common.core.annotations.RedissonLock;
import cn.gtmap.realestate.common.core.ex.AppException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/24
 * @description  业务场景加锁AOP处理逻辑（设置优先级为1，高于事务执行优先级）
 */
@Service
@Aspect
@Order(1)
public class RedissonLockService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonLockService.class);

    @Autowired
    private RedissonClient redissonClient;


    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @description 对标注了@RedissonLock注解的逻辑进行拦截
     */
    @Pointcut("@annotation(cn.gtmap.realestate.common.core.annotations.RedissonLock)")
    private void point(){
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param joinPoint 切点对象
     * @return {Object} 业务逻辑执行结果
     * @description 业务逻辑加锁处理
     */
    @Around("point()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取注解信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RedissonLock redissonLock = method.getAnnotation(RedissonLock.class);
        String description = redissonLock.description();

        // 获取锁并进行加锁处理
        RLock lock = this.getLock(redissonLock);
        try {
            boolean lockResult = lock.tryLock(redissonLock.waitTime(), redissonLock.leaseTime(), redissonLock.unit());
            if(!lockResult) {
                LOGGER.warn("{} 当前应用实例未争抢到任务（可能其它实例在处理），处理中止！", description);
                return null;
            }

            return joinPoint.proceed();
        } catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new AppException(description + "处理加锁逻辑失败！");
        } finally {
            if(null != lock && lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    /**
     * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
     * @param redissonLock 锁注解
     * @return {RLock} 锁对象
     * @description 获取指定类型的分布式锁
     */
    private RLock getLock(RedissonLock redissonLock){
        switch (redissonLock.lockType()){
            case REENTRANT_LOCK:
                return redissonClient.getLock(redissonLock.lockKey());

            case FAIR_LOCK:
                return redissonClient.getFairLock(redissonLock.lockKey());

            case READ_LOCK:
                return redissonClient.getReadWriteLock(redissonLock.lockKey()).readLock();

            case WRITE_LOCK:
                return redissonClient.getReadWriteLock(redissonLock.lockKey()).writeLock();

            default:
                throw new AppException("目前不支持的Redisson锁类型:" + redissonLock.lockType().name());
        }
    }
}
