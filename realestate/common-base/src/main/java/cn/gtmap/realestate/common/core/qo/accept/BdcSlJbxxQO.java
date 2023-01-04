package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/01/06
 * @description 受理基本信息查询对象
 */
@ApiModel(value = "BdcSlJbxxQO", description = "受理基本信息查询QO对象")
public class BdcSlJbxxQO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "基本信息ID")
    private String jbxxid;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "受理人")
    private String slr;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    @ApiModelProperty(value = "登记机构")
    private String djjg;

    @ApiModelProperty(value = "开始时间")
    private String kssj;

    @ApiModelProperty(value = "结束时间")
    private String jssj;

    @ApiModelProperty(value = "推送税务返回的唯一标识")
    private String fwuuid;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getJbxxid() {
        return jbxxid;
    }

    public void setJbxxid(String jbxxid) {
        this.jbxxid = jbxxid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getFwuuid() {
        return fwuuid;
    }

    public void setFwuuid(String fwuuid) {
        this.fwuuid = fwuuid;
    }

    @Override
    public String toString() {
        return "BdcSlJbxxQO{" +
                "slbh='" + slbh + '\'' +
                ", jbxxid='" + jbxxid + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", slr='" + slr + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", djjg='" + djjg + '\'' +
                ", kssj='" + kssj + '\'' +
                ", jssj='" + jssj + '\'' +
                ", fwuuid='" + fwuuid + '\'' +
                '}';
    }
}
