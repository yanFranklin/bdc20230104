package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 共享外网申请项目
 *
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/3/15 10:30
 */
@Table(name = "GX_WW_SQXM")
@ApiModel(value = "GxWwSqxmDO", description = "共享外网申请项目")
public class GxWwSqxmDO implements Serializable {
    @Id
    @ApiModelProperty(value = "项目 id")
    private String xmid;

    @ApiModelProperty(value = "申请受理编号")
    private String sqslbh;

    @ApiModelProperty(value = "不动产登记受理编号")
    private String bdcdjslbh;

    @ApiModelProperty(value = "申请时间")
    private Date sqsj;

    @ApiModelProperty(value = "申请人名称")
    private String sqrmc;

    @ApiModelProperty(value = "申请人 id")
    private String sqrid;

    @ApiModelProperty(value = "数据推送时间")
    private Date tssj;

    @ApiModelProperty(value = "数据更新时间")
    private Date gxsj;

    @ApiModelProperty(value = "登记受理状态")
    private String djzt;

    @ApiModelProperty(value = "不予受理原因")
    private String byslyy;

    @ApiModelProperty(value = "申请类型")
    private String sqlx;

    @ApiModelProperty(value = "单位代码")
    private String dwdm;

    public String getDwdm() {
        return dwdm;
    }

    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqslbh() {
        return sqslbh;
    }

    public void setSqslbh(String sqslbh) {
        this.sqslbh = sqslbh;
    }

    public String getBdcdjslbh() {
        return bdcdjslbh;
    }

    public void setBdcdjslbh(String bdcdjslbh) {
        this.bdcdjslbh = bdcdjslbh;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public Date getTssj() {
        return tssj;
    }

    public void setTssj(Date tssj) {
        this.tssj = tssj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getDjzt() {
        return djzt;
    }

    public void setDjzt(String djzt) {
        this.djzt = djzt;
    }

    public String getByslyy() {
        return byslyy;
    }

    public void setByslyy(String byslyy) {
        this.byslyy = byslyy;
    }

    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    @Override
    public String toString() {
        return "GxWwSqxmDO{" +
                "xmid='" + xmid + '\'' +
                ", sqslbh='" + sqslbh + '\'' +
                ", bdcdjslbh='" + bdcdjslbh + '\'' +
                ", sqsj=" + sqsj +
                ", sqrmc='" + sqrmc + '\'' +
                ", sqrid='" + sqrid + '\'' +
                ", tssj=" + tssj +
                ", gxsj=" + gxsj +
                ", djzt='" + djzt + '\'' +
                ", byslyy='" + byslyy + '\'' +
                ", sqlx='" + sqlx + '\'' +
                ", dwdm='" + dwdm + '\'' +
                '}';
    }
}
