package cn.gtmap.realestate.exchange.core.bo.xsd;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-15
 * @description WebService 服务
 */
public class WebServiceRequestBO {

    private String url;

    private String uri;

    private String operationName;

    private boolean useSOAPAction = true;

    private String soapActionUri;

    private String timeout;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public boolean isUseSOAPAction() {
        return useSOAPAction;
    }

    public void setUseSOAPAction(boolean useSOAPAction) {
        this.useSOAPAction = useSOAPAction;
    }

    public String getSoapActionUri() {
        return soapActionUri;
    }

    public void setSoapActionUri(String soapActionUri) {
        this.soapActionUri = soapActionUri;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }
}
