package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjyewmjkxx.response;

import cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.YrbFhmResponse;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.12.    房产交易缴款二维码获取【A018
 */
public class YrbFcjyEwmDTO {

    private YrbFhmResponse fcjyjkermjg;

    private YrbFcjyEwmResponse kkewm;

    public YrbFhmResponse getFcjyjkermjg() {
        return fcjyjkermjg;
    }

    public void setFcjyjkermjg(YrbFhmResponse fcjyjkermjg) {
        this.fcjyjkermjg = fcjyjkermjg;
    }

    public YrbFcjyEwmResponse getKkewm() {
        return kkewm;
    }

    public void setKkewm(YrbFcjyEwmResponse kkewm) {
        this.kkewm = kkewm;
    }

    @Override
    public String toString() {
        return "YrbFcjyEwmDTO{" +
                "fcjyjkermjg=" + fcjyjkermjg +
                ", kkewm=" + kkewm +
                '}';
    }
}
