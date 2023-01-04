package cn.gtmap.realestate.common.core.dto.accept;

import java.util.StringJoiner;

/**
 * @program: realestate
 * @description: 未缴费数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-11-04 20:47
 **/
public class BdcSlWjfDTO {

    private String jfsbm;

    private String qlrmc;

    private String lxdh;

    private String slbh;

    private String jmyy;

    private String ajzt;

    private String slr;

    private String jkfs;

    private String sfxmmc;

    private String xmid;

    private String sfxxid;

    private String sfxmid;

    private String gbf;

    private String djf;

    private String tdqlr;

    private String tdywr;

    private String sfxmdm;

    private String sfzt;

    // 是否土地使用金 0 否  1 是
    private String sftdsyj;

    private String gzlslid;

    private String qxdm;

    public String getJfsbm() {
        return jfsbm;
    }

    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getJmyy() {
        return jmyy;
    }

    public void setJmyy(String jmyy) {
        this.jmyy = jmyy;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getJkfs() {
        return jkfs;
    }

    public void setJkfs(String jkfs) {
        this.jkfs = jkfs;
    }

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSfxmid() {
        return sfxmid;
    }

    public void setSfxmid(String sfxmid) {
        this.sfxmid = sfxmid;
    }

    public String getGbf() {
        return gbf;
    }

    public void setGbf(String gbf) {
        this.gbf = gbf;
    }

    public String getDjf() {
        return djf;
    }

    public void setDjf(String djf) {
        this.djf = djf;
    }

    public String getTdqlr() {
        return tdqlr;
    }

    public void setTdqlr(String tdqlr) {
        this.tdqlr = tdqlr;
    }

    public String getTdywr() {
        return tdywr;
    }

    public void setTdywr(String tdywr) {
        this.tdywr = tdywr;
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public String getSfzt() {
        return sfzt;
    }

    public void setSfzt(String sfzt) {
        this.sfzt = sfzt;
    }

    public String getSftdsyj() {
        return sftdsyj;
    }

    public void setSftdsyj(String sftdsyj) {
        this.sftdsyj = sftdsyj;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlWjfDTO{");
        sb.append("jfsbm='").append(jfsbm).append('\'');
        sb.append(", qlrmc='").append(qlrmc).append('\'');
        sb.append(", lxdh='").append(lxdh).append('\'');
        sb.append(", slbh='").append(slbh).append('\'');
        sb.append(", jmyy='").append(jmyy).append('\'');
        sb.append(", ajzt='").append(ajzt).append('\'');
        sb.append(", slr='").append(slr).append('\'');
        sb.append(", jkfs='").append(jkfs).append('\'');
        sb.append(", sfxmmc='").append(sfxmmc).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", sfxxid='").append(sfxxid).append('\'');
        sb.append(", sfxmid='").append(sfxmid).append('\'');
        sb.append(", gbf='").append(gbf).append('\'');
        sb.append(", djf='").append(djf).append('\'');
        sb.append(", tdqlr='").append(tdqlr).append('\'');
        sb.append(", tdywr='").append(tdywr).append('\'');
        sb.append(", sfxmdm='").append(sfxmdm).append('\'');
        sb.append(", sfzt='").append(sfzt).append('\'');
        sb.append(", sftdsyj='").append(sftdsyj).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", qxdm='").append(qxdm).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
