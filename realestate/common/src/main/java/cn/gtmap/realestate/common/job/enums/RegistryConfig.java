package cn.gtmap.realestate.common.job.enums;

/**
 * Created by  on 17/5/10.
 */
public class RegistryConfig {

    /**
     * 注册器心跳时长
     */
    public static final int BEAT_TIMEOUT = 30;
    /**
     * 注册器死亡时间
     */
    public static final int DEAD_TIMEOUT = BEAT_TIMEOUT * 3;

    /**
     * 注册器 注册类型 枚举值
     */
    public enum RegistType{ EXECUTOR, ADMIN }

}
