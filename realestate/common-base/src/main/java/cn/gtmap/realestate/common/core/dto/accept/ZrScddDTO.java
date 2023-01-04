package cn.gtmap.realestate.common.core.dto.accept;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @program: 3.0
 * @description: 政融生成订单入参
 * @date 2022/8/23 19:57
 **/
public class ZrScddDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "发起时间")
    private String fqsj;
    @ApiModelProperty(value = "发起类型")
    private String fqly;
    @ApiModelProperty(value = "操作员号")
    private String czyh;
    @ApiModelProperty(value = "总金额")
    private BigDecimal zje;
    @ApiModelProperty(value = "客户订单号，生成规则：xmid+qlrlb")
    private String khddh;
    @ApiModelProperty(value = "非税缴费信息")
    private List<ZrScddYbDTO> ybfyList;
    @ApiModelProperty(value = "税务缴费信息")
    private List<ZrScddTaxDTO> taxList;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getFqsj() {
        return fqsj;
    }

    public void setFqsj(String fqsj) {
        this.fqsj = fqsj;
    }

    public String getFqly() {
        return fqly;
    }

    public void setFqly(String fqly) {
        this.fqly = fqly;
    }

    public String getCzyh() {
        return czyh;
    }

    public void setCzyh(String czyh) {
        this.czyh = czyh;
    }

    public String getKhddh() {
        return khddh;
    }

    public void setKhddh(String khddh) {
        this.khddh = khddh;
    }

    public BigDecimal getZje() {
        return zje;
    }

    public void setZje(BigDecimal zje) {
        this.zje = zje;
    }

    public List<ZrScddYbDTO> getYbfyList() {
        return ybfyList;
    }

    public void setYbfyList(List<ZrScddYbDTO> ybfyList) {
        this.ybfyList = ybfyList;
    }

    public List<ZrScddTaxDTO> getTaxList() {
        return taxList;
    }

    public void setTaxList(List<ZrScddTaxDTO> taxList) {
        this.taxList = taxList;
    }

    @Override
    public String toString() {
        return "ZrScddDTO{" +
                "slbh='" + slbh + '\'' +
                ", fqsj=" + fqsj +
                ", fqly='" + fqly + '\'' +
                ", czyh='" + czyh + '\'' +
                ", zje=" + zje +
                ", khddh='" + khddh + '\'' +
                ", ybfyList=" + ybfyList +
                ", taxList=" + taxList +
                '}';
    }
}
