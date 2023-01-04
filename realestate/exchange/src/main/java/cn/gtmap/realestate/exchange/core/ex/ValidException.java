package cn.gtmap.realestate.exchange.core.ex;

import cn.gtmap.realestate.common.core.ex.ErrorCode;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-10
 * @description 自定义 验证失败 异常
 */
public class ValidException extends RuntimeException implements ErrorCode {

    private String message;

    /**
     * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
     * @param message 异常信息
     * @return
     * @description
     */
    public ValidException(String message){
        super(message);
        this.message = message;
    }
}
