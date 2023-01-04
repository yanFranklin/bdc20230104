package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.dmxxcx.request;

public class DmxxcxCxywcsRequestDTO {

    /**
     * 6位区划代码
     */
    private String code;

    /**
     * 地名
     */
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "DmxxcxCxywcsRequestDTO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
