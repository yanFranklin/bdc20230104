package cn.gtmap.realestate.exchange.core.bo.reqProp;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-09-24
 * @description
 */
public class NtCssnjWebservicePropBO extends ReqProBO {

    private String methodName;

    private String url;

    private String qName;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }
}
