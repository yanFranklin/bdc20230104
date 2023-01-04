package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @program: realestate
 * @description: 查档信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 16:43
 **/
@Table(name = "BDC_CDXX")
@ApiModel(value = "BdcCdxxDO", description = "不动产查档信息")
public class BdcCdxxDO implements Serializable {
    @Id
    @ApiModelProperty(value = "查档信息id")
    private String cdxxid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "查档类型")
    private Integer cdlx;
    @ApiModelProperty(value = "出具证明类型")
    private Integer cjzmlx;
    @ApiModelProperty(value = "查档人")
    private String cdr;
    @ApiModelProperty(value = "查档人证件种类")
    private Integer cdrzjzl;
    @ApiModelProperty(value = "查档人证件号")
    private String cdrzjh;
    @ApiModelProperty(value = "查档原因")
    private String cdyy;
    @ApiModelProperty(value = "打印纸张类型")
    private String dyzzlx;
    @ApiModelProperty(value = "打印纸张页数")
    private String dyzzys;
    @ApiModelProperty(value = "是否出具证明")
    private Integer sfcjzm;
    @ApiModelProperty(value = "发票号码")
    private String fph;
    @ApiModelProperty(value = "需查询人")
    private String xcxr;
    @ApiModelProperty(value = "产权证号")
    private String cqzh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "查询结果")
    private String cxjg;

    @ApiModelProperty(value = "代理人")
    private String dlr;

    @ApiModelProperty(value = "代理人证件种类")
    private Integer dlrzjzl;

    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;

    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;

    @ApiModelProperty(value = "查询目的或用途")
    private String cxmdhyt;

    @ApiModelProperty(value = "登记簿记载信息")
    private String djbjzxx;

    @ApiModelProperty(value = "登记原始凭证")
    private String djyspz;

    @ApiModelProperty(value = "查询结果要求")
    private String cxjgyq;

    @ApiModelProperty(value = "查档人电话")
    private String cdrdh;

    @ApiModelProperty(value = "查询结果要求其他")
    private String cxmdhytqt;

    @ApiModelProperty(value = "需查询事项")
    private String xcxsx;

    /*  @ApiModelProperty(value = "查询证号")
    private String cxzh;

    @ApiModelProperty(value = "查询坐落")
    private String cxzl;*/

    public String getCxmdhytqt() {
        return cxmdhytqt;
    }

    public void setCxmdhytqt(String cxmdhytqt) {
        this.cxmdhytqt = cxmdhytqt;
    }

    public String getDlr() {
        return dlr;
    }

    public void setDlr(String dlr) {
        this.dlr = dlr;
    }

    public Integer getDlrzjzl() {
        return dlrzjzl;
    }

    public void setDlrzjzl(Integer dlrzjzl) {
        this.dlrzjzl = dlrzjzl;
    }

    public String getDlrzjh() {
        return dlrzjh;
    }

    public void setDlrzjh(String dlrzjh) {
        this.dlrzjh = dlrzjh;
    }

    public String getDlrdh() {
        return dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getCxmdhyt() {
        return cxmdhyt;
    }

    public void setCxmdhyt(String cxmdhyt) {
        this.cxmdhyt = cxmdhyt;
    }

    public String getDjbjzxx() {
        return djbjzxx;
    }

    public void setDjbjzxx(String djbjzxx) {
        this.djbjzxx = djbjzxx;
    }

    public String getDjyspz() {
        return djyspz;
    }

    public void setDjyspz(String djyspz) {
        this.djyspz = djyspz;
    }

    public String getCxjgyq() {
        return cxjgyq;
    }

    public void setCxjgyq(String cxjgyq) {
        this.cxjgyq = cxjgyq;
    }

    public String getCdxxid() {
        return cdxxid;
    }

    public void setCdxxid(String cdxxid) {
        this.cdxxid = cdxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getCdlx() {
        return cdlx;
    }

    public void setCdlx(Integer cdlx) {
        this.cdlx = cdlx;
    }

    public Integer getCjzmlx() {
        return cjzmlx;
    }

    public void setCjzmlx(Integer cjzmlx) {
        this.cjzmlx = cjzmlx;
    }

    public String getCdr() {
        return cdr;
    }

    public void setCdr(String cdr) {
        this.cdr = cdr;
    }

    public Integer getCdrzjzl() {
        return cdrzjzl;
    }

    public void setCdrzjzl(Integer cdrzjzl) {
        this.cdrzjzl = cdrzjzl;
    }

    public String getCdrzjh() {
        return cdrzjh;
    }

    public void setCdrzjh(String cdrzjh) {
        this.cdrzjh = cdrzjh;
    }

    public String getCdyy() {
        return cdyy;
    }

    public void setCdyy(String cdyy) {
        this.cdyy = cdyy;
    }

    public String getDyzzlx() {
        return dyzzlx;
    }

    public void setDyzzlx(String dyzzlx) {
        this.dyzzlx = dyzzlx;
    }

    public String getDyzzys() {
        return dyzzys;
    }

    public void setDyzzys(String dyzzys) {
        this.dyzzys = dyzzys;
    }

    public Integer getSfcjzm() {
        return sfcjzm;
    }

    public void setSfcjzm(Integer sfcjzm) {
        this.sfcjzm = sfcjzm;
    }

    public String getFph() {
        return fph;
    }

    public void setFph(String fph) {
        this.fph = fph;
    }

    public String getXcxr() {
        return xcxr;
    }

    public void setXcxr(String xcxr) {
        this.xcxr = xcxr;
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

    public String getCxjg() {
        return cxjg;
    }

    public void setCxjg(String cxjg) {
        this.cxjg = cxjg;
    }

    public String getCdrdh() {
        return cdrdh;
    }

    public void setCdrdh(String cdrdh) {
        this.cdrdh = cdrdh;
    }

    public String getXcxsx() {
        return xcxsx;
    }

    public void setXcxsx(String xcxsx) {
        this.xcxsx = xcxsx;
    }

    @Override
    public String toString() {
        return "BdcCdxxDO{" +
                "cdxxid='" + cdxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", cdlx=" + cdlx +
                ", cjzmlx=" + cjzmlx +
                ", cdr='" + cdr + '\'' +
                ", cdrzjzl=" + cdrzjzl +
                ", cdrzjh='" + cdrzjh + '\'' +
                ", cdyy='" + cdyy + '\'' +
                ", dyzzlx='" + dyzzlx + '\'' +
                ", dyzzys='" + dyzzys + '\'' +
                ", sfcjzm=" + sfcjzm +
                ", fph='" + fph + '\'' +
                ", xcxr='" + xcxr + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", zl='" + zl + '\'' +
                ", cxjg='" + cxjg + '\'' +
                ", dlr='" + dlr + '\'' +
                ", dlrzjzl=" + dlrzjzl +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", cxmdhyt='" + cxmdhyt + '\'' +
                ", djbjzxx='" + djbjzxx + '\'' +
                ", djyspz='" + djyspz + '\'' +
                ", cxjgyq='" + cxjgyq + '\'' +
                ", cdrdh='" + cdrdh + '\'' +
                ", cxmdhytqt='" + cxmdhytqt + '\'' +
                ", xcxsx='" + xcxsx + '\'' +
                '}';
    }
}
