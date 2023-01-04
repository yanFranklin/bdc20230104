package cn.gtmap.realestate.exchange.core.bo.reqProp;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-05-23
 * @description REST请求属性参数 实体
 */
public class RestReqPropBO extends ReqProBO{

    private String url;

    private String contentType;

    // 超时时间
    private String soTimeout;

    public String getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(String soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
