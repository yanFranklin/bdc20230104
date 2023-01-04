package cn.gtmap.realestate.exchange.core.dto.sjpt.xgbmcx.dmxxcx.request;

/**
 * @author <a href="mailto:shenzhanghao@gtmap.cn">shenzhanghao</a>
 * @version 1.0  2019-08-26
 * @description
 */
public class DmxxRequestCxywcs {
    /**
     * 6位区划代码
     */
    private String place_code;

    /**
     * 地名
     */
    private String standard_name;

    public String getPlace_code() {
        return place_code;
    }

    public void setPlace_code(String place_code) {
        this.place_code = place_code;
    }

    public String getStandard_name() {
        return standard_name;
    }

    public void setStandard_name(String standard_name) {
        this.standard_name = standard_name;
    }

    @Override
    public String toString() {
        return "DmxxRequestCxywcs{" +
                "place_code='" + place_code + '\'' +
                ", standard_name='" + standard_name + '\'' +
                '}';
    }
}
