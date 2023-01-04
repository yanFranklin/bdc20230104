package cn.gtmap.realestate.common.core.domain.certificate;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming/a>"
 * @version 1.0, 2021/11/01
 * @description 分配盒号
 */
@Table(
        name = "BDC_GDXX_FPHH"
)
public class BdcGdxxFphhDO {
    @Id
    @ApiModelProperty(value = "归档信息id")
    private String gdxxid;

    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "档案id")
    private String daid;

    @ApiModelProperty(value = "乡镇代码")
    private String xzdm;

    @ApiModelProperty(value = "乡镇名称")
    private String xzmc;

    @ApiModelProperty(value = "年份")
    private String nf;

    @ApiModelProperty(value = "盒号顺序号（按照乡镇、年份编）")
    private Integer sxh;

    @ApiModelProperty(value = "目录号（即盒号）")
    private String mlh;

    @ApiModelProperty(value = "案卷号（即档案号）")
    private String ajh;

    @ApiModelProperty(value = "档案模型")
    private String damx;

    @ApiModelProperty(value = "案卷件数")
    private String ajjs;

    @ApiModelProperty(value = "案卷页数")
    private String ajys;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "档案号生成时间",example = "2018-10-01 12:18:48")
    private Date dahscsj;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "归档时间",example = "2018-10-01 12:18:48")
    private Date gdsj;

    @ApiModelProperty(value = "归档人员姓名")
    private String gdrxm;

    @ApiModelProperty(value = "归档信息")
    private String gdxx;

    @ApiModelProperty(value = "备注")
    private String bz;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "档案号类型")
    private String dahlx;
    @ApiModelProperty(value = "全宗号")
    private String qzh;
    @ApiModelProperty(value = "封面案卷号")
    private String fmajh;
    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }

    public String getDahlx() {
        return dahlx;
    }

    public void setDahlx(String dahlx) {
        this.dahlx = dahlx;
    }

    public Date getDahscsj() {
        return dahscsj;
    }

    public void setDahscsj(Date dahscsj) {
        this.dahscsj = dahscsj;
    }

    public String getXzdm() {
        return xzdm;
    }

    public void setXzdm(String xzdm) {
        this.xzdm = xzdm;
    }

    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getGdxxid() {
        return gdxxid;
    }

    public void setGdxxid(String gdxxid) {
        this.gdxxid = gdxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDaid() {
        return daid;
    }

    public void setDaid(String daid) {
        this.daid = daid;
    }

    public String getMlh() {
        return mlh;
    }

    public void setMlh(String mlh) {
        this.mlh = mlh;
    }

    public String getAjh() {
        return ajh;
    }

    public void setAjh(String ajh) {
        this.ajh = ajh;
    }

    public String getDamx() {
        return damx;
    }

    public void setDamx(String damx) {
        this.damx = damx;
    }

    public String getAjjs() {
        return ajjs;
    }

    public void setAjjs(String ajjs) {
        this.ajjs = ajjs;
    }

    public String getAjys() {
        return ajys;
    }

    public void setAjys(String ajys) {
        this.ajys = ajys;
    }

    public Date getGdsj() {
        return gdsj;
    }

    public void setGdsj(Date gdsj) {
        this.gdsj = gdsj;
    }

    public String getGdrxm() {
        return gdrxm;
    }

    public void setGdrxm(String gdrxm) {
        this.gdrxm = gdrxm;
    }

    public String getGdxx() {
        return gdxx;
    }

    public void setGdxx(String gdxx) {
        this.gdxx = gdxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getFmajh() {
        return fmajh;
    }

    public void setFmajh(String fmajh) {
        this.fmajh = fmajh;
    }

    @Override
    public String toString() {
        return "BdcGdxxFphhDO{" +
                "gdxxid='" + gdxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", daid='" + daid + '\'' +
                ", xzdm='" + xzdm + '\'' +
                ", xzmc='" + xzmc + '\'' +
                ", nf='" + nf + '\'' +
                ", sxh=" + sxh +
                ", mlh='" + mlh + '\'' +
                ", ajh='" + ajh + '\'' +
                ", damx='" + damx + '\'' +
                ", ajjs='" + ajjs + '\'' +
                ", ajys='" + ajys + '\'' +
                ", dahscsj=" + dahscsj +
                ", gdsj=" + gdsj +
                ", gdrxm='" + gdrxm + '\'' +
                ", gdxx='" + gdxx + '\'' +
                ", bz='" + bz + '\'' +
                ", qlr='" + qlr + '\'' +
                ", dahlx='" + dahlx + '\'' +
                ", qzh='" + qzh + '\'' +
                ", fmajh='" + fmajh + '\'' +
                '}';
    }
}
