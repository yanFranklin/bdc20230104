package cn.gtmap.realestate.common.core.domain.accept;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * *
 *
 * @author <a href="mailto:chenyucheng@gtmap.cn>chenyucheng</a>"
 * @version 1.0, 2021/2/23
 * @description 审核信息对象类
 */
@Table(name = "bdc_sl_shxx")
public class BdcSlShxxDO {
    @Id
    /**
     * 审核信息ID
     */
    @ApiModelProperty(value = "审核信息ID")
    String shxxid;
    /**
     * 节点名称
     */
    @ApiModelProperty(value = "节点名称")
    String jdmc;
    /**
     * 顺序号
     */
    @ApiModelProperty(value = "顺序号")
    Integer sxh;
    /**
     * 审核人员姓名
     */
    @ApiModelProperty(value = "审核人姓名")
    String shryxm;
    /**
     * 审核人员ID
     */
    @ApiModelProperty(value = "审核人员ID")
    String shryid;
    /**
     * 审核开始时间
     */
    @ApiModelProperty(value = "审核开始时间",example = "2018-10-01 12:18:48")
    Date shkssj;
    /**
     * 审核结束时间
     */
    @ApiModelProperty(value = "审核结束时间",example = "2018-10-01 12:18:48")
    Date shjssj;
    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    String shyj;
    /**
     * 签名时间
     */
    @ApiModelProperty(value = "签名时间",example = "2018-10-01 12:18:48")
    Date qmsj;
    /**
     * 签名ID
     */
    @ApiModelProperty(value = "签名ID")
    String qmid;
    /**
     * 操作结果
     */
    @ApiModelProperty(value = "操作结果")
    Integer czjg;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    String bz;
    /**
     * 工作流实例ID
     */
    @ApiModelProperty(value = "工作流实例ID")
    String gzlslid;

    @ApiModelProperty("修正信息id")
    private String xzxxid;

    public String getQmid() {
        return qmid;
    }

    public void setQmid(String qmid) {
        this.qmid = qmid;
    }

    public String getShxxid() {
        return shxxid;
    }

    public void setShxxid(String shxxid) {
        this.shxxid = shxxid;
    }

    public String getJdmc() {
        return jdmc;
    }

    public void setJdmc(String jdmc) {
        this.jdmc = jdmc;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public String getShryxm() {
        return shryxm;
    }

    public void setShryxm(String shryxm) {
        this.shryxm = shryxm;
    }

    public String getShryid() {
        return shryid;
    }

    public void setShryid(String shryid) {
        this.shryid = shryid;
    }

    public Date getShkssj() {
        return shkssj;
    }

    public void setShkssj(Date shkssj) {
        this.shkssj = shkssj;
    }

    public Date getShjssj() {
        return shjssj;
    }

    public void setShjssj(Date shjssj) {
        this.shjssj = shjssj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj;
    }

    public Date getQmsj() {
        return qmsj;
    }

    public void setQmsj(Date qmsj) {
        this.qmsj = qmsj;
    }

    public Integer getCzjg() {
        return czjg;
    }

    public void setCzjg(Integer czjg) {
        this.czjg = czjg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXzxxid() {
        return xzxxid;
    }

    public void setXzxxid(String xzxxid) {
        this.xzxxid = xzxxid;
    }

    @Override
    public String toString() {
        return "BdcSlShxxDO{" +
                "shxxid='" + shxxid + '\'' +
                ", jdmc='" + jdmc + '\'' +
                ", sxh=" + sxh +
                ", shryxm='" + shryxm + '\'' +
                ", shryid='" + shryid + '\'' +
                ", shkssj=" + shkssj +
                ", shjssj=" + shjssj +
                ", shyj='" + shyj + '\'' +
                ", qmsj=" + qmsj +
                ", qmid='" + qmid + '\'' +
                ", czjg=" + czjg +
                ", bz='" + bz + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xzxxid='" + xzxxid + '\'' +
                '}';
    }
}
