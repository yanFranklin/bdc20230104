package cn.gtmap.realestate.supervise.core.domain;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhuyong@gmail.com">zhuyong</a>
 * @version V1.0, 2021/09/13
 * @description 职责权能监管-授权信息管理-授权人员
 */
@Table(name = "BDC_LF_SQXX_SQRY")
public class BdcLfSqxxSqryDO {
    @Id
    @ApiModelProperty(value = "主键ID")
    private String id;

    @ApiModelProperty(value = "授权信息ID")
    private String sqxxid;

    @ApiModelProperty(value = "授权人员")
    private String sqry;

    @ApiModelProperty(value = "授权人员ID")
    private String sqryid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "授权时间")
    private Date sqsj;

    @ApiModelProperty(value = "部门名称")
    private String bmmc;

    @ApiModelProperty(value = "部门id")
    private String bmid;

    @ApiModelProperty(value = "科室")
    private String ks;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public String getSqry() {
        return sqry;
    }

    public void setSqry(String sqry) {
        this.sqry = sqry;
    }

    public String getSqryid() {
        return sqryid;
    }

    public void setSqryid(String sqryid) {
        this.sqryid = sqryid;
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid;
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks;
    }

    @Override
    public String toString() {
        return "BdcLfSqxxSqryDO{" +
                "id='" + id + '\'' +
                ", sqxxid='" + sqxxid + '\'' +
                ", sqry='" + sqry + '\'' +
                ", sqryid='" + sqryid + '\'' +
                ", sqsj=" + sqsj +
                ", bmmc='" + bmmc + '\'' +
                ", bmid='" + bmid + '\'' +
                ", ks='" + ks + '\'' +
                '}';
    }
}
