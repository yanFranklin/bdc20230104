package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:songjiawei@gtmap.cn">songjiawei</a>
 * @description 抵押权
 */
@Table(
        name = "BDC_DYAQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcDyaqDO.class)
@ApiModel(value = "BdcDyaqDO",description = "不动产抵押权")
public class BdcDyaqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "债务人")
    private String zwr;
    @Zd(tableClass = BdcZdDyfsDO.class)
    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;
    @ApiModelProperty(value = "在建建筑物坐落")
    private String zjjzwzl;
    @ApiModelProperty(value = "在建建筑物抵押范围")
    private String zjjzwdyfw;
    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    private Date zwlxqssj;
    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    private Date zwlxjssj;
    @ApiModelProperty(value = "最高债权确定事实")
    private String zgzqqdss;
    @ApiModelProperty(value = "最高债权确定数额")
    private Double zgzqqdse;
    @ApiModelProperty(value = "注销抵押业务号")
    private String zxdyywh;
    @ApiModelProperty(value = "注销抵押原因")
    private String zxdyyy;
    @ApiModelProperty(value = "注销抵押登记时间",example = "2018-10-01 12:18:48")
    private Date zxdydjsj;
    @ApiModelProperty(value = "注销抵押登簿人")
    private String zxdydbr;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "担保范围")
    private String dbfw;
    @ApiModelProperty(value = "抵押顺位")
    private Integer dysw;
    @Zd(tableClass = BdcZdDyfsDO.class)
    @ApiModelProperty(value = "贷款方式")
    private String dkfs;
    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;
    @Zd(tableClass = BdcZdJedwDO.class)
    @ApiModelProperty(value = "金额种类")
    private Integer jezl;
    @ApiModelProperty(value = "房屋评估价格")
    private Double fwpgjg;
    @ApiModelProperty(value = "土地评估价格")
    private Double tdpgjg;
    @ApiModelProperty(value = "房屋抵押价格")
    private Double fwdyjg;
    @ApiModelProperty(value = "土地抵押价格")
    private Double tddyjg;
    @ApiModelProperty(value = "土地抵押面积")
    private Double tddymj;
    @ApiModelProperty(value = "房屋抵押面积")
    private Double fwdymj;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "是否共同担保")
    private Integer sfgtdb;
    @ApiModelProperty(value = "土地抵押面积汇总（用于批量抵押）")
    private Double tddymjsum;
    @ApiModelProperty(value = "房屋抵押面积汇总（用于批量抵押）")
    private Double fwdymjsum;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @Zd(tableClass = BdcZdDybdclxDO.class)
    @ApiModelProperty(value = "抵押不动产类型")
    private Integer dybdclx;
    @ApiModelProperty(value = "幢号")
    private String zh;
    @ApiModelProperty(value = "房间号")
    private String fjh;
    @ApiModelProperty(value = "抵押注销申请人")
    private String dyzxsqr;
    @ApiModelProperty(value = "抵押注销申请人证件号")
    private String dyzxsqrzjh;


    @ApiModelProperty(value = "币种")
    private Integer biz;
    @ApiModelProperty(value = "债务人证件号")
    private String zwrzjh;
    @ApiModelProperty(value = "债务人证件种类")
    private Integer zwrzjzl;

    @ApiModelProperty(value = "抵押套数")
    private Integer dyts;

    @ApiModelProperty(value = "最高债权额")
    private Double zgzqe;

    @ApiModelProperty(value = "禁止转让")
    private Integer jzzr;

    @ApiModelProperty(value = "抵押范围")
    private String dyfw;

    @ApiModelProperty(value = "金额单位")
    private Integer jedw;

    @ApiModelProperty(value = "抵押金额类型")
    private Integer dyjelx;

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getDyzxsqr() {
        return dyzxsqr;
    }

    public void setDyzxsqr(String dyzxsqr) {
        this.dyzxsqr = dyzxsqr;
    }

    public String getDyzxsqrzjh() {
        return dyzxsqrzjh;
    }

    public void setDyzxsqrzjh(String dyzxsqrzjh) {
        this.dyzxsqrzjh = dyzxsqrzjh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public Integer getDybdclx() {
        return dybdclx;
    }

    public void setDybdclx(Integer dybdclx) {
        this.dybdclx = dybdclx;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
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

    public String getZwr() {
        return zwr;
    }

    public void setZwr(String zwr) {
        this.zwr = zwr;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    public String getZjjzwzl() {
        return zjjzwzl;
    }

    public void setZjjzwzl(String zjjzwzl) {
        this.zjjzwzl = zjjzwzl;
    }

    public String getZjjzwdyfw() {
        return zjjzwdyfw;
    }

    public void setZjjzwdyfw(String zjjzwdyfw) {
        this.zjjzwdyfw = zjjzwdyfw;
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

    public String getZgzqqdss() {
        return zgzqqdss;
    }

    public void setZgzqqdss(String zgzqqdss) {
        this.zgzqqdss = zgzqqdss;
    }

    public Double getZgzqqdse() {
        return zgzqqdse;
    }

    public void setZgzqqdse(Double zgzqqdse) {
        this.zgzqqdse = zgzqqdse;
    }

    public String getZxdyywh() {
        return zxdyywh;
    }

    public void setZxdyywh(String zxdyywh) {
        this.zxdyywh = zxdyywh;
    }

    public String getZxdyyy() {
        return zxdyyy;
    }

    public void setZxdyyy(String zxdyyy) {
        this.zxdyyy = zxdyyy;
    }

    public Date getZxdydjsj() {
        return zxdydjsj;
    }

    public void setZxdydjsj(Date zxdydjsj) {
        this.zxdydjsj = zxdydjsj;
    }

    public String getZxdydbr() {
        return zxdydbr;
    }

    public void setZxdydbr(String zxdydbr) {
        this.zxdydbr = zxdydbr;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
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

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public Integer getDysw() {
        return dysw;
    }

    public void setDysw(Integer dysw) {
        this.dysw = dysw;
    }

    public String getDkfs() {
        return dkfs;
    }

    public void setDkfs(String dkfs) {
        this.dkfs = dkfs;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Integer getJezl() {
        return jezl;
    }

    public void setJezl(Integer jezl) {
        this.jezl = jezl;
    }

    public Double getFwpgjg() {
        return fwpgjg;
    }

    public void setFwpgjg(Double fwpgjg) {
        this.fwpgjg = fwpgjg;
    }

    public Double getTdpgjg() {
        return tdpgjg;
    }

    public void setTdpgjg(Double tdpgjg) {
        this.tdpgjg = tdpgjg;
    }

    public Double getFwdyjg() {
        return fwdyjg;
    }

    public void setFwdyjg(Double fwdyjg) {
        this.fwdyjg = fwdyjg;
    }

    public Double getTddyjg() {
        return tddyjg;
    }

    public void setTddyjg(Double tddyjg) {
        this.tddyjg = tddyjg;
    }

    public Double getTddymj() {
        return tddymj;
    }

    public void setTddymj(Double tddymj) {
        this.tddymj = tddymj;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Integer getSfgtdb() {
        return sfgtdb;
    }

    public void setSfgtdb(Integer sfgtdb) {
        this.sfgtdb = sfgtdb;
    }

    public Double getTddymjsum() {
        return tddymjsum;
    }

    public void setTddymjsum(Double tddymjsum) {
        this.tddymjsum = tddymjsum;
    }

    public Double getFwdymjsum() {
        return fwdymjsum;
    }

    public void setFwdymjsum(Double fwdymjsum) {
        this.fwdymjsum = fwdymjsum;
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

    public Integer getBiz() {
        return biz;
    }

    public void setBiz(Integer biz) {
        this.biz = biz;
    }

    public String getZwrzjh() {
        return zwrzjh;
    }

    public void setZwrzjh(String zwrzjh) {
        this.zwrzjh = zwrzjh;
    }

    public Integer getZwrzjzl() {
        return zwrzjzl;
    }

    public void setZwrzjzl(Integer zwrzjzl) {
        this.zwrzjzl = zwrzjzl;
    }

    public Integer getDyts() {
        return dyts;
    }

    public void setDyts(Integer dyts) {
        this.dyts = dyts;
    }

    public Double getZgzqe() {
        return zgzqe;
    }

    public void setZgzqe(Double zgzqe) {
        this.zgzqe = zgzqe;
    }

    public Integer getJzzr() {
        return jzzr;
    }

    public void setJzzr(Integer jzzr) {
        this.jzzr = jzzr;
    }

    public String getDyfw() {
        return dyfw;
    }

    public void setDyfw(String dyfw) {
        this.dyfw = dyfw;
    }


    public Integer getJedw() {
        return jedw;
    }

    public void setJedw(Integer jedw) {
        this.jedw = jedw;
    }

    public Integer getDyjelx() {
        return dyjelx;
    }

    public void setDyjelx(Integer dyjelx) {
        this.dyjelx = dyjelx;
    }

    @Override
    public String toString() {
        return "BdcDyaqDO{" +
                "qlid='" + qlid + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", zwr='" + zwr + '\'' +
                ", dyfs=" + dyfs +
                ", zjjzwzl='" + zjjzwzl + '\'' +
                ", zjjzwdyfw='" + zjjzwdyfw + '\'' +
                ", zwlxqssj=" + zwlxqssj +
                ", zwlxjssj=" + zwlxjssj +
                ", zgzqqdss='" + zgzqqdss + '\'' +
                ", zgzqqdse=" + zgzqqdse +
                ", zxdyywh='" + zxdyywh + '\'' +
                ", zxdyyy='" + zxdyyy + '\'' +
                ", zxdydjsj=" + zxdydjsj +
                ", zxdydbr='" + zxdydbr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", dbfw='" + dbfw + '\'' +
                ", dysw=" + dysw +
                ", dkfs='" + dkfs + '\'' +
                ", bdbzzqse=" + bdbzzqse +
                ", jezl=" + jezl +
                ", fwpgjg=" + fwpgjg +
                ", tdpgjg=" + tdpgjg +
                ", fwdyjg=" + fwdyjg +
                ", tddyjg=" + tddyjg +
                ", tddymj=" + tddymj +
                ", fwdymj=" + fwdymj +
                ", sfgtdb=" + sfgtdb +
                ", tddymjsum=" + tddymjsum +
                ", fwdymjsum=" + fwdymjsum +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", dybdclx=" + dybdclx +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", dyzxsqr='" + dyzxsqr + '\'' +
                ", dyzxsqrzjh='" + dyzxsqrzjh + '\'' +
                ", biz=" + biz +
                ", zwrzjh='" + zwrzjh + '\'' +
                ", zwrzjzl=" + zwrzjzl +
                ", dyts=" + dyts +
                ", zgzqe=" + zgzqe +
                ", jzzr=" + jzzr +
                ", dyfw='" + dyfw + '\'' +
                ", jedw=" + jedw +
                ", dyjelx=" + dyjelx +
                '}';
    }
}
