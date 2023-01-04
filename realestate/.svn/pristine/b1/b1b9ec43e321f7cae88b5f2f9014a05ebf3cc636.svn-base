package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 发票号使用明细
 */
@Table(name = "BDC_FPHSYMX")
@ApiModel(value = "BdcFphSymxDO",description = "发票号使用明细")
public class BdcFphSymxDO  implements Serializable {
    private static final long serialVersionUID = -8712424636552318599L;
    @Id
    @ApiModelProperty(value = "发票号使用明细id")
    private String fphsymxid;
    @ApiModelProperty(value = "发票号id")
    private String fphid;
    @ApiModelProperty(value = "发票号使用情况")
    private Integer syqk;
    @ApiModelProperty(value = "使用人")
    private String syr;
    @ApiModelProperty(value = "使用人ID")
    private String syrid;
    @ApiModelProperty(value = "使用时间",example = "2018-10-01 12:18:48")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sysj;
    @ApiModelProperty(value = "使用原因")
    private String syyy;

    public String getFphsymxid() {
        return fphsymxid;
    }

    public void setFphsymxid(String fphsymxid) {
        this.fphsymxid = fphsymxid;
    }

    public String getFphid() {
        return fphid;
    }

    public void setFphid(String fphid) {
        this.fphid = fphid;
    }

    public Integer getSyqk() {
        return syqk;
    }

    public void setSyqk(Integer syqk) {
        this.syqk = syqk;
    }

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public String getSyrid() {
        return syrid;
    }

    public void setSyrid(String syrid) {
        this.syrid = syrid;
    }

    public Date getSysj() {
        return sysj;
    }

    public void setSysj(Date sysj) {
        this.sysj = sysj;
    }

    public String getSyyy() {
        return syyy;
    }

    public void setSyyy(String syyy) {
        this.syyy = syyy;
    }

    @Override
    public String toString() {
        return "BdcFphSymxDO{" +
                "fphsymxid='" + fphsymxid + '\'' +
                ", fphid='" + fphid + '\'' +
                ", syqk=" + syqk +
                ", syr='" + syr + '\'' +
                ", syrid='" + syrid + '\'' +
                ", sysj=" + sysj +
                ", syyy='" + syyy + '\'' +
                '}';
    }
}
