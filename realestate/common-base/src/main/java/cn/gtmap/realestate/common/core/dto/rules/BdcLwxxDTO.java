package cn.gtmap.realestate.common.core.dto.rules;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @version 2018/11/15,1.0
 * @description
 */
@ApiModel(value = "BdcLwxxDTO", description = "不动产例外信息")
public class BdcLwxxDTO implements Serializable {

    private static final long serialVersionUID = -1219407642420396863L;
    @ApiModelProperty(value = "提示信息")
    private String tsxx;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "查封文号")
    private String cfwh;

    @ApiModelProperty(value = "权利id")
    private String qlid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "强制验证模式,cf,sd,yy")
    private String qzyzms;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getTsxx() {
        return tsxx;
    }

    public void setTsxx(String tsxx) {
        this.tsxx = tsxx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getQzyzms() {
        return qzyzms;
    }

    public void setQzyzms(String qzyzms) {
        this.qzyzms = qzyzms;
    }

    @Override
    public String toString() {
        return "BdcLwxxDTO{" +
                "tsxx='" + tsxx + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", cfwh='" + cfwh + '\'' +
                ", qlid='" + qlid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qzyzms='" + qzyzms + '\'' +
                '}';
    }
}

