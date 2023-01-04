package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/4/7
 * @description POS缴费MIS.OCX 接口返回信息
 */
@ApiModel(value = "PosJfDTO", description = "POS机缴费信息")
public class PosJfDTO {

    @ApiModelProperty(value = "订单编号")
    private String ddbh;

    @ApiModelProperty(value = "参考号")
    private String ckh;

    @ApiModelProperty(value = "商户代码")
    private String shdm;

    @ApiModelProperty(value = "终端号")
    private String zdh;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "交易凭证号")
    private String jypzh;

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh;
    }

    public String getShdm() {
        return shdm;
    }

    public void setShdm(String shdm) {
        this.shdm = shdm;
    }

    public String getZdh() {
        return zdh;
    }

    public void setZdh(String zdh) {
        this.zdh = zdh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

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

    public String getJypzh() {
        return jypzh;
    }

    public void setJypzh(String jypzh) {
        this.jypzh = jypzh;
    }

    @Override
    public String toString() {
        return "PosJfDTO{" +
                "ddbh='" + ddbh + '\'' +
                ", ckh='" + ckh + '\'' +
                ", shdm='" + shdm + '\'' +
                ", zdh='" + zdh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", jypzh='" + jypzh + '\'' +
                '}';
    }
}
