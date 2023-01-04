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
 * @description 建设用地使用权、宅基地使用权
 */
@Table(
        name = "BDC_JSYDSYQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcJsydsyqDO.class)
@ApiModel(value = "BdcJsydsyqDO", description = "建设用地使用权、宅基地使用权")
public class BdcJsydsyqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "取得价格")
    private Double qdjg;
    @ApiModelProperty(value = "使用权起始时间", example = "2018-10-01 12:18:48")
    private Date syqqssj;
    @ApiModelProperty(value = "使用权结束时间", example = "2018-10-01 12:18:48")
    private Date syqjssj;
    @ApiModelProperty(value = "使用权面积")
    private Double syqmj;
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
    @ApiModelProperty(value = "使用权起始时间2", example = "2018-10-01 12:18:48")
    private Date syqqssj2;
    @ApiModelProperty(value = "使用权结束时间2", example = "2018-10-01 12:18:48")
    private Date syqjssj2;
    @ApiModelProperty(value = "使用权起始时间3", example = "2018-10-01 12:18:48")
    private Date syqqssj3;
    @ApiModelProperty(value = "使用权结束时间3", example = "2018-10-01 12:18:48")
    private Date syqjssj3;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;
    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;
    @ApiModelProperty(value = "转让价格")
    private Double zrjg;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "土地所有权人")
    private String tdsyqr;
    @Zd(tableClass = BdcZdQlsdfsDO.class)
    @ApiModelProperty(value = "权利设定方式")
    private Integer qlsdfs;
    @Zd(tableClass = BdcZdQlxzDO.class)
    @ApiModelProperty(value = "权利性质")
    private Integer qlxz;
    @ApiModelProperty(value = "注销业务号")
    private String zxywh;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;
    @ApiModelProperty(value = "金额单位")
    private Integer jedw;

    @Override
    public String toString() {
        return "BdcJsydsyqDO{" +
                "qlid='" + qlid + '\'' +
                ", qdjg=" + qdjg +
                ", syqqssj=" + syqqssj +
                ", syqjssj=" + syqjssj +
                ", syqmj=" + syqmj +
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
                ", syqqssj2=" + syqqssj2 +
                ", syqjssj2=" + syqjssj2 +
                ", syqqssj3=" + syqqssj3 +
                ", syqjssj3=" + syqjssj3 +
                ", bz='" + bz + '\'' +
                ", dytdmj=" + dytdmj +
                ", fttdmj=" + fttdmj +
                ", zrjg=" + zrjg +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", tdsyqr='" + tdsyqr + '\'' +
                ", qlsdfs=" + qlsdfs +
                ", qlxz=" + qlxz +
                ", zxywh='" + zxywh + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr='" + zxdbr + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", jedw=" + jedw +
                '}';
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

    public String getQlid() {
        return qlid;
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

    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    public Date getSyqqssj() {
        return syqqssj;
    }

    public void setSyqqssj(Date syqqssj) {
        this.syqqssj = syqqssj;
    }

    public Date getSyqjssj() {
        return syqjssj;
    }

    public void setSyqjssj(Date syqjssj) {
        this.syqjssj = syqjssj;
    }

    public Double getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(Double syqmj) {
        this.syqmj = syqmj;
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

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
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

    public Date getSyqqssj2() {
        return syqqssj2;
    }

    public void setSyqqssj2(Date syqqssj2) {
        this.syqqssj2 = syqqssj2;
    }

    public Date getSyqjssj2() {
        return syqjssj2;
    }

    public void setSyqjssj2(Date syqjssj2) {
        this.syqjssj2 = syqjssj2;
    }

    public Date getSyqqssj3() {
        return syqqssj3;
    }

    public void setSyqqssj3(Date syqqssj3) {
        this.syqqssj3 = syqqssj3;
    }

    public Date getSyqjssj3() {
        return syqjssj3;
    }

    public void setSyqjssj3(Date syqjssj3) {
        this.syqjssj3 = syqjssj3;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public Double getZrjg() {
        return zrjg;
    }

    public void setZrjg(Double zrjg) {
        this.zrjg = zrjg;
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

    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    public Integer getQlsdfs() {
        return qlsdfs;
    }

    public void setQlsdfs(Integer qlsdfs) {
        this.qlsdfs = qlsdfs;
    }

    public Integer getQlxz() {
        return qlxz;
    }

    public void setQlxz(Integer qlxz) {
        this.qlxz = qlxz;
    }

    public Integer getJedw() {
        return jedw;
    }

    public void setJedw(Integer jedw) {
        this.jedw = jedw;
    }
}
