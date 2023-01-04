package cn.gtmap.realestate.common.core.dto.accept;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @program: 3.0
 * @description: 政融生成订单税务项目入参
 * @date 2022/8/23 19:57
 **/
public class ZrScddTaxDTO {

    @ApiModelProperty(value = "顺序")
    private String sn;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "区县代码")
    private String clAhrCd;
    @ApiModelProperty(value = "纳税人识别号")
    private String nsrsbh;
    @ApiModelProperty(value = "电子税票号")
    private String dzsph;
    @ApiModelProperty(value = "金额")
    private BigDecimal je;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getClAhrCd() {
        return clAhrCd;
    }

    public void setClAhrCd(String clAhrCd) {
        this.clAhrCd = clAhrCd;
    }

    public String getNsrsbh() {
        return nsrsbh;
    }

    public void setNsrsbh(String nsrsbh) {
        this.nsrsbh = nsrsbh;
    }

    public String getDzsph() {
        return dzsph;
    }

    public void setDzsph(String dzsph) {
        this.dzsph = dzsph;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    @Override
    public String toString() {
        return "ZrScddTaxDTO{" +
                "sn='" + sn + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", clAhrCd='" + clAhrCd + '\'' +
                ", nsrsbh='" + nsrsbh + '\'' +
                ", dzsph='" + dzsph + '\'' +
                ", je=" + je +
                '}';
    }
}
