package cn.gtmap.realestate.common.core.vo.config.ui;

import cn.gtmap.realestate.common.core.domain.BdcFphDO;
import cn.gtmap.realestate.common.core.domain.BdcFphSymxDO;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019-09-06
 * @description 发票号VO
 */
public class BdcFphVO extends BdcFphDO{
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
    @ApiModelProperty(value = "自助机标识")
    private Integer zzjfbs;
    @ApiModelProperty(value = "发票类型")
    private Integer fplx;

    @Override
    public String getFphsymxid() {
        return fphsymxid;
    }

    @Override
    public void setFphsymxid(String fphsymxid) {
        this.fphsymxid = fphsymxid;
    }

    @Override
    public String getFphid() {
        return fphid;
    }

    @Override
    public void setFphid(String fphid) {
        this.fphid = fphid;
    }

    @Override
    public Integer getSyqk() {
        return syqk;
    }

    @Override
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
    public Integer getZzjfbs() {
        return zzjfbs;
    }

    @Override
    public void setZzjfbs(Integer zzjfbs) {
        this.zzjfbs = zzjfbs;
    }

    @Override
    public Integer getFplx() {
        return fplx;
    }

    @Override
    public void setFplx(Integer fplx) {
        this.fplx = fplx;
    }

    @Override
    public String toString() {
        return "BdcFphVO{" +
                "fphsymxid='" + fphsymxid + '\'' +
                ", fphid='" + fphid + '\'' +
                ", syqk=" + syqk +
                ", syr='" + syr + '\'' +
                ", syrid='" + syrid + '\'' +
                ", sysj=" + sysj +
                ", syyy='" + syyy + '\'' +
                ", zzjfbs=" + zzjfbs +
                ", fplx=" + fplx +
                '}';
    }
}
