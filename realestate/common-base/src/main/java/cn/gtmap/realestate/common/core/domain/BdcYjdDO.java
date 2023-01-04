package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单实体类
 */
@Table(name = "BDC_YJD")
@ApiModel(value = "BdcYjd", description = "不动产移交单")
public class BdcYjdDO {
    @ApiModelProperty(value = "移交单ID")
    @Id
    String yjdid;
    @ApiModelProperty(value = "移交单编号")
    String yjdbh;
    @ApiModelProperty(value = "移交时间")
    Date yjsj;
    @ApiModelProperty(value = "移交人")
    String yjr;
    @ApiModelProperty(value = "接收人")
    String jsr;
    @ApiModelProperty(value = "移交状态")
    Integer yjzt;


    public Integer getYjzt() {
        return yjzt;
    }

    public void setYjzt(Integer yjzt) {
        this.yjzt = yjzt;
    }

    public String getYjdid() {
        return yjdid;
    }

    public void setYjdid(String yjdid) {
        this.yjdid = yjdid;
    }

    public String getYjdbh() {
        return yjdbh;
    }

    public void setYjdbh(String yjdbh) {
        this.yjdbh = yjdbh;
    }

    public Date getYjsj() {
        return yjsj;
    }

    public void setYjsj(Date yjsj) {
        this.yjsj = yjsj;
    }

    public String getYjr() {
        return yjr;
    }

    public void setYjr(String yjr) {
        this.yjr = yjr;
    }

    public String getJsr() {
        return jsr;
    }

    public void setJsr(String jsr) {
        this.jsr = jsr;
    }

    @Override
    public String toString() {
        return "BdcYjdDO{" +
                "yjdid='" + yjdid + '\'' +
                ", yjdbh='" + yjdbh + '\'' +
                ", yjsj=" + yjsj +
                ", yjr='" + yjr + '\'' +
                ", jsr='" + jsr + '\'' +
                ", yjzt=" + yjzt +
                '}';
    }
}
