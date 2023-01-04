package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/10/31
 * @description 不动产所选不动产单元信息
 */
@Table(name = "BDC_SXBDCDYXX")
public class BdcSxbdcdyxxDO {
    @Id
    /**所选不动产单元ID*/
    @ApiModelProperty(value = "所选不动产单元ID")
    private String sxbdcdyd;
    /**项目ID*/
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    /**不动产单元号*/
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    /**权籍ID*/
    @ApiModelProperty(value = "权籍ID")
    private String qjid;
    /**原项目ID*/
    @ApiModelProperty(value = "原项目ID")
    private String yxmid;
    /**原不动产全证*/
    @ApiModelProperty(value = "原不动产权证")
    private String ybdcqz;
    /**不动产类型*/
    @ApiModelProperty(value = "不动产类型")
    private String bdclx;
    /**交易价格*/
    @ApiModelProperty(value = "交易价格")
    private Double jejg;
    /**抵押金额*/
    @ApiModelProperty(value = "抵押金额")
    private Double dyje;
    /**抵押方式*/
    @ApiModelProperty(value = "抵押方式")
    private String dyfs;
    /**债务履行起始时间*/
    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date zwlxqssj;
    /**债务履行结束时间*/
    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date zwlxjssj;
    /**在建建筑物坐落*/
    @ApiModelProperty(value = "在建建筑物坐落")
    private String zjjzwzl;
    /**在建建筑物抵押范围*/
    @ApiModelProperty(value = "在建建筑物抵押范围")
    private String zjjzwdyfw;
    /**最高债权确定事实*/
    @ApiModelProperty(value = "最高债权确定事实")
    private String zgzqqdss;
    /**担保范围*/
    @ApiModelProperty(value = "担保范围")
    private String dbfw;
    /**贷款方式*/
    @ApiModelProperty(value = "付款方式")
    private String dkfs;
    /**被担保主债权数额*/
    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;
    /**房屋评估价格*/
    @ApiModelProperty(value = "房屋评估价格")
    private Double fwpgjg;
    /**土地评估价格*/
    @ApiModelProperty(value = "土地评估价格")
    private Double tdpgjg;
    /**查封机关*/
    @ApiModelProperty(value = "查封机关")
    private String cfjg;
    /**查封类型*/
    @ApiModelProperty(value = "查封类型")
    private String cflx;
    /**查封文号*/
    @ApiModelProperty(value = "查封文号")
    private String cfwh;
    /**查封范围*/
    @ApiModelProperty(value = "查封范围")
    private String  cffw;
    /**查封起始时间*/
    @ApiModelProperty(value = "查封起始时间",example = "2018-10-01 12:18:48")
    private Date cfqssj;
    /**查封结束时间*/
    @ApiModelProperty(value = "查封结束时间",example = "2018-10-01 12:18:48")
    private Date cfjssj;
    /**执行申请人*/
    @ApiModelProperty(value = "执行申请人")
    private String zxsqr;
    /**被执行人*/
    @ApiModelProperty(value = "被执行人")
    private String bzxr;
    /**查封原因*/
    @ApiModelProperty(value = "查封原因")
    private String cfyy;
    /**轮候查封期限*/
    @ApiModelProperty(value = "轮候查封期限")
    private String lhcfqx;
    /**法院送达人*/
    @ApiModelProperty(value = "法院送达人")
    private String fysdr;
    /**预告登记种类*/
    @ApiModelProperty(value = "预告登记种类")
    private String ygdjzl;
    /**取得价格/主债权数额*/
    @ApiModelProperty(value = "取得价格/主债权数额")
    private Double qdjg;
    /**共有情况*/
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    /**预告债务履行起始时间*/
    @ApiModelProperty(value = "预告债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date ygzwlxqssj;
    /**预告债务履行结束时间*/
    @ApiModelProperty(value = "预告债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date ygzwlxjssj;
    /**预告抵押方式*/
    @ApiModelProperty(value = "预告抵押方式")
    private String ygdyfs;

    public String getSxbdcdyd() {
        return sxbdcdyd;
    }

    public void setSxbdcdyd(String sxbdcdyd) {
        this.sxbdcdyd = sxbdcdyd;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQjid() {
        return qjid;
    }

    public void setQjid(String qjid) {
        this.qjid = qjid;
    }

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    public String getYbdcqz() {
        return ybdcqz;
    }

    public void setYbdcqz(String ybdcqz) {
        this.ybdcqz = ybdcqz;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public Double getJejg() {
        return jejg;
    }

    public void setJejg(Double jejg) {
        this.jejg = jejg;
    }

    public Double getDyje() {
        return dyje;
    }

    public void setDyje(Double dyje) {
        this.dyje = dyje;
    }

    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public String getZjjzwzl() {
        return zjjzwzl;
    }

    public void setZjjzwzl(String zjjzwzl) {
        this.zjjzwzl = zjjzwzl;
    }

    public String getZjjzwdyfw() {
        return zjjzwdyfw;
    }

    public void setZjjzwdyfw(String zjjzwdyfw) {
        this.zjjzwdyfw = zjjzwdyfw;
    }

    public String getZgzqqdss() {
        return zgzqqdss;
    }

    public void setZgzqqdss(String zgzqqdss) {
        this.zgzqqdss = zgzqqdss;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Double getFwpgjg() {
        return fwpgjg;
    }

    public void setFwpgjg(Double fwpgjg) {
        this.fwpgjg = fwpgjg;
    }

    public Double getTdpgjg() {
        return tdpgjg;
    }

    public void setTdpgjg(Double tdpgjg) {
        this.tdpgjg = tdpgjg;
    }

    public String getCfjg() {
        return cfjg;
    }

    public void setCfjg(String cfjg) {
        this.cfjg = cfjg;
    }

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCfwh() {
        return cfwh;
    }

    public void setCfwh(String cfwh) {
        this.cfwh = cfwh;
    }

    public String getCffw() {
        return cffw;
    }

    public void setCffw(String cffw) {
        this.cffw = cffw;
    }

    public Date getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(Date cfqssj) {
        this.cfqssj = cfqssj;
    }

    public Date getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(Date cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getZxsqr() {
        return zxsqr;
    }

    public void setZxsqr(String zxsqr) {
        this.zxsqr = zxsqr;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getCfyy() {
        return cfyy;
    }

    public void setCfyy(String cfyy) {
        this.cfyy = cfyy;
    }

    public String getLhcfqx() {
        return lhcfqx;
    }

    public void setLhcfqx(String lhcfqx) {
        this.lhcfqx = lhcfqx;
    }

    public String getFysdr() {
        return fysdr;
    }

    public void setFysdr(String fysdr) {
        this.fysdr = fysdr;
    }

    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Date getYgzwlxqssj() {
        return ygzwlxqssj;
    }

    public void setYgzwlxqssj(Date ygzwlxqssj) {
        this.ygzwlxqssj = ygzwlxqssj;
    }

    public Date getYgzwlxjssj() {
        return ygzwlxjssj;
    }

    public void setYgzwlxjssj(Date ygzwlxjssj) {
        this.ygzwlxjssj = ygzwlxjssj;
    }

    public String getYgdyfs() {
        return ygdyfs;
    }

    public void setYgdyfs(String ygdyfs) {
        this.ygdyfs = ygdyfs;
    }
}
