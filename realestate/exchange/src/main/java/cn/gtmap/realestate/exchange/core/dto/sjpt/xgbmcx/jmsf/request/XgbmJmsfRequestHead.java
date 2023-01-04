package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.jmsf.request;
/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-27
 * @description
 */
public class XgbmJmsfRequestHead {
    private String xzqdm;

    private String token;

    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
