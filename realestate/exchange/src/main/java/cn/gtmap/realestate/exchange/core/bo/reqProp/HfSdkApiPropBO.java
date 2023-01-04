package cn.gtmap.realestate.exchange.core.bo.reqProp;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-10-09
 * @description
 */
public class HfSdkApiPropBO extends ReqProBO{

    private String sdkAppClassName;

    private String host;

    private String port;

    public String getSdkAppClassName() {
        return sdkAppClassName;
    }

    public void setSdkAppClassName(String sdkAppClassName) {
        this.sdkAppClassName = sdkAppClassName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
