package cn.gtmap.realestate.exchange.core.bo.reqProp;

import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-16
 * @description
 */
public class HttpReqPropBO extends ReqProBO{
    /**
     * 这里需要显式设置序列化值，避免增删字段造成序列化值变更
     * 例如外网接口调用发送日志到MQ，登记系统再监听序列化失败
     * added by zhuyong 20221107
     */
    private static final long serialVersionUID = 4321930928405725027L;

    private String url;

    private String appKey;

    //头部token
    private String xPlatToken;

    private String contentType;

    // 参数是否必填
    private boolean paramRequired = true;
    // 是否为https请求
    private boolean httpsRequest = false;
    // get请求是否要用NameValuePair赋值
    private boolean getUseNameValuePair = false;

    // get请求是否需要url参数转码
    private boolean getUrlEncode = false;

    // 强转实体
    private String paramClass;
    // 超时时间
    private String soTimeout;
    //token的请求接口
    private String tokenInterface;
    //替换url实现类
    private String replaceUrl;
    //替换url操作类型：delete（删除）等
    private String replaceUrlHandlerType;
    //需要调用的方法名
    private String method;
    //区县代码
    private String qxdm;
    //参数加密
    private boolean csjm = true;
    //header传的token
    private String token;
    // 请求头ID
    private String workspaceId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReplaceUrlHandlerType() {
        return replaceUrlHandlerType;
    }

    public void setReplaceUrlHandlerType(String replaceUrlHandlerType) {
        this.replaceUrlHandlerType = replaceUrlHandlerType;
    }

    public String getReplaceUrl() {
        return replaceUrl;
    }

    public void setReplaceUrl(String replaceUrl) {
        this.replaceUrl = replaceUrl;
    }

    public String getTokenInterface() {
        return tokenInterface;
    }

    public void setTokenInterface(String tokenInterface) {
        this.tokenInterface = tokenInterface;
    }

    public boolean isGetUseNameValuePair() {
        return getUseNameValuePair;
    }

    public void setGetUseNameValuePair(boolean getUseNameValuePair) {
        this.getUseNameValuePair = getUseNameValuePair;
    }

    public String getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(String soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

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

    public boolean isParamRequired() {
        return paramRequired;
    }

    public void setParamRequired(boolean paramRequired) {
        this.paramRequired = paramRequired;
    }

    public String getParamClass() {
        return paramClass;
    }

    public void setParamClass(String paramClass) {
        this.paramClass = paramClass;
    }

    public String getxPlatToken() {
        return xPlatToken;
    }

    public void setxPlatToken(String xPlatToken) {
        this.xPlatToken = xPlatToken;
    }

    public boolean isHttpsRequest() {
        return httpsRequest;
    }

    public void setHttpsRequest(boolean httpsRequest) {
        this.httpsRequest = httpsRequest;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public boolean isCsjm() {
        return csjm;
    }

    public void setCsjm(boolean csjm) {
        this.csjm = csjm;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public boolean isGetUrlEncode() {
        return getUrlEncode;
    }

    public void setGetUrlEncode(boolean getUrlEncode) {
        this.getUrlEncode = getUrlEncode;
    }
}
