package cn.gtmap.realestate.exchange.core.ex;

import java.io.Serializable;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/11/10
 * @description 实体不存在异常
 */
public class EntityNotFoundException extends EntityException {
    private static final long serialVersionUID = -919405062590174599L;

    /**
     * @param message 异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public EntityNotFoundException(String message) {
        super(ENTITY_NOT_FOUND,message);
    }

    /**
     * @param clazz   实体类
     * @param message 异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public EntityNotFoundException(Class clazz, String message) {
        this(new StringBuilder().append(clazz.getSimpleName()).append(":").append(message).toString());
    }

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param clazz 实体类
     * @param key key名称
     * @param value key 值
     * @return
     * @description
     */
    public EntityNotFoundException(Class clazz, String key, Serializable value){
        this(clazz,key + "=" + value);
    }
}
