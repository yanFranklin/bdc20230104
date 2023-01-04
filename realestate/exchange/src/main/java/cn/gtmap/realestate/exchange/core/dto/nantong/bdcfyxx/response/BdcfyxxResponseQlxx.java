package cn.gtmap.realestate.exchange.core.dto.nantong.bdcfyxx.response;

import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-09-18
 * @description
 */
//@JSONType(serialzeFeatures= SerializerFeature.WriteNullStringAsEmpty)
public class BdcfyxxResponseQlxx {

    private String qxdm;

    private String dzwmj;

    private String dzwyt;

    private String zddm;

    private String qllx;

    private String bdcdyh;

    private String bz;

    private String zdzhyt;

    private String tdsyqssj;

    private String zdzhmj;

    private String szc;

    private String jzmj;

    private String fwxz;

    private String cg;

    private String fjh;

    private String jgsj;

    private String zyjzmj;

    private String bdcdywybh;

    private String qlxz;

    private String xmid;

    private String zdzhqlxz;

    private String zcs;

    private String fwlx;

    private String zh;

    private String zl;

    private String bdcqzh;

    private String tdsyjssj;

    private String szmyc;

    private String bdclx;

    private String fwjg;

    private String slbh;

    private String sfzb;

    // 土地使用权面积
    private String tdsyqmj;

    // 分摊土地面积
    private String fttdmj;

    // 独用土地面积
    private String dytdmj;

    //附记
    private String fj;

    private List<BdcfyxxResponseQlr> qlr;

    //查封信息
    private List<BdcfyxxResponseCfxx> cfxx;

    //抵押信息
    private List<BdcfyxxResponseDyxx> dyxx;

    //异议信息
    private List<BdcfyxxResponseYyxx> yyxx;

    //锁定信息
    private List<BdcfyxxResponseSdxx> sdxx;

    //居住权信息
    private List<BdcfyxxResponseJzqxx> jzqxx;

    public String getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(String dzwmj) {
        this.dzwmj = dzwmj;
    }

    public String getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(String dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public List<BdcfyxxResponseCfxx> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<BdcfyxxResponseCfxx> cfxx) {
        this.cfxx = cfxx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public List<BdcfyxxResponseYyxx> getYyxx() {
        return yyxx;
    }

    public void setYyxx(List<BdcfyxxResponseYyxx> yyxx) {
        this.yyxx = yyxx;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public String getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(String tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public String getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(String zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getCg() {
        return cg;
    }

    public void setCg(String cg) {
        this.cg = cg;
    }

    public List<BdcfyxxResponseSdxx> getSdxx() {
        return sdxx;
    }

    public void setSdxx(List<BdcfyxxResponseSdxx> sdxx) {
        this.sdxx = sdxx;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public String getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(String zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getZdzhqlxz() {
        return zdzhqlxz;
    }

    public void setZdzhqlxz(String zdzhqlxz) {
        this.zdzhqlxz = zdzhqlxz;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
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

    public List<BdcfyxxResponseQlr> getQlr() {
        return qlr;
    }

    public void setQlr(List<BdcfyxxResponseQlr> qlr) {
        this.qlr = qlr;
    }

    public String getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(String tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public List<BdcfyxxResponseDyxx> getDyxx() {
        return dyxx;
    }

    public void setDyxx(List<BdcfyxxResponseDyxx> dyxx) {
        this.dyxx = dyxx;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSfzb() {
        return sfzb;
    }

    public void setSfzb(String sfzb) {
        this.sfzb = sfzb;
    }

    public List<BdcfyxxResponseJzqxx> getJzqxx() {
        return jzqxx;
    }

    public void setJzqxx(List<BdcfyxxResponseJzqxx> jzqxx) {
        this.jzqxx = jzqxx;
    }

    public String getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(String tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public String getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(String fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(String dytdmj) {
        this.dytdmj = dytdmj;
    }

    public String getFj() { return fj; }
    public void setFj(String fj) { this.fj = fj; }
}
