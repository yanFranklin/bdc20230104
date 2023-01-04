package cn.gtmap.realestate.exchange.core.dto.wwsq.tdsyqcx.response;

import cn.gtmap.realestate.exchange.core.dto.wwsq.QlrlistResponse;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/6/29
 * @description 土地所有权查询查询DTO
 */
public class TdsyqDTO {


    /**
     * cqzh : 房权证合产字第8110002040号
     * bdcdyh : 340104405004GB00095F00010078
     * zl : 政务区习友路1973号恒大华府18幢2507
     * nydmj : 78.5
     * sfdy : 1
     * sfyy : 1
     * sfsd : 1
     * sfyg : 1
     * jsydmj :
     * sfcf : 1
     * xmid :
     * wlydmj :
     * gdmj :
     * ldmj :
     * zdzhmj :
     * zdyt :
     * zdytmc :
     * zdqlxz :
     * zdqlxzmc :
     * cdmj :
     * qtmj :
     * fj :
     * bdclx :
     * bdclxmc :
     */

    private String cqzh;
    private String bdcdyh;
    private String zl;
    private String nydmj;
    private String sfdy;
    private String sfyy;
    private String sfsd;
    private String sfyg;
    private String jsydmj;
    private String sfcf;
    private String xmid;
    private String wlydmj;
    private String gdmj;
    private String ldmj;
    private String zdzhmj;
    private String zdyt;
    private String zdytmc;
    private String zdqlxz;
    private String zdqlxzmc;
    private String cdmj;
    private String qtmj;
    private String fj;
    private String bdclx;
    private String bdclxmc;
    private List<QlrlistResponse> qlrlist;


    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
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

    public String getNydmj() {
        return nydmj;
    }

    public void setNydmj(String nydmj) {
        this.nydmj = nydmj;
    }

    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    public String getSfyy() {
        return sfyy;
    }

    public void setSfyy(String sfyy) {
        this.sfyy = sfyy;
    }

    public String getSfsd() {
        return sfsd;
    }

    public void setSfsd(String sfsd) {
        this.sfsd = sfsd;
    }

    public String getSfyg() {
        return sfyg;
    }

    public void setSfyg(String sfyg) {
        this.sfyg = sfyg;
    }

    public String getJsydmj() {
        return jsydmj;
    }

    public void setJsydmj(String jsydmj) {
        this.jsydmj = jsydmj;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getWlydmj() {
        return wlydmj;
    }

    public void setWlydmj(String wlydmj) {
        this.wlydmj = wlydmj;
    }

    public String getGdmj() {
        return gdmj;
    }

    public void setGdmj(String gdmj) {
        this.gdmj = gdmj;
    }

    public String getLdmj() {
        return ldmj;
    }

    public void setLdmj(String ldmj) {
        this.ldmj = ldmj;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getZdyt() {
        return zdyt;
    }

    public void setZdyt(String zdyt) {
        this.zdyt = zdyt;
    }

    public String getZdytmc() {
        return zdytmc;
    }

    public void setZdytmc(String zdytmc) {
        this.zdytmc = zdytmc;
    }

    public String getZdqlxz() {
        return zdqlxz;
    }

    public void setZdqlxz(String zdqlxz) {
        this.zdqlxz = zdqlxz;
    }

    public String getZdqlxzmc() {
        return zdqlxzmc;
    }

    public void setZdqlxzmc(String zdqlxzmc) {
        this.zdqlxzmc = zdqlxzmc;
    }

    public String getCdmj() {
        return cdmj;
    }

    public void setCdmj(String cdmj) {
        this.cdmj = cdmj;
    }

    public String getQtmj() {
        return qtmj;
    }

    public void setQtmj(String qtmj) {
        this.qtmj = qtmj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdclxmc() {
        return bdclxmc;
    }

    public void setBdclxmc(String bdclxmc) {
        this.bdclxmc = bdclxmc;
    }

    public List<QlrlistResponse> getQlrlist() {
        return qlrlist;
    }

    public void setQlrlist(List<QlrlistResponse> qlrlist) {
        this.qlrlist = qlrlist;
    }

    @Override
    public String toString() {
        return "TdsyqDTO{" +
                "cqzh='" + cqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", nydmj='" + nydmj + '\'' +
                ", sfdy='" + sfdy + '\'' +
                ", sfyy='" + sfyy + '\'' +
                ", sfsd='" + sfsd + '\'' +
                ", sfyg='" + sfyg + '\'' +
                ", jsydmj='" + jsydmj + '\'' +
                ", sfcf='" + sfcf + '\'' +
                ", xmid='" + xmid + '\'' +
                ", wlydmj='" + wlydmj + '\'' +
                ", gdmj='" + gdmj + '\'' +
                ", ldmj='" + ldmj + '\'' +
                ", zdzhmj='" + zdzhmj + '\'' +
                ", zdyt='" + zdyt + '\'' +
                ", zdytmc='" + zdytmc + '\'' +
                ", zdqlxz='" + zdqlxz + '\'' +
                ", zdqlxzmc='" + zdqlxzmc + '\'' +
                ", cdmj='" + cdmj + '\'' +
                ", qtmj='" + qtmj + '\'' +
                ", fj='" + fj + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", bdclxmc='" + bdclxmc + '\'' +
                ", qlrlist=" + qlrlist +
                '}';
    }
}
