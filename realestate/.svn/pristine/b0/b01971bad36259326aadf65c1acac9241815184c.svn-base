package cn.gtmap.realestate.common.core.domain;

import cn.gtmap.realestate.common.core.annotations.Zd;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2018/10/31
 * @description 不动产异议
 */
@Table(name = "BDC_YY")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz",defaultImpl = BdcYyDO.class)
@ApiModel(value = "BdcYyDO",description = "不动产异议")
public class BdcYyDO implements BdcQl {

    @Id
    @ApiModelProperty(value = "权利id")
    private String qlid;
    @ApiModelProperty(value = "异议事项")
    private String yysx;
    @ApiModelProperty(value = "注销异议业务号")
    private String zxyyywh;
    @ApiModelProperty(value = "注销异议原因")
    private String zxyyyy;
    @ApiModelProperty(value = "注销异议登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date zxyydjsj;
    @ApiModelProperty(value = "注销异议登簿人")
    private String zxyydbr;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
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
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产单元编号")
    private String bdcdywybh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "送达时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date sdsj;
    @ApiModelProperty(value = "异议登记开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date yydjksrq;
    @ApiModelProperty(value = "异议登记结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date yydjjsrq;

    public Date getSdsj() {
        return sdsj;
    }

    public void setSdsj(Date sdsj) {
        this.sdsj = sdsj;
    }

    public Date getYydjksrq() {
        return yydjksrq;
    }

    public void setYydjksrq(Date yydjksrq) {
        this.yydjksrq = yydjksrq;
    }

    public Date getYydjjsrq() {
        return yydjjsrq;
    }

    public void setYydjjsrq(Date yydjjsrq) {
        this.yydjjsrq = yydjjsrq;
    }

    @Override
    public String getQlid() {
        return qlid;
    }

    @Override
    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public String getYysx() {
        return yysx;
    }

    public void setYysx(String yysx) {
        this.yysx = yysx;
    }

    public String getZxyyywh() {
        return zxyyywh;
    }

    public void setZxyyywh(String zxyyywh) {
        this.zxyyywh = zxyyywh;
    }

    public String getZxyyyy() {
        return zxyyyy;
    }

    public void setZxyyyy(String zxyyyy) {
        this.zxyyyy = zxyyyy;
    }

    public Date getZxyydjsj() {
        return zxyydjsj;
    }

    public void setZxyydjsj(Date zxyydjsj) {
        this.zxyydjsj = zxyydjsj;
    }

    public String getZxyydbr() {
        return zxyydbr;
    }

    public void setZxyydbr(String zxyydbr) {
        this.zxyydbr = zxyydbr;
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

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
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

    @Override
    public String toString() {
        return "BdcYyDO{" +
                "qlid='" + qlid + '\'' +
                ", yysx='" + yysx + '\'' +
                ", zxyyywh='" + zxyyywh + '\'' +
                ", zxyyyy='" + zxyyyy + '\'' +
                ", zxyydjsj=" + zxyydjsj +
                ", zxyydbr='" + zxyydbr + '\'' +
                ", slbh='" + slbh + '\'' +
                ", xmid='" + xmid + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", djjg='" + djjg + '\'' +
                ", fj='" + fj + '\'' +
                ", qszt=" + qszt +
                ", bz='" + bz + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", zl='" + zl + '\'' +
                '}';
    }
}
