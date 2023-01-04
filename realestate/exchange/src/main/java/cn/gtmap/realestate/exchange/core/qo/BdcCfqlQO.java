package cn.gtmap.realestate.exchange.core.qo;

import cn.gtmap.realestate.exchange.core.bo.anno.IgnoreCast;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 查封权利信息查询
 */
@IgnoreCast(ignoreNum = 3)
public class BdcCfqlQO {
    private String qlrmc;
    private String qlrmcmh;
    private String cqzh;
    private String cqzhmh;
    private String zl;
    private String bdcdyh;
    private String qszt;
    private String zlmh;
    private String qlrzjh;
    private String qlrzjhmh;
    private String cqzhjc;
    private String bdclx;
    private String xmid;
    private String fwbm;
    private List<String> zjhs;

    @ApiModelProperty(value = "权利类型")
    private List<String> qllx;

    /**
     * 是否只过滤查封对应权利：0或空：否 ，1：是
     */
    private String sfcf;

    public String getCqzhmh() {
        return cqzhmh;
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    public List<String> getQllx() {
        return qllx;
    }

    public void setQllx(List<String> qllx) {
        this.qllx = qllx;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public List<String> getZjhs() {
        return zjhs;
    }

    public void setZjhs(List<String> zjhs) {
        this.zjhs = zjhs;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getCqzhjc() {
        return cqzhjc;
    }

    public void setCqzhjc(String cqzhjc) {
        this.cqzhjc = cqzhjc;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public String getQlrmcmh() {
        return qlrmcmh;
    }

    public void setQlrmcmh(String qlrmcmh) {
        this.qlrmcmh = qlrmcmh;
    }

    public String getQlrzjhmh() {
        return qlrzjhmh;
    }

    public void setQlrzjhmh(String qlrzjhmh) {
        this.qlrzjhmh = qlrzjhmh;
    }

    @Override
    public String toString() {
        return "BdcCfqlQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrmcmh='" + qlrmcmh + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qszt='" + qszt + '\'' +
                ", zlmh='" + zlmh + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", qlrzjhmh='" + qlrzjhmh + '\'' +
                ", cqzhjc='" + cqzhjc + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", xmid='" + xmid + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", zjhs=" + zjhs +
                ", qllx=" + qllx +
                ", sfcf='" + sfcf + '\'' +
                '}';
    }
}
