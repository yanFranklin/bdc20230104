package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @description 查档信息实体
 * @date : 2021/8/10 15:44
 */
@Table(name = "BDC_SL_CDXX")
@ApiModel(value = "BdcSlCdxxDO", description = "不动产受理查档信息")
public class BdcSlCdxxDO {
    @Id
    @ApiModelProperty(value = "查档信息id")
    private String cdxxid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "查档类型,存名称")
    private String cdlx;
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
    @ApiModelProperty(value = "查档类别")
    private String cdlb;

    @ApiModelProperty("不予受理原因")
    private String byslyy;

    @ApiModelProperty("需查询人证件号")
    private String xcxrzjh;

    @ApiModelProperty(value = "查询申请类别")
    private String cxsqlb;

    @ApiModelProperty(value = "查询内容")
    private String cxnr;

    @ApiModelProperty(value = "律师事务所名称")
    private String lsswsmc;

    @ApiModelProperty(value = "证件号")
    private String lsswszjh;

    @ApiModelProperty(value = "材料页数")
    private Integer lsswsclys;

    @ApiModelProperty(value = "法律事务")
    private String lsswsflsw;

    @ApiModelProperty(value = "收集信息目录清单")
    private String lsswssjxxmlqd;

    /*  @ApiModelProperty(value = "查询证号")
    private String cxzh;

    @ApiModelProperty(value = "查询坐落")
    private String cxzl;*/
    @ApiModelProperty("查询不动产单元号")
    private String bdcdyh;

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

    public String getCdlx() {
        return cdlx;
    }

    public void setCdlx(String cdlx) {
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

    public String getCdlb() {
        return cdlb;
    }

    public void setCdlb(String cdlb) {
        this.cdlb = cdlb;
    }

    public String getByslyy() {
        return byslyy;
    }

    public void setByslyy(String byslyy) {
        this.byslyy = byslyy;
    }

    public String getXcxrzjh() {
        return xcxrzjh;
    }

    public void setXcxrzjh(String xcxrzjh) {
        this.xcxrzjh = xcxrzjh;
    }

    public String getCxsqlb() {
        return cxsqlb;
    }

    public void setCxsqlb(String cxsqlb) {
        this.cxsqlb = cxsqlb;
    }

    public String getCxnr() {
        return cxnr;
    }

    public void setCxnr(String cxnr) {
        this.cxnr = cxnr;
    }

    public String getLsswsmc() {
        return lsswsmc;
    }

    public void setLsswsmc(String lsswsmc) {
        this.lsswsmc = lsswsmc;
    }

    public String getLsswszjh() {
        return lsswszjh;
    }

    public void setLsswszjh(String lsswszjh) {
        this.lsswszjh = lsswszjh;
    }

    public Integer getLsswsclys() {
        return lsswsclys;
    }

    public void setLsswsclys(Integer lsswsclys) {
        this.lsswsclys = lsswsclys;
    }

    public String getLsswsflsw() {
        return lsswsflsw;
    }

    public void setLsswsflsw(String lsswsflsw) {
        this.lsswsflsw = lsswsflsw;
    }

    public String getLsswssjxxmlqd() {
        return lsswssjxxmlqd;
    }

    public void setLsswssjxxmlqd(String lsswssjxxmlqd) {
        this.lsswssjxxmlqd = lsswssjxxmlqd;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "BdcSlCdxxDO{" +
                "cdxxid='" + cdxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", cdlx='" + cdlx + '\'' +
                ", cjzmlx=" + cjzmlx +
                ", cdr='" + cdr + '\'' +
                ", cdrzjzl=" + cdrzjzl +
                ", cdrzjh='" + cdrzjh + '\'' +
                ", cdyy='" + cdyy + '\'' +
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
                ", cdlb='" + cdlb + '\'' +
                ", byslyy='" + byslyy + '\'' +
                ", xcxrzjh='" + xcxrzjh + '\'' +
                ", cxsqlb='" + cxsqlb + '\'' +
                ", cxnr='" + cxnr + '\'' +
                ", lsswsmc='" + lsswsmc + '\'' +
                ", lsswszjh='" + lsswszjh + '\'' +
                ", lsswsclys=" + lsswsclys +
                ", lsswsflsw='" + lsswsflsw + '\'' +
                ", lsswssjxxmlqd='" + lsswssjxxmlqd + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
