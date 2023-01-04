package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/12/19
 * @description 自助打证机证明查询接口返回证明信息
 */
@Api(value = "BdcZzdzZmxxDTO", description = "自助打证机证明查询接口返回证明信息")
public class BdcZzdzZmxxDTO {

    @ApiModelProperty(value = "业务编号")
    private String slbh;

    @ApiModelProperty(value = "不动产登记证明号")
    private String bdcqzh;

    @ApiModelProperty(value = "证号省份简称")
    private String sqsjc;

    @ApiModelProperty(value = "证号年份")
    private String nf;

    @ApiModelProperty(value = "证号区县")
    private String szsxqc;

    @ApiModelProperty(value = "证明流水号")
    private String zhlsh;

    @ApiModelProperty(value = "证明权利类型")
    private String zmqlsx;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "义务人")
    private String ywr;

    @ApiModelProperty(value = "不动产坐落")
    private String zl;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利其他信息")
    private String qlqtzk;

    @ApiModelProperty(value = "附记信息")
    private String fj;

    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    @ApiModelProperty(value = "登记时间年")
    private String djn;

    @ApiModelProperty(value = "登记时间月")
    private String djy;

    @ApiModelProperty(value = "登记时间日")
    private String djr;

    @ApiModelProperty(value = "证明id")
    private String zsid;

    @ApiModelProperty(value = "抵押物清单base64")
    private String dywqd;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getZhlsh() {
        return zhlsh;
    }

    public void setZhlsh(String zhlsh) {
        this.zhlsh = zhlsh;
    }

    public String getZmqlsx() {
        return zmqlsx;
    }

    public void setZmqlsx(String zmqlsx) {
        this.zmqlsx = zmqlsx;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
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

    public String getQlqtzk() {
        return qlqtzk;
    }

    public void setQlqtzk(String qlqtzk) {
        this.qlqtzk = qlqtzk;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDjn() {
        return djn;
    }

    public void setDjn(String djn) {
        this.djn = djn;
    }

    public String getDjy() {
        return djy;
    }

    public void setDjy(String djy) {
        this.djy = djy;
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getZsid() {
        return zsid;
    }

    public void setZsid(String zsid) {
        this.zsid = zsid;
    }

    public String getDywqd() {
        return dywqd;
    }

    public void setDywqd(String dywqd) {
        this.dywqd = dywqd;
    }

    @Override
    public String toString() {
        return "BdcZzdzZmxxDTO{" +
                "slbh='" + slbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", sqsjc='" + sqsjc + '\'' +
                ", nf='" + nf + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", zhlsh='" + zhlsh + '\'' +
                ", zmqlsx='" + zmqlsx + '\'' +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlqtzk='" + qlqtzk + '\'' +
                ", fj='" + fj + '\'' +
                ", djsj=" + djsj +
                ", djn='" + djn + '\'' +
                ", djy='" + djy + '\'' +
                ", djr='" + djr + '\'' +
                ", zsid='" + zsid + '\'' +
                ", dywqd='" + dywqd + '\'' +
                '}';
    }
}
