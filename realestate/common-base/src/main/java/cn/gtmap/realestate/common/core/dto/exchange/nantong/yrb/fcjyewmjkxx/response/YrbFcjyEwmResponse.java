package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.12.    房产交易缴款二维码获取【A018
 */
public class YrbFcjyEwmResponse {


    /**
     * dzsphm : 电子税票号
     * kkje : 扣款金额
     * ewm : 二维码
     */

    private String dzsphm;
    private Double kkje;
    private String ewm;

    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }

    public Double getKkje() {
        return kkje;
    }

    public void setKkje(Double kkje) {
        this.kkje = kkje;
    }

    public String getEwm() {
        return ewm;
    }

    public void setEwm(String ewm) {
        this.ewm = ewm;
    }
}
