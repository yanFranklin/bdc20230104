package cn.gtmap.realestate.exchange.core.ex;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/21
 * @description 用户信息获取异常
 */
public class UserInformationAccessException extends AppException {
    private static final long serialVersionUID = 48375281170088377L;
    private static final String MISSING_USER_INFORMATION = "缺失用户信息";

    public UserInformationAccessException(String argName) {
        super(NPE_EX, MISSING_USER_INFORMATION + ":" + argName);
    }
}
