package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/05/11
 * @description 不动产受理发票信息DO
 */
@Table(name = "BDC_SL_FPXX")
@ApiModel(value = "BdcSlFpxxDO", description = "不动产受理发票信息")
public class BdcSlFpxxDO {

    @Id
    @ApiModelProperty(value = "主键，发票信息ID")
    private String fpxxid;
    @ApiModelProperty(value = "收费信息ID")
    private String sfxxid;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "发票代码")
    private String fpdm;
    @ApiModelProperty(value = "发票号")
    private String fph;
    @ApiModelProperty(value = "开票日期")
    private Date kprq;
    @ApiModelProperty(value = "发票种类名称")
    private String fpzlmc;
    @ApiModelProperty(value = "发票状态: 1（正常） 4(冲红)")
    private Integer fpzt;
    @ApiModelProperty(value = "发票链接地址")
    private String fpurl;

    public String getFpxxid() {
        return fpxxid;
    }

    public void setFpxxid(String fpxxid) {
        this.fpxxid = fpxxid;
    }

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getFpdm() {
        return fpdm;
    }

    public void setFpdm(String fpdm) {
        this.fpdm = fpdm;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public Date getKprq() {
        return kprq;
    }

    public void setKprq(Date kprq) {
        this.kprq = kprq;
    }

    public String getFpzlmc() {
        return fpzlmc;
    }

    public void setFpzlmc(String fpzlmc) {
        this.fpzlmc = fpzlmc;
    }

    public Integer getFpzt() {
        return fpzt;
    }

    public void setFpzt(Integer fpzt) {
        this.fpzt = fpzt;
    }

    public String getFpurl() {
        return fpurl;
    }

    public void setFpurl(String fpurl) {
        this.fpurl = fpurl;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSlFpxxDO.class.getSimpleName() + "[", "]")
                .add("fpxxid='" + fpxxid + "'")
                .add("sfxxid='" + sfxxid + "'")
                .add("xmid='" + xmid + "'")
                .add("fpdm='" + fpdm + "'")
                .add("fph='" + fph + "'")
                .add("kprq=" + kprq)
                .add("fpzlmc='" + fpzlmc + "'")
                .add("fpzt=" + fpzt)
                .add("fpurl='" + fpurl + "'")
                .toString();
    }
}
