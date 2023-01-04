package cn.gtmap.realestate.exchange.core.qo;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;

/**
 * 查封信息查询
 */
@IgnoreCast(ignoreNum = 5)
public class BdcCfxxQO {
    private String cfjg;
    private String cfwh;
    private String bcfr;
    private String bcfrmh;
    private String cqzh;
    private String cqzhmh;
    private String qszt;
    private String zlmh;
    private String zl;
    private String slbh;

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getBcfr() {
        return bcfr;
    }

    public void setBcfr(String bcfr) {
        this.bcfr = bcfr;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getZlmh() {
        return zlmh;
    }

    public void setZlmh(String zlmh) {
        this.zlmh = zlmh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBcfrmh() {
        return bcfrmh;
    }

    public void setBcfrmh(String bcfrmh) {
        this.bcfrmh = bcfrmh;
    }

    public String getCqzhmh() {
        return cqzhmh;
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }
}
