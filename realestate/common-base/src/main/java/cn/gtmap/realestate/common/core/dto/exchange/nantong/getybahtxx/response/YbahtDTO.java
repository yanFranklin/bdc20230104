package cn.gtmap.realestate.common.core.dto.exchange.nantong.getybahtxx.response;

/**
 * @author <a href="mailto:waangyongming@gtmap.cn">wangyongming</a>
 * @Date 2021/11/26
 * @description 获取已备案合同
 */
public class YbahtDTO {
    /**
     * 合同号
     */
    private String code;

    /**
     * 备案号
     */
    private String record;

    /**
     * 备案合同电子档
     */
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "YbahtDTO{" +
                "code='" + code + '\'' +
                ", record='" + record + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
