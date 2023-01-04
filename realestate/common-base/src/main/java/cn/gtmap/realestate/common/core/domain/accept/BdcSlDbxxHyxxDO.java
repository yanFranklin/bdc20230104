package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 2021-01-29
 * @description 婚姻信息比对信息
 */
@Table(name = "BDC_SL_DBXX_HYXX")
public class BdcSlDbxxHyxxDO {

    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "申请人id")
    private String sqrid;

    @ApiModelProperty(value = "婚姻状态")
    private String hyzt;

    @ApiModelProperty(value = "配偶姓名")
    private String poxm;

    @ApiModelProperty(value = "配偶证件种类")
    private Integer pozjzl;

    @ApiModelProperty(value = "配偶证件号")
    private String pozjh;

    @ApiModelProperty(value = "婚姻登记机构")
    private String hydjjg;

    @ApiModelProperty(value = "登记日期")
    private Date djrq;

    @ApiModelProperty(value = "是否通过比对")
    private String sfbdtg;

    @ApiModelProperty(value = "原因")
    private String yy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getHyzt() {
        return hyzt;
    }

    public void setHyzt(String hyzt) {
        this.hyzt = hyzt;
    }

    public String getPoxm() {
        return poxm;
    }

    public void setPoxm(String poxm) {
        this.poxm = poxm;
    }

    public Integer getPozjzl() {
        return pozjzl;
    }

    public void setPozjzl(Integer pozjzl) {
        this.pozjzl = pozjzl;
    }

    public String getPozjh() {
        return pozjh;
    }

    public void setPozjh(String pozjh) {
        this.pozjh = pozjh;
    }

    public String getHydjjg() {
        return hydjjg;
    }

    public void setHydjjg(String hydjjg) {
        this.hydjjg = hydjjg;
    }

    public Date getDjrq() {
        return djrq;
    }

    public void setDjrq(Date djrq) {
        this.djrq = djrq;
    }

    public String getSfbdtg() {
        return sfbdtg;
    }

    public void setSfbdtg(String sfbdtg) {
        this.sfbdtg = sfbdtg;
    }

    public String getYy() {
        return yy;
    }

    public void setYy(String yy) {
        this.yy = yy;
    }

    @Override
    public String toString() {
        return "BdcSlDbxxHyxxDO{" +
                "id='" + id + '\'' +
                ", sqrid='" + sqrid + '\'' +
                ", hyzt='" + hyzt + '\'' +
                ", poxm='" + poxm + '\'' +
                ", pozjzl=" + pozjzl +
                ", pozjh='" + pozjh + '\'' +
                ", hydjjg='" + hydjjg + '\'' +
                ", djrq=" + djrq +
                ", sfbdtg='" + sfbdtg + '\'' +
                ", yy='" + yy + '\'' +
                '}';
    }
}
