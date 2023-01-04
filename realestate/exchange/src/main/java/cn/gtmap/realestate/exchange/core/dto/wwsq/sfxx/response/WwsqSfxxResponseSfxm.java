package cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/17
 * @description
 */
@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullNumberAsZero
})
public class WwsqSfxxResponseSfxm {
    private String sfxmmc;
    private String sfxmdm;
    private String xh;
    private String sl;
    private String jmje;
    private String ysje;
    private String ssje;
    private String sfbl;
    private String sfxmbz;
    private String jedw;
    private String jedwmc;
    private String jsff;
    private String jffs;
    private String jffsmc;
    private String jfzd;
    private String sflx;
    private String sflxmc;
    private String qlrlb;
    private String qlrlbmc;

    public String getSfxmmc() {
        return sfxmmc;
    }

    public void setSfxmmc(String sfxmmc) {
        this.sfxmmc = sfxmmc;
    }

    public String getSfxmdm() {
        return sfxmdm;
    }

    public void setSfxmdm(String sfxmdm) {
        this.sfxmdm = sfxmdm;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getSl() {
        return sl;
    }

    public void setSl(String sl) {
        this.sl = sl;
    }

    public String getJmje() {
        return jmje;
    }

    public void setJmje(String jmje) {
        this.jmje = jmje;
    }

    public String getYsje() {
        return ysje;
    }

    public void setYsje(String ysje) {
        this.ysje = ysje;
    }

    public String getSsje() {
        return ssje;
    }

    public void setSsje(String ssje) {
        this.ssje = ssje;
    }

    public String getSfbl() {
        return sfbl;
    }

    public void setSfbl(String sfbl) {
        this.sfbl = sfbl;
    }

    public String getSfxmbz() {
        return sfxmbz;
    }

    public void setSfxmbz(String sfxmbz) {
        this.sfxmbz = sfxmbz;
    }

    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public String getJedwmc() {
        return jedwmc;
    }

    public void setJedwmc(String jedwmc) {
        this.jedwmc = jedwmc;
    }

    public String getJsff() {
        return jsff;
    }

    public void setJsff(String jsff) {
        this.jsff = jsff;
    }

    public String getJffs() {
        return jffs;
    }

    public void setJffs(String jffs) {
        this.jffs = jffs;
    }

    public String getJfzd() {
        return jfzd;
    }

    public void setJfzd(String jfzd) {
        this.jfzd = jfzd;
    }

    public String getSflx() {
        return sflx;
    }

    public void setSflx(String sflx) {
        this.sflx = sflx;
    }


    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getSflxmc() {
        return sflxmc;
    }

    public void setSflxmc(String sflxmc) {
        this.sflxmc = sflxmc;
    }

    public String getQlrlbmc() {
        return qlrlbmc;
    }

    public void setQlrlbmc(String qlrlbmc) {
        this.qlrlbmc = qlrlbmc;
    }

    public String getJffsmc() {
        return jffsmc;
    }

    public void setJffsmc(String jffsmc) {
        this.jffsmc = jffsmc;
    }
}
