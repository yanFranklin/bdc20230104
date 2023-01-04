package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 不动产预告
 */
@Table(name = "BDC_YG")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = BdcYgDO.class)
@ApiModel(value = "BdcYgDO", description = "不动产预告信息")
public class BdcYgDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @Zd(tableClass = BdcZdYgdjzlDO.class)
    @ApiModelProperty(value = "预告登记种类")
    private Integer ygdjzl;
    @ApiModelProperty(value = "土地使用权人")
    private String tdsyqr;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "规划用途")
    private Integer ghyt;
    @ApiModelProperty(value = "规划用途名称")
    private String ghytmc;
    @Zd(tableClass = BdcZdFwxzDO.class)
    @ApiModelProperty(value = "房屋性质")
    private Integer fwxz;
    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;
    @ApiModelProperty(value = "建筑面积取得价格/被担保主债权数额")
    private Double qdjg;
    @ApiModelProperty(value = "面积单位")
    private Integer mjdw;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date zwlxqssj;
    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date zwlxjssj;
    @ApiModelProperty(value = "所在层")
    private Integer szc;
    @ApiModelProperty(value = "总层数")
    private Integer zcs;
    @ApiModelProperty(value = "交易金额")
    private Double jyje;
    @ApiModelProperty(value = "所在名义层")
    private String szmyc;
    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "土地使用起始时间",example = "2018-10-01 12:18:48")
    private Date tdsyqssj;
    @ApiModelProperty(value = "土地使用结束时间",example = "2018-10-01 12:18:48")
    private Date tdsyjssj;
    @Zd(tableClass = BdcZdDyfsDO.class)
    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;
    @ApiModelProperty(value = "注销预告业务号")
    private String zxygywh;
    @ApiModelProperty(value = "注销预告原因")
    private String zxygyy;
    @ApiModelProperty(value = "注销预告登簿人")
    private String zxygdbr;
    @ApiModelProperty(value = "注销预告登记时间",example = "2018-10-01 12:18:48")
    private Date zxygdjsj;
    @ApiModelProperty(value = "物理层")
    private Integer wlc;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "幢号")
    private String zh;
    @ApiModelProperty(value = "房间号")
    private String fjh;
    @ApiModelProperty(value = "名义总层数")
    private String myzcs;
    @ApiModelProperty(value = "担保范围")
    private String dbfw;

    @ApiModelProperty(value = "禁止转让")
    private Integer jzzr;

    @ApiModelProperty(value = "金额单位")
    private Integer jedw;
    @ApiModelProperty(value = "用途名称")
    private String ytmc;


    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Integer getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(Integer ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    public Integer getGhyt() {
        return ghyt;
    }

    public void setGhyt(Integer ghyt) {
        this.ghyt = ghyt;
    }

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    public Integer getFwxz() {
        return fwxz;
    }

    public void setFwxz(Integer fwxz) {
        this.fwxz = fwxz;
    }

    public String getFwxzmc() {
        return fwxzmc;
    }

    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    public Integer getMjdw() {
        return mjdw;
    }

    public void setMjdw(Integer mjdw) {
        this.mjdw = mjdw;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    @Override
    public String getDjjg() {
        return djjg;
    }

    @Override
    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public Integer getSzc() {
        return szc;
    }

    public void setSzc(Integer szc) {
        this.szc = szc;
    }

    public Integer getZcs() {
        return zcs;
    }

    public void setZcs(Integer zcs) {
        this.zcs = zcs;
    }

    public Double getJyje() {
        return jyje;
    }

    public void setJyje(Double jyje) {
        this.jyje = jyje;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public String getZxygywh() {
        return zxygywh;
    }

    public void setZxygywh(String zxygywh) {
        this.zxygywh = zxygywh;
    }

    public String getZxygyy() {
        return zxygyy;
    }

    public void setZxygyy(String zxygyy) {
        this.zxygyy = zxygyy;
    }

    public String getZxygdbr() {
        return zxygdbr;
    }

    public void setZxygdbr(String zxygdbr) {
        this.zxygdbr = zxygdbr;
    }

    public Date getZxygdjsj() {
        return zxygdjsj;
    }

    public void setZxygdjsj(Date zxygdjsj) {
        this.zxygdjsj = zxygdjsj;
    }

    public Integer getWlc() {
        return wlc;
    }

    public void setWlc(Integer wlc) {
        this.wlc = wlc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getMyzcs() {
        return myzcs;
    }

    public void setMyzcs(String myzcs) {
        this.myzcs = myzcs;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public Integer getJzzr() {
        return jzzr;
    }

    public void setJzzr(Integer jzzr) {
        this.jzzr = jzzr;
    }

    public Integer getJedw() {
        return jedw;
    }

    public void setJedw(Integer jedw) {
        this.jedw = jedw;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    @Override
    public String toString() {
        return "BdcYgDO{" +
                "qlid='" + qlid + '\'' +
                ", ygdjzl=" + ygdjzl +
                ", tdsyqr='" + tdsyqr + '\'' +
                ", ghyt=" + ghyt +
                ", ghytmc='" + ghytmc + '\'' +
                ", fwxz=" + fwxz +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", jzmj=" + jzmj +
                ", qdjg=" + qdjg +
                ", mjdw=" + mjdw +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", zwlxqssj=" + zwlxqssj +
                ", zwlxjssj=" + zwlxjssj +
                ", szc=" + szc +
                ", zcs=" + zcs +
                ", jyje=" + jyje +
                ", szmyc='" + szmyc + '\'' +
                ", fttdmj=" + fttdmj +
                ", bz='" + bz + '\'' +
                ", tdsyqssj=" + tdsyqssj +
                ", tdsyjssj=" + tdsyjssj +
                ", dyfs=" + dyfs +
                ", zxygywh='" + zxygywh + '\'' +
                ", zxygyy='" + zxygyy + '\'' +
                ", zxygdbr='" + zxygdbr + '\'' +
                ", zxygdjsj=" + zxygdjsj +
                ", wlc=" + wlc +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", djjg='" + djjg + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", myzcs='" + myzcs + '\'' +
                ", dbfw='" + dbfw + '\'' +
                ", jzzr=" + jzzr +
                ", jedw=" + jedw +
                ", ytmc='" + ytmc + '\'' +
                '}';
    }
}
