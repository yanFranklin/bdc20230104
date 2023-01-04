package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.StringJoiner;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/28
 * @description 不动产收费收税订单信息查询QO对象
 */
@ApiModel(value = "BdcSlSfssDdxxQO", description = "不动产收费收税订单信息查询QO对象")
public class BdcSlSfssDdxxQO {

    @ApiModelProperty(value = "税费关联ID")
    private String sfglid;

    @ApiModelProperty(value = "税费关联ID类型")
    private String sfglidlx;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value="月结单号")
    private String yjdh;

    @ApiModelProperty(value = "订单编号")
    private String ddbh;

    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;

    @ApiModelProperty(value = "第三方订单编号")
    private String dsfddbh;

    @ApiModelProperty(value = "支付状态")
    private Integer jfzt;

    @ApiModelProperty(value = "银行缴库入库状态")
    private Integer yhjkrkzt;

    @ApiModelProperty(value = "订单类型")
    private Integer ddlx;

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getSfglid() {
        return sfglid;
    }

    public void setSfglid(String sfglid) {
        this.sfglid = sfglid;
    }

    public String getSfglidlx() {
        return sfglidlx;
    }

    public void setSfglidlx(String sfglidlx) {
        this.sfglidlx = sfglidlx;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getDsfddbh() {
        return dsfddbh;
    }

    public void setDsfddbh(String dsfddbh) {
        this.dsfddbh = dsfddbh;
    }

    public String getYjdh() {
        return yjdh;
    }

    public void setYjdh(String yjdh) {
        this.yjdh = yjdh;
    }

    public Integer getJfzt() {
        return jfzt;
    }

    public void setJfzt(Integer jfzt) {
        this.jfzt = jfzt;
    }

    public Integer getYhjkrkzt() {
        return yhjkrkzt;
    }

    public void setYhjkrkzt(Integer yhjkrkzt) {
        this.yhjkrkzt = yhjkrkzt;
    }

    public Integer getDdlx() {
        return ddlx;
    }

    public void setDdlx(Integer ddlx) {
        this.ddlx = ddlx;
    }

    @Override
    public String toString() {
        return "BdcSlSfssDdxxQO{" +
                "sfglid='" + sfglid + '\'' +
                ", sfglidlx='" + sfglidlx + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", yjdh='" + yjdh + '\'' +
                ", ddbh='" + ddbh + '\'' +
                ", qlrlb='" + qlrlb + '\'' +
                ", dsfddbh='" + dsfddbh + '\'' +
                ", jfzt=" + jfzt +
                ", yhjkrkzt=" + yhjkrkzt +
                ", ddlx=" + ddlx +
                '}';
    }
}
