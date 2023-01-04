package cn.gtmap.realestate.exchange.core.ex;


/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/10/30
 * @description 不动产登记异常基类
 */
public class AppException extends RuntimeException implements ErrorCode {
    private static final long serialVersionUID = -7599107858890346456L;
    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 异常代码
     */
    private Integer code;
    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 错误提示信息
     */
    private String message;

    /**
     * @param
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 默认构造函数
     */
    public AppException() {
        super();
    }

    /**
     * @param code 异常代码
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description
     */
    public AppException(Integer code) {
        super();
        this.code = code;
    }

    /**
     * @param message 异常信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description
     */
    public AppException(String message) {
        super();
        this.message = message;
    }

    /**
     * @param code    异常代码
     * @param message 异常提示信息
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @description 构造函数
     */
    public AppException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "AppException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
