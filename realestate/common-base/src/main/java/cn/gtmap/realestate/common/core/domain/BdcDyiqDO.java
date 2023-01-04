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
  * @description 地役权
  */
@Table(
        name = "BDC_DYIQ"
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = BdcDyiqDO.class)
@ApiModel(value = "BdcDyiqDO", description = "不动产地役权信息")
public class BdcDyiqDO implements BdcQl {
    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
     @ApiModelProperty(value = "地役权内容")
    private String dyqnr;
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
     @ApiModelProperty(value = "需役地坐落")
    private String xydzl;
     @ApiModelProperty(value = "权利起始时间",example = "2018-10-01 12:18:48")
    private Date qlqssj;
     @ApiModelProperty(value = "权利结束时间",example = "2018-10-01 12:18:48")
    private Date qljssj;
     @ApiModelProperty(value = "需役地不动产单元号")
    private String xydbdcdyh;
     @ApiModelProperty(value = "备注")
    private String bz;
     @ApiModelProperty(value = "注销地役业务号")
    private String zxdyywh;
     @ApiModelProperty(value = "注销地役原因")
    private String zxdyyy;
     @ApiModelProperty(value = "注销地役登簿人")
    private String zxdydbr;
     @ApiModelProperty(value = "注销地役登记时间",example = "2018-10-01 12:18:48")
    private Date zxdydjsj;
     @ApiModelProperty(value = "供役地不动产单元号")
    private String gydbdcdyh;
     @ApiModelProperty(value = "供役地不动产单元编号")
    private String gydbdcdywybh;
     @ApiModelProperty(value = "需役地不动产单元编号")
    private String xydbdcdywybh;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getDyqnr() {
        return dyqnr;
    }

    public void setDyqnr(String dyqnr) {
        this.dyqnr = dyqnr;
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

    public String getXydzl() {
        return xydzl;
    }

    public void setXydzl(String xydzl) {
        this.xydzl = xydzl;
    }

    public Date getQlqssj() {
        return qlqssj;
    }

    public void setQlqssj(Date qlqssj) {
        this.qlqssj = qlqssj;
    }

    public Date getQljssj() {
        return qljssj;
    }

    public void setQljssj(Date qljssj) {
        this.qljssj = qljssj;
    }

    public String getXydbdcdyh() {
        return xydbdcdyh;
    }

    public void setXydbdcdyh(String xydbdcdyh) {
        this.xydbdcdyh = xydbdcdyh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public String getZxdydbr() {
        return zxdydbr;
    }

    public void setZxdydbr(String zxdydbr) {
        this.zxdydbr = zxdydbr;
    }

    public Date getZxdydjsj() {
        return zxdydjsj;
    }

    public void setZxdydjsj(Date zxdydjsj) {
        this.zxdydjsj = zxdydjsj;
    }

    public String getGydbdcdyh() {
        return gydbdcdyh;
    }

    public void setGydbdcdyh(String gydbdcdyh) {
        this.gydbdcdyh = gydbdcdyh;
    }


     public String getGydbdcdywybh() {
         return gydbdcdywybh;
     }

     public void setGydbdcdywybh(String gydbdcdywybh) {
         this.gydbdcdywybh = gydbdcdywybh;
     }

     public String getXydbdcdywybh() {
         return xydbdcdywybh;
     }

     public void setXydbdcdywybh(String xydbdcdywybh) {
         this.xydbdcdywybh = xydbdcdywybh;
     }

     @Override
     public String toString() {
         return "BdcDyiqDO{" +
                 "qlid='" + qlid + '\'' +
                 ", dyqnr='" + dyqnr + '\'' +
                 ", slbh='" + slbh + '\'' +
                 ", djlx=" + djlx +
                 ", djyy='" + djyy + '\'' +
                 ", xmid='" + xmid + '\'' +
                 ", gyqk='" + gyqk + '\'' +
                 ", djjg='" + djjg + '\'' +
                 ", djsj=" + djsj +
                 ", dbr='" + dbr + '\'' +
                 ", fj='" + fj + '\'' +
                 ", qszt=" + qszt +
                 ", xydzl='" + xydzl + '\'' +
                 ", qlqssj=" + qlqssj +
                 ", qljssj=" + qljssj +
                 ", xydbdcdyh='" + xydbdcdyh + '\'' +
                 ", bz='" + bz + '\'' +
                 ", zxdyywh='" + zxdyywh + '\'' +
                 ", zxdyyy='" + zxdyyy + '\'' +
                 ", zxdydbr='" + zxdydbr + '\'' +
                 ", zxdydjsj=" + zxdydjsj +
                 ", gydbdcdyh='" + gydbdcdyh + '\'' +
                 ", gydbdcdywybh='" + gydbdcdywybh + '\'' +
                 ", xydbdcdywybh='" + xydbdcdywybh + '\'' +
                 '}';
     }
 }
