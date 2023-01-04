package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@Api(value = "BdcDzzzQO", description = "证书证明查询Qo")
public class BdcDzzzQO {

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("受理编号模糊类型")
    private String slbhmhlx;

    @ApiModelProperty("产权证号")
    private String cqzh;

    @ApiModelProperty("产权证号模糊类型")
    private String cqzhmhlx;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("坐落模糊类型")
    private String zlmhlx;

    @ApiModelProperty("发证起始日期")
    private String fzqsrq;

    @ApiModelProperty("发证结束日期")
    private String fzjsrq;

    @ApiModelProperty("证书类型")
    private String zslx;

    @ApiModelProperty("证书查询类型默认：0。1 为 in 查询")
    private String zscxlx = "0";

    @ApiModelProperty("例外的流程定义 id")
    private String lwgzldyid;

    @ApiModelProperty("是否生成pdf")
    private String sfscpdf;

    @ApiModelProperty("是否生成证照标识")
    private String sfsczzbs;

    @ApiModelProperty("发证时间排序方式，不传默认DESC")
    private String fzsjOrder;

    @ApiModelProperty("权属状态")
    private Integer qszt;

    @ApiModelProperty("电子证照状态")
    private Integer dzzzzt;

    @ApiModelProperty("证照状态")
    private Integer zzzt;

    @ApiModelProperty("缮证起始日期")
    private String szqsrq;

    @ApiModelProperty("缮证结束日期")
    private String szjsrq;

    @ApiModelProperty("工作流定义ID")
    private String processDefKey;

    @ApiModelProperty("登记起始日期")
    private String djqsrq;

    @ApiModelProperty("登记结束日期")
    private String djjsrq;

    @ApiModelProperty(value = "区县代码集合")
    private List<String> qxdmList;

    public Integer getZzzt() {
        return zzzt;
    }

    public void setZzzt(Integer zzzt) {
        this.zzzt = zzzt;
    }

    public String getZslx() {
        return zslx;
    }

    public void setZslx(String zslx) {
        this.zslx = zslx;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getFzqsrq() {
        return fzqsrq;
    }

    public void setFzqsrq(String fzqsrq) {
        this.fzqsrq = fzqsrq;
    }

    public String getFzjsrq() {
        return fzjsrq;
    }

    public void setFzjsrq(String fzjsrq) {
        this.fzjsrq = fzjsrq;
    }

    public String getSfscpdf() {
        return sfscpdf;
    }

    public void setSfscpdf(String sfscpdf) {
        this.sfscpdf = sfscpdf;
    }

    public String getSfsczzbs() {
        return sfsczzbs;
    }

    public void setSfsczzbs(String sfsczzbs) {
        this.sfsczzbs = sfsczzbs;
    }

    public String getFzsjOrder() {
        return fzsjOrder;
    }

    public void setFzsjOrder(String fzsjOrder) {
        this.fzsjOrder = fzsjOrder;
    }

    public String getSlbhmhlx() {
        return slbhmhlx;
    }

    public void setSlbhmhlx(String slbhmhlx) {
        this.slbhmhlx = slbhmhlx;
    }

    public String getCqzhmhlx() {
        return cqzhmhlx;
    }

    public void setCqzhmhlx(String cqzhmhlx) {
        this.cqzhmhlx = cqzhmhlx;
    }

    public String getZlmhlx() {
        return zlmhlx;
    }

    public void setZlmhlx(String zlmhlx) {
        this.zlmhlx = zlmhlx;
    }

    public String getZscxlx() {
        return zscxlx;
    }

    public void setZscxlx(String zscxlx) {
        this.zscxlx = zscxlx;
    }

    public String getLwgzldyid() {
        return lwgzldyid;
    }

    public void setLwgzldyid(String lwgzldyid) {
        this.lwgzldyid = lwgzldyid;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getSzqsrq() {
        return szqsrq;
    }

    public void setSzqsrq(String szqsrq) {
        this.szqsrq = szqsrq;
    }

    public String getSzjsrq() {
        return szjsrq;
    }

    public void setSzjsrq(String szjsrq) {
        this.szjsrq = szjsrq;
    }

    public String getProcessDefKey() {
        return processDefKey;
    }

    public void setProcessDefKey(String processDefKey) {
        this.processDefKey = processDefKey;
    }

    public String getDjqsrq() {
        return djqsrq;
    }

    public void setDjqsrq(String djqsrq) {
        this.djqsrq = djqsrq;
    }

    public String getDjjsrq() {
        return djjsrq;
    }

    public void setDjjsrq(String djjsrq) {
        this.djjsrq = djjsrq;
    }

    public Integer getDzzzzt() {return dzzzzt;}

    public void setDzzzzt(Integer dzzzzt) {this.dzzzzt = dzzzzt;}

    public List<String> getQxdmList() {
        return qxdmList;
    }

    public void setQxdmList(List<String> qxdmList) {
        this.qxdmList = qxdmList;
    }

    @Override
    public String toString() {
        return "BdcDzzzQO{" +
                "slbh='" + slbh + '\'' +
                "qszt='" + qszt + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", zslx='" + zslx + '\'' +
                ", zl='" + zl + '\'' +
                ", fzqsrq='" + fzqsrq + '\'' +
                ", fzjsrq='" + fzjsrq + '\'' +
                ", sfscpdf='" + sfscpdf + '\'' +
                ", sfsczzbs='" + sfsczzbs + '\'' +
                ", fzsjOrder='" + fzsjOrder + '\'' +
                ", szjsrq='" + szjsrq + '\'' +
                ", szqsrq='" + szqsrq + '\'' +
                ", djqsrq='" + djqsrq + '\'' +
                ", djjsrq='" + djjsrq + '\'' +
                ", processDefKey='" + processDefKey + '\'' +
                ", dzzzzt='" + dzzzzt + '\'' +
                ", qxdmList='" + qxdmList + '\'' +
                '}';
    }
}
