package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/8/6.
 * @description 农用地经营权
 */
@Table(
        name = "BDC_NYDJYQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = BdcNydjyqDO.class)
@ApiModel(value = "BdcNydjyqDO", description = "农用地经营权")
public class BdcNydjyqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "经营用途")
    private String jyyt;
    @ApiModelProperty(value = "经营（合同）面积")
    private Double jymj;
    @ApiModelProperty(value = "转包价格")
    private Double zbjg;
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
    @ApiModelProperty(value = "登记时间", example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "附记")
    private String fj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "经营起始时间", example = "2018-10-01 12:18:48")
    private Date cbqssj;
    @ApiModelProperty(value = "经营结束时间", example = "2018-10-01 12:18:48")
    private Date cbjssj;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "注销原因")
    private String zxyy;
    @ApiModelProperty(value = "注销登簿人")
    private String zxdbr;
    @ApiModelProperty(value = "注销登记时间")
    private Date zxdjsj;
    @ApiModelProperty(value = "地块编码")
    private String dkbm;
    @ApiModelProperty(value = "承包方")
    private String cbf;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @Override
    public String getQlid() {
        return qlid;
    }

    @Override
    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getJyyt() {
        return jyyt;
    }

    public void setJyyt(String jyyt) {
        this.jyyt = jyyt;
    }

    public Double getJymj() {
        return jymj;
    }

    public void setJymj(Double jymj) {
        this.jymj = jymj;
    }

    public Double getZbjg() {
        return zbjg;
    }

    public void setZbjg(Double zbjg) {
        this.zbjg = zbjg;
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
        this.gyqk =gyqk;

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
    public String getDjjg() {
        return djjg;
    }

    @Override
    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
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

    public String getDkbm() {
        return dkbm;
    }

    public void setDkbm(String dkbm) {
        this.dkbm = dkbm;
    }

    public String getCbf() {
        return cbf;
    }

    public void setCbf(String cbf) {
        this.cbf = cbf;
    }


    @Override
    public String toString() {
        return "BdcNydjyqDO{" +
                "qlid='" + qlid + '\'' +
                ", jyyt='" + jyyt + '\'' +
                ", jymj=" + jymj +
                ", zbjg=" + zbjg +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", xmid='" + xmid + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", djjg='" + djjg + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", cbqssj=" + cbqssj +
                ", cbjssj=" + cbjssj +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                ", zxyy='" + zxyy + '\'' +
                ", zxdbr='" + zxdbr + '\'' +
                ", zxdjsj=" + zxdjsj +
                ", dkbm='" + dkbm + '\'' +
                ", cbf='" + cbf + '\'' +
                '}';
    }
}
