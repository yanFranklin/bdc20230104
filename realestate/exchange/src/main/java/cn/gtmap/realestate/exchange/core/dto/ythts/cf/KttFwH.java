package cn.gtmap.realestate.exchange.core.dto.ythts.cf;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wyh
 */
public class KttFwH {
    @JSONField(name = "BDCDYH")
    private String BDCDYH;
    @JSONField(name = "ZL")
    private String ZL;
    @JSONField(name = "YWH")
    private String YWH;

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setZL(String ZL) {
        this.ZL = ZL;
    }

    public String getZL() {
        return ZL;
    }

    public void setYWH(String YWH) {
        this.YWH = YWH;
    }

    public String getYWH() {
        return YWH;
    }

}