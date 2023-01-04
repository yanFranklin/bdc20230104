package cn.gtmap.realestate.exchange.core.ex;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/11/10
 * @description 实体异常类
 */
public class EntityException extends AppException{

    private static final long serialVersionUID = 8199716097303783442L;

    /**
     * @param message 错异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public EntityException(String message) {
        super(ENTITY_EX, message);
    }

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param code 异常代码
     * @param message 异常提示信息
     * @return
     * @description
     */
    public EntityException(Integer code,String message){
        super(code,message);
    }

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param clazz 实体类
     * @param message 异常提示信息
     * @return
     * @description 构造函数
     */
    public EntityException(Class clazz,String message){
        this(new StringBuilder().append(clazz.getSimpleName()).append(":").append(message).toString());
    }
}
