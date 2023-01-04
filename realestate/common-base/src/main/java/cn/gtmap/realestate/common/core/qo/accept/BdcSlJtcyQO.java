package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/1/7
 * @description
 */
@ApiModel(value = "BdcSlJtcyQO", description = "受理家庭成员查询QO对象")
public class BdcSlJtcyQO {

    @ApiModelProperty(value = "家庭成员名称")
    private String jtcymc;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "申请人类别")
    private String sqrlb;

    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    @ApiModelProperty(value = "与申请人关系")
    private String ysqrgx;

    public String getJtcymc() {
        return jtcymc;
    }

    public void setJtcymc(String jtcymc) {
        this.jtcymc = jtcymc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
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

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getYsqrgx() {
        return ysqrgx;
    }

    public void setYsqrgx(String ysqrgx) {
        this.ysqrgx = ysqrgx;
    }
}
