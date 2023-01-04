package cn.gtmap.realestate.common.core.enums;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/24
 * @description  Redis锁类型枚举定义
 */
public enum LockType {
    /**
     * 可重入锁
     */
    REENTRANT_LOCK,

    /**
     * 公平锁
     */
    FAIR_LOCK,

    /**
     * 读锁
     */
    READ_LOCK,

    /**
     * 写锁
     */
    WRITE_LOCK;
}
