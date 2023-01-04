package cn.gtmap.realestate.exchange.core.dto.wwsq.init.dydj;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/16
 * @description 收费信息
 */
public class InitRequestSfxx {


    /**
     * qlrmc :
     * qlrlb :
     * hj :
     * czjkm :
     * pjdz :
     * sfxmxx : [{}]
     */

    private String qlrmc;
    private String qlrlb;
    private Double hj;
    private String czjkm;
    private String pjdz;
    private List<InitRequestSfxmxx> sfxmxx;
    private String sfkp;
    private String jfzt;
    private String sfjzjf;
    private String jkzt;

    public String getJkzt() {
        return jkzt;
    }

    public void setJkzt(String jkzt) {
        this.jkzt = jkzt;
    }

    public String getSfkp() {
        return sfkp;
    }

    public void setSfkp(String sfkp) {
        this.sfkp = sfkp;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getSfjzjf() {
        return sfjzjf;
    }

    public void setSfjzjf(String sfjzjf) {
        this.sfjzjf = sfjzjf;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public String getCzjkm() {
        return czjkm;
    }

    public void setCzjkm(String czjkm) {
        this.czjkm = czjkm;
    }

    public String getPjdz() {
        return pjdz;
    }

    public void setPjdz(String pjdz) {
        this.pjdz = pjdz;
    }

    public List<InitRequestSfxmxx> getSfxmxx() {
        return sfxmxx;
    }

    public void setSfxmxx(List<InitRequestSfxmxx> sfxmxx) {
        this.sfxmxx = sfxmxx;
    }

}
