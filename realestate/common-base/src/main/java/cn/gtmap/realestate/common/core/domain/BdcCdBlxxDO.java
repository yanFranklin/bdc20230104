package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: realestate
 * @description: 首套房查询时需要补录的数据，仅用于查询打印，不作其他流程数据
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2020-09-23 16:52
 **/
@Table(name = "BDC_CDBLXX")
@ApiModel(value = "BdcCdBlxxDO", description = "不动产补录信息")
public class BdcCdBlxxDO {

    @Id
    @ApiModelProperty(value = "补录信息id")
    private String blxxid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "申请人名称")
    private String sqrmc;
    @ApiModelProperty(value = "申请人证件种类")
    private Integer sqrzjzl;
    @ApiModelProperty(value = "申请人证件号")
    private String sqrzjh;
    @ApiModelProperty(value = "共有人")
    private String gyr;
    @ApiModelProperty(value = "房屋坐落")
    private String fwzl;
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;
    @ApiModelProperty(value = "取得方式")
    private Integer qdfs;
    @ApiModelProperty(value = "登记时间")
    private Date djsj;
    @ApiModelProperty(value = "转移时间")
    private Date zysj;
    @ApiModelProperty(value = "备注")
    private String bz;

    public String getBlxxid() {
        return blxxid;
    }

    public void setBlxxid(String blxxid) {
        this.blxxid = blxxid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSqrmc() {
        return sqrmc;
    }

    public void setSqrmc(String sqrmc) {
        this.sqrmc = sqrmc;
    }

    public Integer getSqrzjzl() {
        return sqrzjzl;
    }

    public void setSqrzjzl(Integer sqrzjzl) {
        this.sqrzjzl = sqrzjzl;
    }

    public String getSqrzjh() {
        return sqrzjh;
    }

    public void setSqrzjh(String sqrzjh) {
        this.sqrzjh = sqrzjh;
    }

    public String getFwzl() {
        return fwzl;
    }

    public void setFwzl(String fwzl) {
        this.fwzl = fwzl;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Integer getQdfs() {
        return qdfs;
    }

    public void setQdfs(Integer qdfs) {
        this.qdfs = qdfs;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    public Date getZysj() {
        return zysj;
    }

    public void setZysj(Date zysj) {
        this.zysj = zysj;
    }

    @Override
    public String toString() {
        return "BdcCdBlxxDO{" +
                "blxxid='" + blxxid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", sqrmc='" + sqrmc + '\'' +
                ", sqrzjzl=" + sqrzjzl +
                ", sqrzjh='" + sqrzjh + '\'' +
                ", gyr='" + gyr + '\'' +
                ", fwzl='" + fwzl + '\'' +
                ", jzmj=" + jzmj +
                ", qdfs=" + qdfs +
                ", djsj=" + djsj +
                ", zysj=" + zysj +
                ", bz='" + bz + '\'' +
                '}';
    }
}
