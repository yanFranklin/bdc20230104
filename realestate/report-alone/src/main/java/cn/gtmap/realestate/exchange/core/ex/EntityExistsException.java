package cn.gtmap.realestate.exchange.core.ex;

import java.io.Serializable;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/11/10
 * @description 实体已存在异常
 */
public class EntityExistsException extends EntityException{
    private static final long serialVersionUID = 742358011600519648L;

    /**
     * @param message 异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public EntityExistsException(String message) {
        super(ENTITY_EXISTS,message);
    }

    /**
     * @param clazz   实体类
     * @param message 异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public EntityExistsException(Class clazz, String message) {
        this(new StringBuilder().append(clazz.getSimpleName()).append(":").append(message).toString());
    }

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param clazz 实体类
     * @param key key名称
     * @param value key 值
     * @return
     * @description 构造函数
     */
    public EntityExistsException(Class clazz, String key, Serializable value){
        this(clazz,key + "=" + value);
    }
}
