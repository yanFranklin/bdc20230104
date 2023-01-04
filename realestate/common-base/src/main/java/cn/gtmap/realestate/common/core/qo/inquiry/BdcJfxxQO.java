package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2019/08/14
 * @description
 */
@Api(value = "BdcJfxxQO", description = "解封信息对象")
public class BdcJfxxQO {

    @ApiModelProperty("解封时间")
    private String jfsj;

    @ApiModelProperty("解封登记时间")
    private String jfdjsj;

    @ApiModelProperty("解封文号")
    private String jfwh;

    @ApiModelProperty("解封机构")
    private String jfjg;

    @ApiModelProperty("解封登簿人")
    private String jfdbr;

    @ApiModelProperty("解封业务号")
    private String jfywh;

    @ApiModelProperty("解封文件")
    private String jfwj;

    @ApiModelProperty("解封原因")
    private String jfyy;

    @ApiModelProperty("附记")
    private String fj;

    @ApiModelProperty("xmid")
    private String xmid;

    @ApiModelProperty("查封类型")
    private String cflx;

    @ApiModelProperty("查封起始时间")
    private String cfqssj;

    @ApiModelProperty("查封文号")
    private String cfwh;

    @ApiModelProperty("查封结束时间")
    private String cfjssj;

    @ApiModelProperty("登簿人")
    private String dbr;

    @ApiModelProperty("权属状态")
    private String qszt;

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("被执行人")
    private String bzxr;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产权证号")
    private String bdcqzh;

    @ApiModelProperty("项目id集合")
    private List xmidlist;

    @ApiModelProperty("不动产单元号集合")
    private List bdcdyhlist;

    public List getXmidlist() {
        return xmidlist;
    }

    public void setXmidlist(List xmidlist) {
        this.xmidlist = xmidlist;
    }

    public String getBzxr() {
        return bzxr;
    }

    public void setBzxr(String bzxr) {
        this.bzxr = bzxr;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
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

    public String getCflx() {
        return cflx;
    }

    public void setCflx(String cflx) {
        this.cflx = cflx;
    }

    public String getCfqssj() {
        return cfqssj;
    }

    public void setCfqssj(String cfqssj) {
        this.cfqssj = cfqssj;
    }

    public String getCfjssj() {
        return cfjssj;
    }

    public void setCfjssj(String cfjssj) {
        this.cfjssj = cfjssj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getJfsj() {
        return jfsj;
    }

    public void setJfsj(String jfsj) {
        this.jfsj = jfsj;
    }

    public String getJfdjsj() {
        return jfdjsj;
    }

    public void setJfdjsj(String jfdjsj) {
        this.jfdjsj = jfdjsj;
    }

    public String getJfwh() {
        return jfwh;
    }

    public void setJfwh(String jfwh) {
        this.jfwh = jfwh;
    }

    public String getJfjg() {
        return jfjg;
    }

    public void setJfjg(String jfjg) {
        this.jfjg = jfjg;
    }

    public String getJfywh() {
        return jfywh;
    }

    public void setJfywh(String jfywh) {
        this.jfywh = jfywh;
    }

    public String getJfdbr() {
        return jfdbr;
    }

    public void setJfdbr(String jfdbr) {
        this.jfdbr = jfdbr;
    }

    public String getJfwj() {
        return jfwj;
    }

    public void setJfwj(String jfwj) {
        this.jfwj = jfwj;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getJfyy() {
        return jfyy;
    }

    public void setJfyy(String jfyy) {
        this.jfyy = jfyy;
    }

    public List getBdcdyhlist() {
        return bdcdyhlist;
    }

    public void setBdcdyhlist(List bdcdyhlist) {
        this.bdcdyhlist = bdcdyhlist;
    }
}
