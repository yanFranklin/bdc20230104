package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-12-25
 * @description 不动产收费收税信息 DTO
 */
public class BdcSfSsxxDTO {

    private String xmid;

    private String slbh;

    private String qlrlb;

    private String qxdm;

    // 查询标志  1为只查询收费，2为只查询收税，0或空为查询全部
    private String cxbz;

    // 共有申请人列表
    private List<BdcSlSqrDO> bdcSlSqrDOS;

    // 收费信息集合
    private List<BdcSfxxDTO> bdcSfxxDTOS;

    // 收税信息集合
    private List<BdcSwxxDTO> bdcSwxxDTOS;

    // 维修基金集合
    private List<BdcWxjjxxDTO> bdcWxjjxxDTOS;

    // 缴费状态 0 未缴费  1已缴费
    private String jfzt;

    // 是否月结
    private Integer sfyj;

    @ApiModelProperty("房屋UUID")
    private String fwuuid;

    @ApiModelProperty("缴款识别码")
    private String jksbm;

    @ApiModelProperty("缴费类型：DK：德宽，空：银行")
    private String jfType;

    public List<BdcSlSqrDO> getBdcSlSqrDOS() {
        return bdcSlSqrDOS;
    }

    public void setBdcSlSqrDOS(List<BdcSlSqrDO> bdcSlSqrDOS) {
        this.bdcSlSqrDOS = bdcSlSqrDOS;
    }

    public List<BdcSfxxDTO> getBdcSfxxDTOS() {
        return bdcSfxxDTOS;
    }

    public void setBdcSfxxDTOS(List<BdcSfxxDTO> bdcSfxxDTOS) {
        this.bdcSfxxDTOS = bdcSfxxDTOS;
    }

    public List<BdcSwxxDTO> getBdcSwxxDTOS() {
        return bdcSwxxDTOS;
    }

    public void setBdcSwxxDTOS(List<BdcSwxxDTO> bdcSwxxDTOS) {
        this.bdcSwxxDTOS = bdcSwxxDTOS;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
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

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getCxbz() {
        return cxbz;
    }

    public void setCxbz(String cxbz) {
        this.cxbz = cxbz;
    }

    public Integer getSfyj() {
        return sfyj;
    }

    public void setSfyj(Integer sfyj) {
        this.sfyj = sfyj;
    }

    public List<BdcWxjjxxDTO> getBdcWxjjxxDTOS() {
        return bdcWxjjxxDTOS;
    }

    public void setBdcWxjjxxDTOS(List<BdcWxjjxxDTO> bdcWxjjxxDTOS) {
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

    public String getJfType() {
        return jfType;
    }

    public void setJfType(String jfType) {
        this.jfType = jfType;
    }

    @Override
    public String toString() {
        return "BdcSfSsxxDTO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", cxbz='" + cxbz + '\'' +
                ", bdcSlSqrDOS=" + bdcSlSqrDOS +
                ", bdcSfxxDTOS=" + bdcSfxxDTOS +
                ", bdcSwxxDTOS=" + bdcSwxxDTOS +
                ", bdcWxjjxxDTOS=" + bdcWxjjxxDTOS +
                ", jfzt='" + jfzt + '\'' +
                ", sfyj=" + sfyj +
                ", fwuuid='" + fwuuid + '\'' +
                ", jksbm='" + jksbm + '\'' +
                ", jfType='" + jfType + '\'' +
                '}';
    }
}
