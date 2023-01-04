package cn.gtmap.realestate.common.core.bo;

/**
 * @author <a href="mailto:zhangwentao@gtmap.cn">zhangwentao</a>
 * @version 1.0 2019/12/18
 * @description 电子签章请求头
 */
public class RequestHeadBO {

    /**
     * 数字签名
     */
    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "RequestHeadBO{" +
                "sign='" + sign + '\'' +
                '}';
    }
}
