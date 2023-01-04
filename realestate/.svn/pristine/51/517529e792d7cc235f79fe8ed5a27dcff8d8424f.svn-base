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
 * @description 构（建）筑物所有权
 */
@Table(
        name = "BDC_GJZWSYQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcGjzwsyqDO.class)
@ApiModel(value = "BdcGjzwsyqDO", description = "不动产构（建）筑物所有权信息")
public class BdcGjzwsyqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "土地/海域使用权人")
    private String tdhysyqr;
    @ApiModelProperty(value = "土地/海域使用面积")
    private Double tdhysymj;
    @ApiModelProperty(value = "土地/海域使用起始时间", example = "2018-10-01 12:18:48")
    private Date tdhysyqssj;
    @ApiModelProperty(value = "土地/海域使用结束时间", example = "2018-10-01 12:18:48")
    private Date tdhysyjssj;
    @Zd(tableClass = BdcZdGjzwlxDO.class)
    @ApiModelProperty(value = "构（建）筑物类型")
    private Integer gjzwlx;
    @ApiModelProperty(value = "构（建）筑物规划用途")
    private String gjzwghyt;
    @ApiModelProperty(value = "构(建)筑物面积")
    private Double gjzwmj;
    @ApiModelProperty(value = "竣工时间", example = "2018-10-01")
    private String jgsj;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "权利类型")
    private String qllx;
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
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;

    @Override
    public String toString() {
        return "BdcGjzwsyqDO{" +
                "qlid='" + qlid + '\'' +
                ", tdhysyqr='" + tdhysyqr + '\'' +
                ", tdhysymj=" + tdhysymj +
                ", tdhysyqssj=" + tdhysyqssj +
                ", tdhysyjssj=" + tdhysyjssj +
                ", gjzwlx=" + gjzwlx +
                ", gjzwghyt='" + gjzwghyt + '\'' +
                ", gjzwmj=" + gjzwmj +
                ", jgsj='" + jgsj + '\'' +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx='" + qllx + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djjg='" + djjg + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr=" + zxdbr +
                ", zxdjsj='" + zxdjsj + '\'' +
                '}';
    }

    public String getQlid() {
        return qlid;
    }

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

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getTdhysyqr() {
        return tdhysyqr;
    }

    public void setTdhysyqr(String tdhysyqr) {
        this.tdhysyqr = tdhysyqr;
    }

    public Double getTdhysymj() {
        return tdhysymj;
    }

    public void setTdhysymj(Double tdhysymj) {
        this.tdhysymj = tdhysymj;
    }

    public Date getTdhysyqssj() {
        return tdhysyqssj;
    }

    public void setTdhysyqssj(Date tdhysyqssj) {
        this.tdhysyqssj = tdhysyqssj;
    }

    public Date getTdhysyjssj() {
        return tdhysyjssj;
    }

    public void setTdhysyjssj(Date tdhysyjssj) {
        this.tdhysyjssj = tdhysyjssj;
    }

    public Integer getGjzwlx() {
        return gjzwlx;
    }

    public void setGjzwlx(Integer gjzwlx) {
        this.gjzwlx = gjzwlx;
    }

    public String getGjzwghyt() {
        return gjzwghyt;
    }

    public void setGjzwghyt(String gjzwghyt) {
        this.gjzwghyt = gjzwghyt;
    }

    public Double getGjzwmj() {
        return gjzwmj;
    }

    public void setGjzwmj(Double gjzwmj) {
        this.gjzwmj = gjzwmj;
    }

    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
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

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

}
