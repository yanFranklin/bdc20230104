package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/12/13
 * @description 税务三要素信息
 */
@Table(name = "BDC_SL_SYSXX")
@ApiModel(value = "BdcSlSysxxDO", description = "税务三要素信息")
public class BdcSlSysxxDO {

    @Id
    @ApiModelProperty(value = "三要素ID")
    private String sysid;

    @ApiModelProperty(value = "税务机关代码")
    private String swjgdm;

    @ApiModelProperty(value = "纳税人识别号")
    private String nsrsbh;

    @ApiModelProperty(value = "税票号码")
    private String dzsphm;

    @ApiModelProperty(value = "银行缴库入库状态")
    private Integer yhjkrkzt;

    @ApiModelProperty(value = "应缴税额")
    private Double yjse;

    @ApiModelProperty(value = "核税信息ID")
    private String hsxxid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "申请人类别")
    private String sqrlb;

    @ApiModelProperty(value = "缴库入库时间")
    private Date jkrksj;

    public String getSysid() {
        return sysid;
    }

    public void setSysid(String sysid) {
        this.sysid = sysid;
    }

    public String getSwjgdm() {
        return swjgdm;
    }

    public void setSwjgdm(String swjgdm) {
        this.swjgdm = swjgdm;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getDzsphm() {
        return dzsphm;
    }

    public void setDzsphm(String dzsphm) {
        this.dzsphm = dzsphm;
    }

    public Integer getYhjkrkzt() {
        return yhjkrkzt;
    }

    public void setYhjkrkzt(Integer yhjkrkzt) {
        this.yhjkrkzt = yhjkrkzt;
    }

    public Double getYjse() {
        return yjse;
    }

    public void setYjse(Double yjse) {
        this.yjse = yjse;
    }

    public String getHsxxid() {
        return hsxxid;
    }

    public void setHsxxid(String hsxxid) {
        this.hsxxid = hsxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrlb() {
        return sqrlb;
    }

    public void setSqrlb(String sqrlb) {
        this.sqrlb = sqrlb;
    }

    public Date getJkrksj() {
        return jkrksj;
    }

    public void setJkrksj(Date jkrksj) {
        this.jkrksj = jkrksj;
    }
}
