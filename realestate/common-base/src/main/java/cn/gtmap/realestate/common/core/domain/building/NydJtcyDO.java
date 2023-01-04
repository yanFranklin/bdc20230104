package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018/11/11
 * @description农用地家庭成员
 */
@Table(name = "NYD_JTCY")
    public class NydJtcyDO {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String nydJtcyIndex;
    /**
     * 家庭标识
     */
    @ApiModelProperty(value = "家庭标识")
    private String jtIndex;
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
    @ApiModelProperty(value = "身份证号码")
    private String sfzhm;
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
     * 成员备注说明
     */
    @ApiModelProperty(value = "成员备注说明")
    private String cybzsm;
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新时间",example = "2018-10-01 12:18:48")
    private Date gxsj;

    public String getNydJtcyIndex() {
        return nydJtcyIndex;
    }

    public void setNydJtcyIndex(String nydJtcyIndex) {
        this.nydJtcyIndex = nydJtcyIndex;
    }

    public String getJtIndex() {
        return jtIndex;
    }

    public void setJtIndex(String jtIndex) {
        this.jtIndex = jtIndex;
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

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
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


    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getCybzsm() {
        return cybzsm;
    }

    public void setCybzsm(String cybzsm) {
        this.cybzsm = cybzsm;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    @Override
    public String toString() {
        return "NydJtcyDO{" +
                "nydJtcyIndex='" + nydJtcyIndex + '\'' +
                ", jtIndex='" + jtIndex + '\'' +
                ", xh=" + xh +
                ", xm='" + xm + '\'' +
                ", xb='" + xb + '\'' +
                ", sfzhm='" + sfzhm + '\'' +
                ", gx='" + gx + '\'' +
                ", hkszd='" + hkszd + '\'' +
                ", gxrq=" + gxrq +
                ", bz='" + bz + '\'' +
                ", cybzsm='" + cybzsm + '\'' +
                ", bgbh='" + bgbh + '\'' +
                ", gxsj=" + gxsj +
                '}';
    }
}
