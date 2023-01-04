package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:sunyuzhaung@gtmap.cn">sunyuzhaung</a>
 * @version 1.0  2021/11/25
 * @description房屋家庭成员
 */
@Table(name = "Fw_JTCY")
    public class FwJtcyDO {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String fwJtcyIndex;
    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer xh;
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    private String xm;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "证件号")
    private String zjh;
    /**
     * 关系
     */
    @ApiModelProperty(value = "关系")
    private String gx;
    /**
     * 户口所在地
     */
    @ApiModelProperty(value = "户口所在地")
    private String hkszd;
    /**
     * 建立日期
     */
    @ApiModelProperty(value = "建立日期",example = "2018-10-01 12:18:48")
    private Date jlrq;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:48")
    private Date gxrq;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;

    /**
     * 证件种类
     */
    @ApiModelProperty(value = "证件种类")
    private String zjzl;

    /**
     * 是否共有人
     */
    @ApiModelProperty(value = "是否共有人")
    private String sfgyr;

    /**
     * 不动产主键
     */
    @ApiModelProperty(value = "不动产主键")
    private String bdcIndex;

    /**
     * 权利人主键
     */
    @ApiModelProperty(value = "权利人主键")
    private String qlrIndex;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新时间",example = "2018-10-01 12:18:48")
    private Date gxsj;


    public String getFwJtcyIndex() {
        return fwJtcyIndex;
    }

    public void setFwJtcyIndex(String fwJtcyIndex) {
        this.fwJtcyIndex = fwJtcyIndex;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getGx() {
        return gx;
    }

    public void setGx(String gx) {
        this.gx = gx;
    }

    public String getHkszd() {
        return hkszd;
    }

    public void setHkszd(String hkszd) {
        this.hkszd = hkszd;
    }

    public Date getJlrq() {
        return jlrq;
    }

    public void setJlrq(Date jlrq) {
        this.jlrq = jlrq;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getSfgyr() {
        return sfgyr;
    }

    public void setSfgyr(String sfgyr) {
        this.sfgyr = sfgyr;
    }

    public String getBdcIndex() {
        return bdcIndex;
    }

    public void setBdcIndex(String bdcIndex) {
        this.bdcIndex = bdcIndex;
    }

    public String getQlrIndex() {
        return qlrIndex;
    }

    public void setQlrIndex(String qlrIndex) {
        this.qlrIndex = qlrIndex;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Override
    public String toString() {
        return "FwJtcyDO{" +
                "fwJtcyIndex='" + fwJtcyIndex + '\'' +
                ", xh=" + xh +
                ", xm='" + xm + '\'' +
                ", xb='" + xb + '\'' +
                ", zjh='" + zjh + '\'' +
                ", gx='" + gx + '\'' +
                ", hkszd='" + hkszd + '\'' +
                ", jlrq=" + jlrq +
                ", gxrq=" + gxrq +
                ", bz='" + bz + '\'' +
                ", zjzl='" + zjzl + '\'' +
                ", sfgyr='" + sfgyr + '\'' +
                ", bdcIndex='" + bdcIndex + '\'' +
                ", qlrIndex='" + qlrIndex + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }
}
