package cn.gtmap.realestate.common.core.dto.exchange.hefei.dzzz.response;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2020-05-06
 * @description 合肥电子证照请求制证接口响应结果参数
 */
public class HefeiDzzzDataResponseDTO {
    //电子证照编码
    private String code;
    //目录编码
    private String contentCode;
    //标识id
    private String id;
    //证照下载地址
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContentCode() {
        return contentCode;
    }

    public void setContentCode(String contentCode) {
        this.contentCode = contentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "HefeiDzzzDataResponseDTO{" +
                "code='" + code + '\'' +
                ", contentCode='" + contentCode + '\'' +
                ", id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
