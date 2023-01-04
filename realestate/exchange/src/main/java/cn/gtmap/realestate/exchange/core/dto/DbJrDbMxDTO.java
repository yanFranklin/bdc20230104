package cn.gtmap.realestate.exchange.core.dto;

/**
 * @program: realestate
 * @description: 登簿接入对比明细
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-28 09:57
 **/
public class DbJrDbMxDTO {

    private String xh;

    private String slbh;

    private String bdcdyh;

    private String zl;

    private String bdcqzh;

    private String lcmc;

    private String tsyy;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getTsyy() {
        return tsyy;
    }

    public void setTsyy(String tsyy) {
        this.tsyy = tsyy;
    }

    @Override
    public String toString() {
        return "DbJrDbMxDTO{" +
                "xh='" + xh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", tsyy='" + tsyy + '\'' +
                '}';
    }
}
