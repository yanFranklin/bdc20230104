package cn.gtmap.realestate.exchange.core.dto.wwsq.sfssxx.response;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2020-01-02
 * @description
 */
public class BdcSfSsxxResponseDTO {

    private List<BdcSfSsxxResponseBdcSwxxDTOS> bdcSwxxDTOS;

    private String qlrlb;

    private String xmid;

    private List<BdcSfSsxxResponseBdcSfxxDTOS> bdcSfxxDTOS;

    private String slbh;

    private String qxdm;

    // 执收单位代码 根据QXDM做第三方对照获得
    private String zsdwdm;

    // 执收单位名称 根据QXDM做第三方对照获得
    private String zsdwmc;

    private String jfzt;

    private String jfztmc;

    //是否月结
    private String sfyj;

    private List<BdcSfSsxxResponseBdcSlSqrDOS> bdcSlSqrDOS;

    //维修基金信息
    private List<BdcSfSsxxResponseBdcWxjjxxDTOS> bdcWxjjxxDTOS;

    @ApiModelProperty("房屋UUID")
    private String fwuuid;

    @ApiModelProperty("缴款识别码")
    private String jksbm;

    public List<BdcSfSsxxResponseBdcSwxxDTOS> getBdcSwxxDTOS() {
        return bdcSwxxDTOS;
    }

    public void setBdcSwxxDTOS(List<BdcSfSsxxResponseBdcSwxxDTOS> bdcSwxxDTOS) {
        this.bdcSwxxDTOS = bdcSwxxDTOS;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public List<BdcSfSsxxResponseBdcSfxxDTOS> getBdcSfxxDTOS() {
        return bdcSfxxDTOS;
    }

    public void setBdcSfxxDTOS(List<BdcSfSsxxResponseBdcSfxxDTOS> bdcSfxxDTOS) {
        this.bdcSfxxDTOS = bdcSfxxDTOS;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public List<BdcSfSsxxResponseBdcSlSqrDOS> getBdcSlSqrDOS() {
        return bdcSlSqrDOS;
    }

    public void setBdcSlSqrDOS(List<BdcSfSsxxResponseBdcSlSqrDOS> bdcSlSqrDOS) {
        this.bdcSlSqrDOS = bdcSlSqrDOS;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getZsdwdm() {
        return zsdwdm;
    }

    public void setZsdwdm(String zsdwdm) {
        this.zsdwdm = zsdwdm;
    }

    public String getZsdwmc() {
        return zsdwmc;
    }

    public void setZsdwmc(String zsdwmc) {
        this.zsdwmc = zsdwmc;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getJfztmc() {
        return jfztmc;
    }

    public void setJfztmc(String jfztmc) {
        this.jfztmc = jfztmc;
    }

    public String getSfyj() {
        return sfyj;
    }

    public void setSfyj(String sfyj) {
        this.sfyj = sfyj;
    }

    public List<BdcSfSsxxResponseBdcWxjjxxDTOS> getBdcWxjjxxDTOS() {
        return bdcWxjjxxDTOS;
    }

    public void setBdcWxjjxxDTOS(List<BdcSfSsxxResponseBdcWxjjxxDTOS> bdcWxjjxxDTOS) {
        this.bdcWxjjxxDTOS = bdcWxjjxxDTOS;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    public String getJksbm() {
        return jksbm;
    }

    public void setJksbm(String jksbm) {
        this.jksbm = jksbm;
    }

    @Override
    public String toString() {
        return "BdcSfSsxxResponseDTO{" +
                "bdcSwxxDTOS=" + bdcSwxxDTOS +
                ", qlrlb='" + qlrlb + '\'' +
                ", xmid='" + xmid + '\'' +
                ", bdcSfxxDTOS=" + bdcSfxxDTOS +
                ", slbh='" + slbh + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", zsdwdm='" + zsdwdm + '\'' +
                ", zsdwmc='" + zsdwmc + '\'' +
                ", jfzt='" + jfzt + '\'' +
                ", jfztmc='" + jfztmc + '\'' +
                ", sfyj='" + sfyj + '\'' +
                ", bdcSlSqrDOS=" + bdcSlSqrDOS +
                ", bdcWxjjxxDTOS=" + bdcWxjjxxDTOS +
                ", fwuuid='" + fwuuid + '\'' +
                ", jksbm='" + jksbm + '\'' +
                '}';
    }
}
