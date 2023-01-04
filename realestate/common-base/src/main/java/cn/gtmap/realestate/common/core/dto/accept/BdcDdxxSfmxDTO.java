package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/11/3
 * @description 订单缴费基础信息DTO
 */
public class BdcDdxxSfmxDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "执收单位名称")
    private String zsdwmc;
    @ApiModelProperty(value = "执收单位代码")
    private String zsdwdm;
    @ApiModelProperty(value = "申请人列表")
    private List<BdcSlSqrDO> bdcSlSqrDOS;
    @ApiModelProperty(value = "收费信息集合")
    private List<BdcYczfSfxxDTO> bdcSfxxDTOS;
    @ApiModelProperty(value = "收税信息集合")
    private List<BdcSwxxDTO> bdcSwxxDTOS;
    @ApiModelProperty(value = "维修基金信息集合")
    private List<BdcWxjjxxDTO> bdcWxjjxxDTOS;
    @ApiModelProperty(value = "缴费状态")
    private String jfzt;
    @ApiModelProperty(value = "缴费状态名称")
    private String jfztmc;
    @ApiModelProperty(value = "是否月结")
    private String sfyj;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getZsdwmc() {
        return zsdwmc;
    }

    public void setZsdwmc(String zsdwmc) {
        this.zsdwmc = zsdwmc;
    }

    public String getZsdwdm() {
        return zsdwdm;
    }

    public void setZsdwdm(String zsdwdm) {
        this.zsdwdm = zsdwdm;
    }

    public List<BdcSlSqrDO> getBdcSlSqrDOS() {
        return bdcSlSqrDOS;
    }

    public void setBdcSlSqrDOS(List<BdcSlSqrDO> bdcSlSqrDOS) {
        this.bdcSlSqrDOS = bdcSlSqrDOS;
    }

    public List<BdcYczfSfxxDTO> getBdcSfxxDTOS() {
        return bdcSfxxDTOS;
    }

    public void setBdcSfxxDTOS(List<BdcYczfSfxxDTO> bdcSfxxDTOS) {
        this.bdcSfxxDTOS = bdcSfxxDTOS;
    }

    public List<BdcSwxxDTO> getBdcSwxxDTOS() {
        return bdcSwxxDTOS;
    }

    public void setBdcSwxxDTOS(List<BdcSwxxDTO> bdcSwxxDTOS) {
        this.bdcSwxxDTOS = bdcSwxxDTOS;
    }

    public List<BdcWxjjxxDTO> getBdcWxjjxxDTOS() {
        return bdcWxjjxxDTOS;
    }

    public void setBdcWxjjxxDTOS(List<BdcWxjjxxDTO> bdcWxjjxxDTOS) {
        this.bdcWxjjxxDTOS = bdcWxjjxxDTOS;
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
}
