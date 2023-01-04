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
 * @description 房屋租赁
 */
@Table(name = "BDC_FWZL")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = BdcFwzlDO.class)
@ApiModel(value = "BdcFwzlDO", description = "不动产房屋租赁信息")
public class BdcFwzlDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "注销房屋租赁业务号")
    private String zxfwzlywh;
    @ApiModelProperty(value = "注销房屋租赁登记时间",example = "2018-10-01 12:18:48")
    private Date zxfwzldjsj;
    @ApiModelProperty(value = "注销房屋租赁登簿人")
    private String zxfwzldbr;
    @ApiModelProperty(value = "注销房屋租赁原因")
    private String zxfwzlyy;
    @ApiModelProperty(value = "租赁起始时间",example = "2018-10-01 12:18:48")
    private Date zlqssj;
    @ApiModelProperty(value = "租赁结束时间",example = "2018-10-01 12:18:48")
    private Date zljssj;
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
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
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
    @ApiModelProperty(value = "租赁协议送达时间",example = "2018-10-01 12:18:48")
    private Date zlxysdsj;
    @ApiModelProperty(value = "租赁部位")
    private String zlbw;
    @ApiModelProperty(value = "租赁方式")
    private Integer zlfs;
    @ApiModelProperty(value = "租赁类型")
    private Integer zllx;
    @ApiModelProperty(value = "月租金")
    private Double yzj;
    @ApiModelProperty(value = "房屋租赁备案证明号")
    private String fwzlbazmh;
    @ApiModelProperty(value = "登记机构")
    private String djjg;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getZxfwzlywh() {
        return zxfwzlywh;
    }

    public void setZxfwzlywh(String zxfwzlywh) {
        this.zxfwzlywh = zxfwzlywh;
    }

    public Date getZxfwzldjsj() {
        return zxfwzldjsj;
    }

    public void setZxfwzldjsj(Date zxfwzldjsj) {
        this.zxfwzldjsj = zxfwzldjsj;
    }

    public String getZxfwzldbr() {
        return zxfwzldbr;
    }

    public void setZxfwzldbr(String zxfwzldbr) {
        this.zxfwzldbr = zxfwzldbr;
    }

    public String getZxfwzlyy() {
        return zxfwzlyy;
    }

    public void setZxfwzlyy(String zxfwzlyy) {
        this.zxfwzlyy = zxfwzlyy;
    }

    public Date getZlqssj() {
        return zlqssj;
    }

    public void setZlqssj(Date zlqssj) {
        this.zlqssj = zlqssj;
    }

    public Date getZljssj() {
        return zljssj;
    }

    public void setZljssj(Date zljssj) {
        this.zljssj = zljssj;
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

    public Date getZlxysdsj() {
        return zlxysdsj;
    }

    public void setZlxysdsj(Date zlxysdsj) {
        this.zlxysdsj = zlxysdsj;
    }

    public String getZlbw() {
        return zlbw;
    }

    public void setZlbw(String zlbw) {
        this.zlbw = zlbw;
    }

    public Integer getZlfs() {
        return zlfs;
    }

    public void setZlfs(Integer zlfs) {
        this.zlfs = zlfs;
    }

    public Integer getZllx() {
        return zllx;
    }

    public void setZllx(Integer zllx) {
        this.zllx = zllx;
    }

    public Double getYzj() {
        return yzj;
    }

    public void setYzj(Double yzj) {
        this.yzj = yzj;
    }

    public String getFwzlbazmh() {
        return fwzlbazmh;
    }

    public void setFwzlbazmh(String fwzlbazmh) {
        this.fwzlbazmh = fwzlbazmh;
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
    public String toString() {
        return "BdcFwzlDO{" +
                "qlid='" + qlid + '\'' +
                ", zxfwzlywh='" + zxfwzlywh + '\'' +
                ", zxfwzldjsj=" + zxfwzldjsj +
                ", zxfwzldbr='" + zxfwzldbr + '\'' +
                ", zxfwzlyy='" + zxfwzlyy + '\'' +
                ", zlqssj=" + zlqssj +
                ", zljssj=" + zljssj +
                ", slbh='" + slbh + '\'' +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", qllx=" + qllx +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", bz='" + bz + '\'' +
                ", zlxysdsj=" + zlxysdsj +
                ", zlbw='" + zlbw + '\'' +
                ", zlfs=" + zlfs +
                ", zllx=" + zllx +
                ", yzj=" + yzj +
                ", fwzlbazmh='" + fwzlbazmh + '\'' +
                '}';
    }
}
