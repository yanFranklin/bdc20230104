package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/11/18
 * @description 不动产受理发票信息修改记录信息DO
 */
@Table(name = "BDC_SL_FPXX_XGJL")
@ApiModel(value = "BdcSlFpxxXgjlDO", description = "不动产受理发票信息修改记录信息DO")
public class BdcSlFpxxXgjlDO implements Serializable {

    @Id
    @ApiModelProperty(value = "修改记录ID")
    private String xgjlid;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "缴费人姓名")
    private String jkrxm;
    @ApiModelProperty(value = "修改时间")
    private Date xgsj;
    @ApiModelProperty(value = "修改前字段")
    private String xgqzd;
    @ApiModelProperty(value = "修改后字段")
    private String xghzd;
    @ApiModelProperty(value = "修改人")
    private String xgr;
    @ApiModelProperty(value = "项目ID")
    private String xmid;

    public String getXgjlid() {
        return xgjlid;
    }

    public void setXgjlid(String xgjlid) {
        this.xgjlid = xgjlid;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getJkrxm() {
        return jkrxm;
    }

    public void setJkrxm(String jkrxm) {
        this.jkrxm = jkrxm;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getXgqzd() {
        return xgqzd;
    }

    public void setXgqzd(String xgqzd) {
        this.xgqzd = xgqzd;
    }

    public String getXghzd() {
        return xghzd;
    }

    public void setXghzd(String xghzd) {
        this.xghzd = xghzd;
    }

    public String getXgr() {
        return xgr;
    }

    public void setXgr(String xgr) {
        this.xgr = xgr;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlFpxxXgjlDO.class.getSimpleName() + "[", "]")
                .add("xgjlid='" + xgjlid + "'")
                .add("fph='" + fph + "'")
                .add("slbh='" + slbh + "'")
                .add("zl='" + zl + "'")
                .add("jkrxm='" + jkrxm + "'")
                .add("xgsj=" + xgsj)
                .add("xgqzd='" + xgqzd + "'")
                .add("xghzd='" + xghzd + "'")
                .add("xgr='" + xgr + "'")
                .add("xmid='" + xmid + "'")
                .toString();
    }
}
