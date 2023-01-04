package cn.gtmap.realestate.common.core.ex;

/**
 * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
 * @version 1.0, 2018/11/10
 * @description 非法参数异常
 */
public class IllegalArgumentException extends AppException {
    private static final long serialVersionUID = 1995655314378238587L;
    private static final String ILLEGAL_ARG_MSG_PREFIX = "非法参数";

    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @param argName 参数名
     * @return
     * @description
     */
    public IllegalArgumentException(String argName) {
        super(ILLEGAL_ARG, ILLEGAL_ARG_MSG_PREFIX +":"+argName);
    }
}
