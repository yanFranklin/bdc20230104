package cn.gtmap.realestate.common.core.dto.exchange.sjpt.xgbmcx.swyxzm.request;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0  2020-11-02
 * @description
 */
public class SwyxzmCxywcsDTO {
    /**
     * 姓名
     */
    private String xm;

    /**
     * 身份证号
     */
    private String sfzh;

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }


    @Override
    public String toString() {
        return "SwyxzmCxywcsDTO{" +
                "xm='" + xm + '\'' +
                ", sfzh='" + sfzh + '\'' +
                '}';
    }
}
