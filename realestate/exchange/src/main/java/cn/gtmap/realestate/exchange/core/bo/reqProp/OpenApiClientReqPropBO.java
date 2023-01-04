package cn.gtmap.realestate.exchange.core.bo.reqProp;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-06
 * @description 综合查询方式请求属性
 */
public class OpenApiClientReqPropBO extends ReqProBO{

    private String url;

    private String appKey;

    private String userName;

    private String messageId;

    private String status;

    private String contentType;

    private String requestType;

    private String skprivateKeyPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getSkprivateKeyPath() {
        return skprivateKeyPath;
    }

    public void setSkprivateKeyPath(String skprivateKeyPath) {
        this.skprivateKeyPath = skprivateKeyPath;
    }

    @Override
    public String toString() {
        return "OpenApiClientReqPropBO{" +
                "url='" + url + '\'' +
                ", appKey='" + appKey + '\'' +
                ", userName='" + userName + '\'' +
                ", messageId='" + messageId + '\'' +
                ", status='" + status + '\'' +
                ", contentType='" + contentType + '\'' +
                ", requestType='" + requestType + '\'' +
                ", skprivateKeyPath='" + skprivateKeyPath + '\'' +
                '}';
    }
}
