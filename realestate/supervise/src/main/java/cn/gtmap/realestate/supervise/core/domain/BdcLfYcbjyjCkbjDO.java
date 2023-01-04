package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/23
 * @description 异常办件预警-超快办件表
 */
@Table(name = "BDC_LF_YCBJYJ_CKBJ")
public class BdcLfYcbjyjCkbjDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;

    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "流程开始时间")
    private String lckssj;

    @ApiModelProperty(value = "流程结束时间")
    private String lcjssj;

    @ApiModelProperty(value = "办件时长")
    private Double bjsc;

    @ApiModelProperty(value = "超快办件时长标准")
    private Double ckbjscbz;

    @ApiModelProperty(value = "流程启动者")
    private String lcqdz;

    @ApiModelProperty(value = "数据同步时间")
    private Date sjtbsj;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "受理人员")
    private String slr;

    @ApiModelProperty(value = "审核人")
    private String shry;

    @ApiModelProperty(value = "审核人ID")
    private String shrid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "审核时间")
    private Date shsj;

    @ApiModelProperty(value = "审核人ID")
    private String shyj;

    @ApiModelProperty(value = "审核状态")
    private Integer shzt;


    public String getShry() {
        return shry;
    }

    public void setShry(String shry) {
        this.shry = shry;
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid;
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public Integer getShzt() {
        return shzt;
    }

    public void setShzt(Integer shzt) {
        this.shzt = shzt;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getLckssj() {
        return lckssj;
    }

    public void setLckssj(String lckssj) {
        this.lckssj = lckssj;
    }

    public String getLcjssj() {
        return lcjssj;
    }

    public void setLcjssj(String lcjssj) {
        this.lcjssj = lcjssj;
    }

    public String getLcqdz() {
        return lcqdz;
    }

    public void setLcqdz(String lcqdz) {
        this.lcqdz = lcqdz;
    }

    public Date getSjtbsj() {
        return sjtbsj;
    }

    public void setSjtbsj(Date sjtbsj) {
        this.sjtbsj = sjtbsj;
    }

    public Double getBjsc() {
        return bjsc;
    }

    public void setBjsc(Double bjsc) {
        this.bjsc = bjsc;
    }

    public Double getCkbjscbz() {
        return ckbjscbz;
    }

    public void setCkbjscbz(Double ckbjscbz) {
        this.ckbjscbz = ckbjscbz;
    }

    @Override
    public String toString() {
        return "BdcLfYcbjyjCkbjDO{" +
                "id='" + id + '\'' +
                ", gzldymc='" + gzldymc + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", lckssj='" + lckssj + '\'' +
                ", lcjssj='" + lcjssj + '\'' +
                ", bjsc=" + bjsc +
                ", ckbjscbz=" + ckbjscbz +
                ", lcqdz='" + lcqdz + '\'' +
                ", sjtbsj=" + sjtbsj +
                ", slbh='" + slbh + '\'' +
                ", slr='" + slr + '\'' +
                ", shry='" + shry + '\'' +
                ", shrid='" + shrid + '\'' +
                ", shsj=" + shsj +
                ", shyj='" + shyj + '\'' +
                ", shzt=" + shzt +
                '}';
    }
}
