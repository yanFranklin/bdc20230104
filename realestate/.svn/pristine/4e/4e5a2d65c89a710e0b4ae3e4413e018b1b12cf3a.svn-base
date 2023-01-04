package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/28/15:57
 * @Description:
 */
@ApiModel(value = "BdcDpDyjeMxDTO", description = "不动产大屏抵押金额统计明细DTO")
public class BdcDpDyjeMxDTO {
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "被担保主债权数额")
    private Double bdbzzqse;
    @ApiModelProperty(value = "债务履行起始时间",example = "2018-10-01 12:18:48")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxqssj;
    @ApiModelProperty(value = "债务履行结束时间",example = "2018-10-01 12:18:48")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date zwlxjssj;
    @Zd(tableClass = BdcZdQsztDO.class)
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "义务人")
    private String ywr;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @Zd(tableClass = BdcZdDyfsDO.class)
    @ApiModelProperty(value = "抵押方式")
    private Integer dyfs;
    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Integer getDyfs() {
        return dyfs;
    }

    public void setDyfs(Integer dyfs) {
        this.dyfs = dyfs;
    }

    @Override
    public String toString() {
        return "BdcDpDyjeMxDTO{" +
                "slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", bdbzzqse=" + bdbzzqse +
                ", zwlxqssj=" + zwlxqssj +
                ", zwlxjssj=" + zwlxjssj +
                ", qszt=" + qszt +
                ", qlr='" + qlr + '\'' +
                ", ywr='" + ywr + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", dyfs=" + dyfs +
                '}';
    }
}
