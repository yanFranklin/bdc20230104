package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/5
 * @description 不动产受理申请人
 */
@Table(name = "BDC_SL_SQR")
@ApiModel(value = "BdcSlSqrDO", description = "不动产受理申请人")
public class BdcSlSqrDO implements Serializable {
    private static final long serialVersionUID = 3796664761559303730L;
    @Id
    @ApiModelProperty(value = "申请人ID")
    private String sqrid;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "申请人名称")
    private String sqrmc;

    @ApiModelProperty(value = "证件种类")
    private Integer zjzl;

    @ApiModelProperty(value = "证件号")
    private String zjh;

    @ApiModelProperty(value = "通讯地址")
    private String txdz;

    @ApiModelProperty(value = "邮编")
    private String yb;

    @ApiModelProperty(value = "性别")
    private Integer xb;

    @ApiModelProperty(value = "法人名称")
    private String frmc;

    @ApiModelProperty(value = "法人电话")
    private String frdh;

    @ApiModelProperty(value = "代理人名称")
    private String dlrmc;

    @ApiModelProperty(value = "代理人电话")
    private String dlrdh;

    @ApiModelProperty(value = "代理机构")
    private String dljg;

    @ApiModelProperty(value = "申请人类型")
    private Integer sqrlx;

    @ApiModelProperty(value = "申请人类别(1-权利人；2-义务人)")
    private String sqrlb;

    @ApiModelProperty(value = "权利比例")
    private String qlbl;

    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "电话")
    private String dh;

    @ApiModelProperty(value = "所属行业")
    private String sshy;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "是否持证人")
    private Integer sfczr;

    @ApiModelProperty(value = "是否领证人")
    private Integer sflzr;

    @ApiModelProperty(value = "顺序号")
    private Integer sxh;

    @ApiModelProperty(value = "婚姻状况")
    private String hyzk;

    @ApiModelProperty(value = "是否本地户籍")
    private Integer sfbdhj;

    @ApiModelProperty(value = "申报房屋套次")
    private String sbfwtc;

    @ApiModelProperty(value = "房屋套次")
    private String fwtc;

    @ApiModelProperty(value = "共有人是否夫妻关系")
    private Integer gyrsffq;

    @ApiModelProperty(value = "是否税费减免")
    private Integer sfjm;

    @ApiModelProperty(value = "是否直系亲属")
    private Integer sfzxqs;

    @ApiModelProperty(value = "家庭满五唯一住宅")
    private Integer jtmwwyzz;

    @ApiModelProperty(value = "代理人证件种类")
    private Integer dlrzjzl;

    @ApiModelProperty(value = "代理人证件号")
    private String dlrzjh;

    @ApiModelProperty(value = "法人证件种类")
    private Integer frzjzl;

    @ApiModelProperty(value = "法人证件号")
    private String frzjh;

    @ApiModelProperty(value = "共有人标识")
    private String gyrbj;

    @ApiModelProperty(value = "婚姻信息比对结果")
    private Integer hyxxbdjg;

    @ApiModelProperty(value = "房屋套次比对结果")
    private Integer fwtcbdjg;

    @ApiModelProperty(value = "小规模纳税人")
    private Integer xgmnsr;

    @ApiModelProperty(value = "交易份额")
    private String jyfe;

    @ApiModelProperty(value = "是否购买满两年")
    private String sfgmmln;

    @ApiModelProperty(value = "权利人来源")
    private Integer qlrly;

    @ApiModelProperty(value = "曾用名")
    private String cym;

    @ApiModelProperty(value = "是否月结（1：月结）")
    private Integer sfyj;

    @ApiModelProperty(value = "是否主产权人")
    private Integer sfzcqr;

    @ApiModelProperty(value = "转让方承受方关系")
    private Integer zrfcsfgx;

    public String getCym() {
        return cym;
    }

    public void setCym(String cym) {
        this.cym = cym;
    }

    public String getSqrid() {
        return this.sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getXmid() {
        return this.xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrmc() {
        return this.sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public Integer getZjzl() {
        return this.zjzl;
    }

    public void setZjzl(Integer zjzl) {
        this.zjzl = zjzl;
    }

    public String getZjh() {
        return this.zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getTxdz() {
        return this.txdz;
    }

    public void setTxdz(String txdz) {
        this.txdz = txdz;
    }

    public String getYb() {
        return this.yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public Integer getXb() {
        return this.xb;
    }

    public void setXb(Integer xb) {
        this.xb = xb;
    }

    public String getFrmc() {
        return this.frmc;
    }

    public void setFrmc(String frmc) {
        this.frmc = frmc;
    }

    public String getFrdh() {
        return this.frdh;
    }

    public void setFrdh(String frdh) {
        this.frdh = frdh;
    }

    public String getDlrmc() {
        return this.dlrmc;
    }

    public void setDlrmc(String dlrmc) {
        this.dlrmc = dlrmc;
    }

    public String getDlrdh() {
        return this.dlrdh;
    }

    public void setDlrdh(String dlrdh) {
        this.dlrdh = dlrdh;
    }

    public String getDljg() {
        return this.dljg;
    }

    public void setDljg(String dljg) {
        this.dljg = dljg;
    }

    public Integer getSqrlx() {
        return this.sqrlx;
    }

    public void setSqrlx(Integer sqrlx) {
        this.sqrlx = sqrlx;
    }

    public String getSqrlb() {
        return this.sqrlb;
    }

    public void setSqrlb(String sqrlb) {
        this.sqrlb = sqrlb;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public Integer getGyfs() {
        return this.gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyqk() {
        return this.gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getDh() {
        return this.dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getSshy() {
        return this.sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getBz() {
        return this.bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getSfczr() {
        return this.sfczr;
    }

    public void setSfczr(Integer sfczr) {
        this.sfczr = sfczr;
    }

    public Integer getSflzr() {
        return sflzr;
    }

    public void setSflzr(Integer sflzr) {
        this.sflzr = sflzr;
    }

    public Integer getSxh() {
        return this.sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getHyzk() {
        return hyzk;
    }

    public void setHyzk(String hyzk) {
        this.hyzk = hyzk;
    }

    public Integer getSfbdhj() {
        return sfbdhj;
    }

    public void setSfbdhj(Integer sfbdhj) {
        this.sfbdhj = sfbdhj;
    }

    public String getSbfwtc() {
        return sbfwtc;
    }

    public void setSbfwtc(String sbfwtc) {
        this.sbfwtc = sbfwtc;
    }

    public String getFwtc() {
        return fwtc;
    }

    public void setFwtc(String fwtc) {
        this.fwtc = fwtc;
    }

    public Integer getGyrsffq() {
        return gyrsffq;
    }

    public void setGyrsffq(Integer gyrsffq) {
        this.gyrsffq = gyrsffq;
    }

    public Integer getSfjm() {
        return sfjm;
    }

    public void setSfjm(Integer sfjm) {
        this.sfjm = sfjm;
    }

    public Integer getSfzxqs() {
        return sfzxqs;
    }

    public void setSfzxqs(Integer sfzxqs) {
        this.sfzxqs = sfzxqs;
    }

    public Integer getJtmwwyzz() {
        return jtmwwyzz;
    }

    public void setJtmwwyzz(Integer jtmwwyzz) {
        this.jtmwwyzz = jtmwwyzz;
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

    public Integer getFrzjzl() {
        return frzjzl;
    }

    public void setFrzjzl(Integer frzjzl) {
        this.frzjzl = frzjzl;
    }

    public String getFrzjh() {
        return frzjh;
    }

    public void setFrzjh(String frzjh) {
        this.frzjh = frzjh;
    }

    public String getGyrbj() {
        return gyrbj;
    }

    public void setGyrbj(String gyrbj) {
        this.gyrbj = gyrbj;
    }

    public Integer getHyxxbdjg() {
        return hyxxbdjg;
    }

    public void setHyxxbdjg(Integer hyxxbdjg) {
        this.hyxxbdjg = hyxxbdjg;
    }

    public Integer getFwtcbdjg() {
        return fwtcbdjg;
    }

    public void setFwtcbdjg(Integer fwtcbdjg) {
        this.fwtcbdjg = fwtcbdjg;
    }

    public Integer getXgmnsr() {
        return xgmnsr;
    }

    public void setXgmnsr(Integer xgmnsr) {
        this.xgmnsr = xgmnsr;
    }

    public String getJyfe() {
        return jyfe;
    }

    public void setJyfe(String jyfe) {
        this.jyfe = jyfe;
    }

    public String getSfgmmln() {
        return sfgmmln;
    }

    public void setSfgmmln(String sfgmmln) {
        this.sfgmmln = sfgmmln;
    }

    public Integer getQlrly() {
        return qlrly;
    }

    public void setQlrly(Integer qlrly) {
        this.qlrly = qlrly;
    }

    public Integer getSfyj() {
        return sfyj;
    }

    public void setSfyj(Integer sfyj) {
        this.sfyj = sfyj;
    }

    public Integer getSfzcqr() {
        return sfzcqr;
    }

    public void setSfzcqr(Integer sfzcqr) {
        this.sfzcqr = sfzcqr;
    }

    public Integer getZrfcsfgx() {
        return zrfcsfgx;
    }

    public void setZrfcsfgx(Integer zrfcsfgx) {
        this.zrfcsfgx = zrfcsfgx;
    }

    @Override
    public String toString() {
        return "BdcSlSqrDO{" +
                "sqrid='" + sqrid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sqrmc='" + sqrmc + '\'' +
                ", zjzl=" + zjzl +
                ", zjh='" + zjh + '\'' +
                ", txdz='" + txdz + '\'' +
                ", yb='" + yb + '\'' +
                ", xb=" + xb +
                ", frmc='" + frmc + '\'' +
                ", frdh='" + frdh + '\'' +
                ", dlrmc='" + dlrmc + '\'' +
                ", dlrdh='" + dlrdh + '\'' +
                ", dljg='" + dljg + '\'' +
                ", sqrlx=" + sqrlx +
                ", sqrlb='" + sqrlb + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", gyfs=" + gyfs +
                ", gyqk='" + gyqk + '\'' +
                ", dh='" + dh + '\'' +
                ", sshy='" + sshy + '\'' +
                ", bz='" + bz + '\'' +
                ", sfczr=" + sfczr +
                ", sflzr=" + sflzr +
                ", sxh=" + sxh +
                ", hyzk='" + hyzk + '\'' +
                ", sfbdhj=" + sfbdhj +
                ", sbfwtc='" + sbfwtc + '\'' +
                ", fwtc='" + fwtc + '\'' +
                ", gyrsffq=" + gyrsffq +
                ", sfjm=" + sfjm +
                ", sfzxqs=" + sfzxqs +
                ", jtmwwyzz=" + jtmwwyzz +
                ", dlrzjzl=" + dlrzjzl +
                ", dlrzjh='" + dlrzjh + '\'' +
                ", frzjzl=" + frzjzl +
                ", frzjh='" + frzjh + '\'' +
                ", gyrbj='" + gyrbj + '\'' +
                ", hyxxbdjg=" + hyxxbdjg +
                ", fwtcbdjg=" + fwtcbdjg +
                ", xgmnsr=" + xgmnsr +
                ", jyfe='" + jyfe + '\'' +
                ", sfgmmln='" + sfgmmln + '\'' +
                ", qlrly=" + qlrly +
                ", cym='" + cym + '\'' +
                ", sfyj=" + sfyj +
                ", sfzcqr=" + sfzcqr +
                ", zrfcsfgx=" + zrfcsfgx +
                '}';
    }

}
