package cn.gtmap.realestate.common.core.dto.exchange.standard.esign.cjqslc;

import java.util.List;

public class EsignDataDTO {


    /**
     * hddz : http://IP:PORT/{projectName}/rest/v1.0/signFlows/callback
     * fqrzjh : 发起人证件号
     * slbh : 受理编号
     * lsh : 流水号,调用方唯一约束
     * sfyyrz : 0
     * lcmc : 存量房转移
     * yyrzfs : DXYZM
     * csdm : 厂商代码
     * fjxxList :
     * qsrxxList :
     */

    private String hddz;
    private String fqrzjh;
    private String slbh;
    private String lsh;
    private String sfyyrz;
    private String lcmc;
    private String yyrzfs;
    private String csdm;
    private List<EsignFjxxDTO> fjxxList;
    private List<EsignQsrxxDTO> qsrxxList;

    public String getHddz() {
        return hddz;
    }

    public void setHddz(String hddz) {
        this.hddz = hddz;
    }

    public String getFqrzjh() {
        return fqrzjh;
    }

    public void setFqrzjh(String fqrzjh) {
        this.fqrzjh = fqrzjh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh;
    }

    public String getSfyyrz() {
        return sfyyrz;
    }

    public void setSfyyrz(String sfyyrz) {
        this.sfyyrz = sfyyrz;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getYyrzfs() {
        return yyrzfs;
    }

    public void setYyrzfs(String yyrzfs) {
        this.yyrzfs = yyrzfs;
    }

    public String getCsdm() {
        return csdm;
    }

    public void setCsdm(String csdm) {
        this.csdm = csdm;
    }

    public List<EsignFjxxDTO> getFjxxList() {
        return fjxxList;
    }

    public void setFjxxList(List<EsignFjxxDTO> fjxxList) {
        this.fjxxList = fjxxList;
    }

    public List<EsignQsrxxDTO> getQsrxxList() {
        return qsrxxList;
    }

    public void setQsrxxList(List<EsignQsrxxDTO> qsrxxList) {
        this.qsrxxList = qsrxxList;
    }

    @Override
    public String toString() {
        return "EsignDataDTO{" +
                "hddz='" + hddz + '\'' +
                ", fqrzjh='" + fqrzjh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", lsh='" + lsh + '\'' +
                ", sfyyrz='" + sfyyrz + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", yyrzfs='" + yyrzfs + '\'' +
                ", csdm='" + csdm + '\'' +
                ", fjxxList=" + fjxxList +
                ", qsrxxList=" + qsrxxList +
                '}';
    }
}
