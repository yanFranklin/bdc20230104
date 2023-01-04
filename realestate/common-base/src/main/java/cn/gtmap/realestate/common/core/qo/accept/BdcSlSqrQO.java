package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/31.
 * @description 受理申请人查询QO
 */
@ApiModel(value = "BdcSlSqrQO", description = "受理申请人查询QO")
public class BdcSlSqrQO {

    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "申请人名称")
    private String sqrmc;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "申请人类别(1-权利人；2-义务人)")
    private String sqrlb;

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getSqrlb() {
        return sqrlb;
    }

    public void setSqrlb(String sqrlb) {
        this.sqrlb = sqrlb;
    }

    @Override
    public String toString() {
        return "BdcSlSqrQO{" +
                "sqrid='" + sqrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sqrmc='" + sqrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                ", sqrlb='" + sqrlb + '\'' +
                '}';
    }
}
