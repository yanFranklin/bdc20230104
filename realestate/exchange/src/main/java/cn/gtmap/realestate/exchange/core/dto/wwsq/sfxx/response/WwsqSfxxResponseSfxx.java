package cn.gtmap.realestate.exchange.core.dto.wwsq.sfxx.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;

/**
 * @author <a herf="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/10/17
 * @description
 */
@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteNullNumberAsZero
})
public class WwsqSfxxResponseSfxx {
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sfsj;
    private String jedw;
    private String jedwmc;
    private String hj;
    private String bz;
    private String sfdcsr;
    private String sfdfsr;
    private String hsje;
    private String sfdwmc;
    private String jfrxm;
    private String sfrxm;
    private String sfrzh;
    private String sfrkhyh;
    private String sfdwdm;
    private String sfzt;
    private String sfztmc;
    private String sfztczrxm;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String sfztczsj;
    private String fph;
    private String cztxmbh;
    private String fff;
    private String qlrlb;
    private String qlrlbmc;
    private String sfrkhyhwddm;
    private String kpfs;
    private String kpfsmc;
    private String jkfs;
    private String jkfsmc;
    private String jfsbm;
    private String jfsewmurl;
    private List<WwsqSfxxResponseSfxm> sfxmList;

    public String getSfsj() {
        return sfsj;
    }

    public void setSfsj(String sfsj) {
        this.sfsj = sfsj;
    }

    public String getHj() {
        return hj;
    }

    public void setHj(String hj) {
        this.hj = hj;
    }

    public String getHsje() {
        return hsje;
    }

    public void setHsje(String hsje) {
        this.hsje = hsje;
    }

    public String getSfzt() {
        return sfzt;
    }

    public void setSfzt(String sfzt) {
        this.sfzt = sfzt;
    }

    public String getSfztczsj() {
        return sfztczsj;
    }

    public void setSfztczsj(String sfztczsj) {
        this.sfztczsj = sfztczsj;
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSfdcsr() {
        return sfdcsr;
    }

    public void setSfdcsr(String sfdcsr) {
        this.sfdcsr = sfdcsr;
    }

    public String getSfdfsr() {
        return sfdfsr;
    }

    public void setSfdfsr(String sfdfsr) {
        this.sfdfsr = sfdfsr;
    }

    public String getSfdwmc() {
        return sfdwmc;
    }

    public void setSfdwmc(String sfdwmc) {
        this.sfdwmc = sfdwmc;
    }

    public String getJfrxm() {
        return jfrxm;
    }

    public void setJfrxm(String jfrxm) {
        this.jfrxm = jfrxm;
    }

    public String getSfrxm() {
        return sfrxm;
    }

    public void setSfrxm(String sfrxm) {
        this.sfrxm = sfrxm;
    }

    public String getSfrzh() {
        return sfrzh;
    }

    public void setSfrzh(String sfrzh) {
        this.sfrzh = sfrzh;
    }

    public String getSfrkhyh() {
        return sfrkhyh;
    }

    public void setSfrkhyh(String sfrkhyh) {
        this.sfrkhyh = sfrkhyh;
    }

    public String getSfdwdm() {
        return sfdwdm;
    }

    public void setSfdwdm(String sfdwdm) {
        this.sfdwdm = sfdwdm;
    }

    public String getSfztmc() {
        return sfztmc;
    }

    public void setSfztmc(String sfztmc) {
        this.sfztmc = sfztmc;
    }

    public String getSfztczrxm() {
        return sfztczrxm;
    }

    public void setSfztczrxm(String sfztczrxm) {
        this.sfztczrxm = sfztczrxm;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public String getCztxmbh() {
        return cztxmbh;
    }

    public void setCztxmbh(String cztxmbh) {
        this.cztxmbh = cztxmbh;
    }

    public String getFff() {
        return fff;
    }

    public void setFff(String fff) {
        this.fff = fff;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrlbmc() {
        return qlrlbmc;
    }

    public void setQlrlbmc(String qlrlbmc) {
        this.qlrlbmc = qlrlbmc;
    }

    public String getSfrkhyhwddm() {
        return sfrkhyhwddm;
    }

    public void setSfrkhyhwddm(String sfrkhyhwddm) {
        this.sfrkhyhwddm = sfrkhyhwddm;
    }

    public String getKpfs() {
        return kpfs;
    }

    public void setKpfs(String kpfs) {
        this.kpfs = kpfs;
    }

    public String getKpfsmc() {
        return kpfsmc;
    }

    public void setKpfsmc(String kpfsmc) {
        this.kpfsmc = kpfsmc;
    }

    public String getJkfs() {
        return jkfs;
    }

    public void setJkfs(String jkfs) {
        this.jkfs = jkfs;
    }

    public String getJkfsmc() {
        return jkfsmc;
    }

    public void setJkfsmc(String jkfsmc) {
        this.jkfsmc = jkfsmc;
    }

    public String getJfsbm() {
        return jfsbm;
    }

    public void setJfsbm(String jfsbm) {
        this.jfsbm = jfsbm;
    }

    public String getJfsewmurl() {
        return jfsewmurl;
    }

    public void setJfsewmurl(String jfsewmurl) {
        this.jfsewmurl = jfsewmurl;
    }

    public List<WwsqSfxxResponseSfxm> getSfxmList() {
        return sfxmList;
    }

    public void setSfxmList(List<WwsqSfxxResponseSfxm> sfxmList) {
        this.sfxmList = sfxmList;
    }
}
