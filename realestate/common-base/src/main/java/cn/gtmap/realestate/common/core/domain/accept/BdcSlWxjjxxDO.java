package cn.gtmap.realestate.common.core.domain.accept;

import cn.gtmap.realestate.common.util.CommonConstantUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/22
 * @description 不动产受理维修基金信息
 */
@Table(name = "BDC_SL_WXJJXX")
@ApiModel(value = "BdcSlWxjjxxDO", description = "不动产受理维修基金信息")
public class BdcSlWxjjxxDO implements Serializable {

    @Id
    @ApiModelProperty(value = "维修基金信息ID")
    private String wxjjxxid;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "收费时间")
    private Date sfsj;
    @ApiModelProperty(value = "金额单位")
    private String jedw;
    @ApiModelProperty(value = "合计")
    private Double hj;
    @ApiModelProperty(value = "收费状态")
    private Integer sfzt;
    @ApiModelProperty(value = "收费状态操作时间")
    private Date sfztczsj;
    @ApiModelProperty(value = "收费名称")
    private String sfmc;
    @ApiModelProperty(value = "收费代码")
    private String sfdm;
    @ApiModelProperty(value = "订单编号")
    private String ddbh;
    @ApiModelProperty(value = "通知状态")
    private Integer tzzt;
    @ApiModelProperty(value = "权利人类别")
    private String qlrlb;
    @ApiModelProperty(value = "维修基金缴费人")
    private String wxjjjfr;
    @ApiModelProperty(value = "bdcdyh")
    private String bdcdyh;
    public String getWxjjxxid() {
        return wxjjxxid;
    }

    public void setWxjjxxid(String wxjjxxid) {
        this.wxjjxxid = wxjjxxid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Date getSfsj() {
        return sfsj;
    }

    public void setSfsj(Date sfsj) {
        this.sfsj = sfsj;
    }

    public String getJedw() {
        return jedw;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public Integer getSfzt() {
        return sfzt;
    }

    public void setSfzt(Integer sfzt) {
        this.sfzt = sfzt;
    }

    public Date getSfztczsj() {
        return sfztczsj;
    }

    public void setSfztczsj(Date sfztczsj) {
        this.sfztczsj = sfztczsj;
    }

    public String getSfmc() {
        return sfmc;
    }

    public void setSfmc(String sfmc) {
        this.sfmc = sfmc;
    }

    public String getDdbh() {
        return ddbh;
    }

    public void setDdbh(String ddbh) {
        this.ddbh = ddbh;
    }

    public String getSfdm() {
        return sfdm;
    }

    public void setSfdm(String sfdm) {
        this.sfdm = sfdm;
    }

    public Integer getTzzt() {
        return tzzt;
    }

    public void setTzzt(Integer tzzt) {
        this.tzzt = tzzt;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getWxjjjfr() {
        return wxjjjfr;
    }

    public void setWxjjjfr(String wxjjjfr) {
        this.wxjjjfr = wxjjjfr;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcSlWxjjxxDO{");
        sb.append("wxjjxxid='").append(wxjjxxid).append('\'');
        sb.append(", gzlslid='").append(gzlslid).append('\'');
        sb.append(", xmid='").append(xmid).append('\'');
        sb.append(", sfsj=").append(sfsj);
        sb.append(", jedw='").append(jedw).append('\'');
        sb.append(", hj=").append(hj);
        sb.append(", sfzt=").append(sfzt);
        sb.append(", sfztczsj=").append(sfztczsj);
        sb.append(", sfmc='").append(sfmc).append('\'');
        sb.append(", sfdm='").append(sfdm).append('\'');
        sb.append(", ddbh='").append(ddbh).append('\'');
        sb.append(", tzzt=").append(tzzt);
        sb.append(", qlrlb='").append(qlrlb).append('\'');
        sb.append(", wxjjjfr='").append(wxjjjfr).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append('}');
        return sb.toString();
    }
}