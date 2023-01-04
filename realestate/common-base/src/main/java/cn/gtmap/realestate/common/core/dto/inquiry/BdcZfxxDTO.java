package cn.gtmap.realestate.common.core.dto.inquiry;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/8
 * @description  不动产住房信息查询结果DTO定义
 */
public class BdcZfxxDTO {
    @ApiModelProperty(value = "不动产权证号（房产证号）")
    private String bdcqzh;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    @ApiModelProperty(value = "规划用途")
    private String ghyt;

    @ApiModelProperty(value = "规划用途代码")
    private String ghytdm;

    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "受理人")
    private String slr;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "登记类型")
    private String djlx;

    @ApiModelProperty(value = "登记原因")
    private String djyy;

    @ApiModelProperty(value = "登记时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @ApiModelProperty(value = "项目ID")
    private String xmid;

    @ApiModelProperty(value = "项目来源")
    private Integer xmly;

    @ApiModelProperty(value = "不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;

    @ApiModelProperty(value = "附记")
    private String fj;

    @ApiModelProperty(value = "权利主键")
    private String qlid;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "权利性质")
    private String qlxz;

    @ApiModelProperty(value = "权属状态")
    private String qszt;

    @ApiModelProperty(value = "交易合同号")
    private String jyhth;

    @ApiModelProperty(value = "宗地面积")
    private String zdmj;

    @ApiModelProperty(value = "土地使用起始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date tdsyqssj;

    @ApiModelProperty(value = "土地使用结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date tdsyjssj;

    @ApiModelProperty(value = "不动产类型")
    private String bdclx;

    @ApiModelProperty(value = "所在层")
    private String szc;

    @ApiModelProperty(value = "总层数")
    private String zcs;

    @ApiModelProperty(value = "所在名义层")
    private String szmyc;

    @ApiModelProperty(value = "房屋性质")
    private String fwxz;

    @ApiModelProperty(value = "交易价格")
    private Double jyjg;

    @ApiModelProperty(value = "原权利人")
    private String yqlrmc;

    @ApiModelProperty(value = "原权利人证件号")
    private String yqlrzjh;

    @ApiModelProperty(value = "注销登记时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zxdjsj;

    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;

    @ApiModelProperty(value = "权利性质名称")
    private String qlxzmc;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "土地使用权面积")
    private Double tdsyqmj;

    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;

    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;

    @ApiModelProperty(value = "登记小类")
    private String djxl;

    @ApiModelProperty(value = "证书类型")
    private Integer zslx;

    @ApiModelProperty(value = "土地用途")
    private String tdyt;

    @ApiModelProperty(value = "区县代码")
    private String qxdm;

    @ApiModelProperty(value = "是否查封")
    private Boolean sfcf;

    @ApiModelProperty(value = "是否抵押")
    private Boolean sfdya;

    @ApiModelProperty(value = "是否预告")
    private Boolean sfyg;

    @ApiModelProperty(value = "是否锁定")
    private Boolean sfsd;

    @ApiModelProperty(value = "是否异议")
    private Boolean sfyy;

    @ApiModelProperty(value = "是否合同备案数据")
    private Integer sfhtba;

    public Boolean getSfcf() {
        return sfcf;
    }

    public void setSfcf(Boolean sfcf) {
        this.sfcf = sfcf;
    }

    public Boolean getSfdya() {
        return sfdya;
    }

    public void setSfdya(Boolean sfdya) {
        this.sfdya = sfdya;
    }

    public Boolean getSfyg() {
        return sfyg;
    }

    public void setSfyg(Boolean sfyg) {
        this.sfyg = sfyg;
    }

    public Boolean getSfsd() {
        return sfsd;
    }

    public void setSfsd(Boolean sfsd) {
        this.sfsd = sfsd;
    }

    public Boolean getSfyy() {
        return sfyy;
    }

    public void setSfyy(Boolean sfyy) {
        this.sfyy = sfyy;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getQlr() { return qlr; }

    public void setQlr(String qlr) { this.qlr = qlr; }

    public String getFwxzmc() { return fwxzmc; }

    public void setFwxzmc(String fwxzmc) { this.fwxzmc = fwxzmc; }

    public String getQlxzmc() { return qlxzmc; }

    public void setQlxzmc(String qlxzmc) { this.qlxzmc = qlxzmc; }

    public String getYqlrmc() {
        return yqlrmc;
    }

    public void setYqlrmc(String yqlrmc) {
        this.yqlrmc = yqlrmc;
    }

    public String getYqlrzjh() {
        return yqlrzjh;
    }

    public void setYqlrzjh(String yqlrzjh) {
        this.yqlrzjh = yqlrzjh;
    }

    public Date getZxdjsj() {
        return zxdjsj;
    }

    public void setZxdjsj(Date zxdjsj) {
        this.zxdjsj = zxdjsj;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getGhytdm() {
        return ghytdm;
    }

    public void setGhytdm(String ghytdm) {
        this.ghytdm = ghytdm;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public String getZdmj() {
        return zdmj;
    }

    public void setZdmj(String zdmj) {
        this.zdmj = zdmj;
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

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getXmly() {
        return xmly;
    }

    public void setXmly(Integer xmly) {
        this.xmly = xmly;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public Double getTdsyqmj() {
        return tdsyqmj;
    }

    public void setTdsyqmj(Double tdsyqmj) {
        this.tdsyqmj = tdsyqmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Integer getZslx() {
        return zslx;
    }

    public void setZslx(Integer zslx) {
        this.zslx = zslx;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public Integer getSfhtba() {
        return sfhtba;
    }

    public void setSfhtba(Integer sfhtba) {
        this.sfhtba = sfhtba;
    }

    @Override
    public String toString() {
        return "BdcZfxxDTO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", zl='" + zl + '\'' +
                ", jzmj=" + jzmj +
                ", ghyt='" + ghyt + '\'' +
                ", ghytdm='" + ghytdm + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", slr='" + slr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djlx='" + djlx + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djsj=" + djsj +
                ", xmid='" + xmid + '\'' +
                ", xmly=" + xmly +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", fj='" + fj + '\'' +
                ", qlid='" + qlid + '\'' +
                ", qllx='" + qllx + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", qszt='" + qszt + '\'' +
                ", jyhth='" + jyhth + '\'' +
                ", zdmj='" + zdmj + '\'' +
                ", tdsyqssj=" + tdsyqssj +
                ", tdsyjssj=" + tdsyjssj +
                ", bdclx='" + bdclx + '\'' +
                ", szc='" + szc + '\'' +
                ", zcs='" + zcs + '\'' +
                ", szmyc='" + szmyc + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", jyjg=" + jyjg +
                ", yqlrmc='" + yqlrmc + '\'' +
                ", yqlrzjh='" + yqlrzjh + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", qlxzmc='" + qlxzmc + '\'' +
                ", qlr='" + qlr + '\'' +
                ", tdsyqmj=" + tdsyqmj +
                ", fttdmj=" + fttdmj +
                ", dytdmj=" + dytdmj +
                ", djxl='" + djxl + '\'' +
                ", zslx=" + zslx +
                ", tdyt='" + tdyt + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", sfcf=" + sfcf +
                ", sfdya=" + sfdya +
                ", sfyg=" + sfyg +
                ", sfsd=" + sfsd +
                ", sfyy=" + sfyy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof BdcZfxxDTO)){
            return false;
        }
        BdcZfxxDTO that = (BdcZfxxDTO) o;
        return Objects.equals(bdcqzh, that.bdcqzh) && Objects.equals(bdcdyh, that.bdcdyh) && Objects.equals(qlrmc, that.qlrmc) && Objects.equals(qlrzjh, that.qlrzjh) && Objects.equals(zl, that.zl) && Objects.equals(jzmj, that.jzmj) && Objects.equals(ghyt, that.ghyt) && Objects.equals(ghytdm, that.ghytdm) && Objects.equals(gyqk, that.gyqk) && Objects.equals(slr, that.slr) && Objects.equals(slbh, that.slbh) && Objects.equals(djlx, that.djlx) && Objects.equals(djyy, that.djyy) && Objects.equals(djsj, that.djsj) && Objects.equals(xmid, that.xmid) && Objects.equals(xmly, that.xmly) && Objects.equals(bdcdywybh, that.bdcdywybh) && Objects.equals(yxtcqzh, that.yxtcqzh) && Objects.equals(fj, that.fj) && Objects.equals(qlid, that.qlid) && Objects.equals(qllx, that.qllx) && Objects.equals(qlxz, that.qlxz) && Objects.equals(qszt, that.qszt) && Objects.equals(jyhth, that.jyhth) && Objects.equals(zdmj, that.zdmj) && Objects.equals(tdsyqssj, that.tdsyqssj) && Objects.equals(tdsyjssj, that.tdsyjssj) && Objects.equals(bdclx, that.bdclx) && Objects.equals(szc, that.szc) && Objects.equals(zcs, that.zcs) && Objects.equals(szmyc, that.szmyc) && Objects.equals(fwxz, that.fwxz) && Objects.equals(jyjg, that.jyjg) && Objects.equals(yqlrmc, that.yqlrmc) && Objects.equals(yqlrzjh, that.yqlrzjh) && Objects.equals(zxdjsj, that.zxdjsj) && Objects.equals(fwxzmc, that.fwxzmc) && Objects.equals(qlxzmc, that.qlxzmc) && Objects.equals(qlr, that.qlr) && Objects.equals(tdsyqmj, that.tdsyqmj) && Objects.equals(fttdmj, that.fttdmj) && Objects.equals(dytdmj, that.dytdmj) && Objects.equals(djxl, that.djxl) && Objects.equals(zslx, that.zslx) && Objects.equals(tdyt, that.tdyt) && Objects.equals(qxdm, that.qxdm) && Objects.equals(sfcf, that.sfcf) && Objects.equals(sfdya, that.sfdya) && Objects.equals(sfyg, that.sfyg) && Objects.equals(sfsd, that.sfsd) && Objects.equals(sfyy, that.sfyy) && Objects.equals(sfhtba, that.sfhtba);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bdcqzh, bdcdyh, qlrmc, qlrzjh, zl, jzmj, ghyt, ghytdm, gyqk, slr, slbh, djlx, djyy, djsj, xmid, xmly, bdcdywybh, yxtcqzh, fj, qlid, qllx, qlxz, qszt, jyhth, zdmj, tdsyqssj, tdsyjssj, bdclx, szc, zcs, szmyc, fwxz, jyjg, yqlrmc, yqlrzjh, zxdjsj, fwxzmc, qlxzmc, qlr, tdsyqmj, fttdmj, dytdmj, djxl, zslx, tdyt, qxdm, sfcf, sfdya, sfyg, sfsd, sfyy, sfhtba);
    }
}
