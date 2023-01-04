package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:songjiawei@gtmap.cn">songjiawei</a>
 * @description 房地产权（独幢、层、套、间房屋）
 */
@Table(
        name = "BDC_FDCQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcFdcqDO.class)
@ApiModel(value = "BdcFdcqDO", description = "不动产房地产权")
public class BdcFdcqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "土地使用权人")
    private String tdsyqr;
    @ApiModelProperty(value = "土地使用起始时间", example = "2018-10-01 12:18:48")
    private Date tdsyqssj;
    @ApiModelProperty(value = "土地使用结束时间", example = "2018-10-01 12:18:48")
    private Date tdsyjssj;
    @ApiModelProperty(value = "房地产交易价格")
    private Double jyjg;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "规划用途")
    private Integer ghyt;
    @Zd(tableClass = BdcZdFwxzDO.class)
    @ApiModelProperty(value = "房屋性质")
    private Integer fwxz;
    @Zd(tableClass = BdcZdFwjgDO.class)
    @ApiModelProperty(value = "房屋结构")
    private Integer fwjg;
    @ApiModelProperty(value = "所在层")
    private Integer szc;
    @ApiModelProperty(value = "总层数")
    private Integer zcs;
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;
    @ApiModelProperty(value = "专有（套内）建筑面积")
    private Double zyjzmj;
    @ApiModelProperty(value = "分摊建筑面积")
    private Double ftjzmj;
    @ApiModelProperty(value = "竣工时间", example = "2018-10-01")
    private String jgsj;
    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;
    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "幢号")
    private String zh;
    @Zd(tableClass = BdcZdFwlxDO.class)
    @ApiModelProperty(value = "房屋类型")
    private Integer fwlx;
    @ApiModelProperty(value = "所在名义层")
    private String szmyc;
    @ApiModelProperty(value = "层高")
    private Double cg;
    @ApiModelProperty(value = "名义总层数")
    private String myzcs;
    @ApiModelProperty(value = "土地使用权面积")
    private Double tdsyqmj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "房屋丘权号")
    private String fwqqh;
    @ApiModelProperty(value = "幢占地面积")
    private Double zzdmj;
    @ApiModelProperty(value = "房屋属性")
    private Integer fwsx;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @Zd(tableClass = BdcZdBdcdyfwlxDO.class)
    @ApiModelProperty(value = "不动产单元房屋类型")
    private Integer bdcdyfwlx;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "是否共用宗")
    private Integer sfgyz;
    @ApiModelProperty(value = "房间号")
    private String fjh;
    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;
    @ApiModelProperty(value = "土地使用起始时间2", example = "2018-10-01 12:18:48")
    private Date tdsyqssj2;
    @ApiModelProperty(value = "土地使用结束时间2", example = "2018-10-01 12:18:48")
    private Date tdsyjssj2;
    @ApiModelProperty(value = "土地使用起始时间3", example = "2018-10-01 12:18:48")
    private Date tdsyqssj3;
    @ApiModelProperty(value = "土地使用结束时间3", example = "2018-10-01 12:18:48")
    private Date tdsyjssj3;
    @ApiModelProperty(value = "用途名称")
    private String ytmc;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "批准用途")
    private Integer pzyt;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "实际用途")
    private Integer sjyt;
    @ApiModelProperty(value = "金额单位")
    private Integer jedw;
    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;
    @ApiModelProperty(value = "房屋结构名称")
    private String fwjgmc;

    public String getZxywh() {
        return zxywh;
    }

    public void setZxywh(String zxywh) {
        this.zxywh = zxywh;
    }

    public String getZxyy() {
        return zxyy;
    }

    public void setZxyy(String zxyy) {
        this.zxyy = zxyy;
    }


    public String getZxdbr() {
        return zxdbr;
    }

    public void setZxdbr(String zxdbr) {
        this.zxdbr = zxdbr;
    }

    public Date getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(Date zxdjsj) {
        this.zxdjsj = zxdjsj;
    }

    @Override
    public String getQlid() {
        return qlid;
    }

    @Override
    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
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

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public Integer getGhyt() {
        return ghyt;
    }

    public void setGhyt(Integer ghyt) {
        this.ghyt = ghyt;
    }

    public Integer getFwxz() {
        return fwxz;
    }

    public void setFwxz(Integer fwxz) {
        this.fwxz = fwxz;
    }

    public Integer getFwjg() {
        return fwjg;
    }

    public void setFwjg(Integer fwjg) {
        this.fwjg = fwjg;
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

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Double getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(Double zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    public Double getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(Double ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    @Override
    public String getSlbh() {
        return slbh;
    }

    @Override
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

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    @Override
    public String getXmid() {
        return xmid;
    }

    @Override
    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    @Override
    public String getGyqk() {
        return gyqk;
    }

    @Override
    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    @Override
    public String getDjjg() {
        return djjg;
    }

    @Override
    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @Override
    public Date getDjsj() {
        return djsj;
    }

    @Override
    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @Override
    public String getDbr() {
        return dbr;
    }

    @Override
    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    @Override
    public String getFj() {
        return fj;
    }

    @Override
    public void setFj(String fj) {
        this.fj = fj;
    }

    @Override
    public Integer getQszt() {
        return qszt;
    }

    @Override
    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public Integer getFwlx() {
        return fwlx;
    }

    public void setFwlx(Integer fwlx) {
        this.fwlx = fwlx;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public Double getCg() {
        return cg;
    }

    public void setCg(Double cg) {
        this.cg = cg;
    }

    public String getMyzcs() {
        return myzcs;
    }

    public void setMyzcs(String myzcs) {
        this.myzcs = myzcs;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFwqqh() {
        return fwqqh;
    }

    public void setFwqqh(String fwqqh) {
        this.fwqqh = fwqqh;
    }

    public Double getZzdmj() {
        return zzdmj;
    }

    public void setZzdmj(Double zzdmj) {
        this.zzdmj = zzdmj;
    }

    public Integer getFwsx() {
        return fwsx;
    }

    public void setFwsx(Integer fwsx) {
        this.fwsx = fwsx;
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

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public Integer getSfgyz() {
        return sfgyz;
    }

    public void setSfgyz(Integer sfgyz) {
        this.sfgyz = sfgyz;
    }

    public Date getTdsyqssj2() {
        return tdsyqssj2;
    }

    public void setTdsyqssj2(Date tdsyqssj2) {
        this.tdsyqssj2 = tdsyqssj2;
    }

    public Date getTdsyjssj2() {
        return tdsyjssj2;
    }

    public void setTdsyjssj2(Date tdsyjssj2) {
        this.tdsyjssj2 = tdsyjssj2;
    }

    public Date getTdsyqssj3() {
        return tdsyqssj3;
    }

    public void setTdsyqssj3(Date tdsyqssj3) {
        this.tdsyqssj3 = tdsyqssj3;
    }

    public Date getTdsyjssj3() {
        return tdsyjssj3;
    }

    public void setTdsyjssj3(Date tdsyjssj3) {
        this.tdsyjssj3 = tdsyjssj3;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public Integer getPzyt() {
        return pzyt;
    }

    public void setPzyt(Integer pzyt) {
        this.pzyt = pzyt;
    }

    public Integer getSjyt() {
        return sjyt;
    }

    public void setSjyt(Integer sjyt) {
        this.sjyt = sjyt;
    }

    public Integer getJedw() {
        return jedw;
    }

    public void setJedw(Integer jedw) {
        this.jedw = jedw;
    }

    public String getFwxzmc() {
        return fwxzmc;
    }

    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

    public String getFwjgmc() {
        return fwjgmc;
    }

    public void setFwjgmc(String fwjgmc) {
        this.fwjgmc = fwjgmc;
    }

    @Override
    public String toString() {
        return "BdcFdcqDO{" +
                "qlid='" + qlid + '\'' +
                ", tdsyqr='" + tdsyqr + '\'' +
                ", tdsyqssj=" + tdsyqssj +
                ", tdsyjssj=" + tdsyjssj +
                ", jyjg=" + jyjg +
                ", ghyt=" + ghyt +
                ", fwxz=" + fwxz +
                ", fwjg=" + fwjg +
                ", szc=" + szc +
                ", zcs=" + zcs +
                ", jzmj=" + jzmj +
                ", zyjzmj=" + zyjzmj +
                ", ftjzmj=" + ftjzmj +
                ", jgsj='" + jgsj + '\'' +
                ", dytdmj=" + dytdmj +
                ", fttdmj=" + fttdmj +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", zh='" + zh + '\'' +
                ", fwlx=" + fwlx +
                ", szmyc='" + szmyc + '\'' +
                ", cg=" + cg +
                ", myzcs='" + myzcs + '\'' +
                ", tdsyqmj=" + tdsyqmj +
                ", bz='" + bz + '\'' +
                ", fwqqh='" + fwqqh + '\'' +
                ", zzdmj=" + zzdmj +
                ", fwsx=" + fwsx +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyfwlx=" + bdcdyfwlx +
                ", sfgyz=" + sfgyz +
                ", fjh='" + fjh + '\'' +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr='" + zxdbr + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", tdsyqssj2=" + tdsyqssj2 +
                ", tdsyjssj2=" + tdsyjssj2 +
                ", tdsyqssj3=" + tdsyqssj3 +
                ", tdsyjssj3=" + tdsyjssj3 +
                ", ytmc='" + ytmc + '\'' +
                ", pzyt=" + pzyt +
                ", sjyt=" + sjyt +
                ", jedw=" + jedw +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", fwjgmc='" + fwjgmc + '\'' +
                '}';
    }
}
