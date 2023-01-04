package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name = "fw_fcqlr")
@ApiModel(value = "FwFcqlrDO", description = "房屋房产权利人实体")
public class FwFcqlrDO {

    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String fwFcqlrIndex;
    /**
     * 房屋主键
     */
    @RequiredFk
    @ApiModelProperty(value = "房屋主键")
    private String fwIndex;
    /**
     * 权利人
     */
    @ApiModelProperty(value = "权利人")
    private String qlr;
    /**
     * 权利人证件类型
     */
    @Zd(tableClass = SZdZjllxDO.class)
    @ApiModelProperty(value = "权利人证件类型")
    private String qlrzjlx;
    /**
     * 权利人证件号
     */
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    /**
     * 房产证编号
     */
    @ApiModelProperty(value = "房产证编号")
    private String fczbh;
    /**
     * 发证机关
     */
    @ApiModelProperty(value = "发证机关")
    private String fzjg;
    /**
     * 所属行业
     */
    @ApiModelProperty(value = "所属行业")
    private String sshy;
    /**
     * 土地证编号
     */
    @ApiModelProperty(value = "土地证编号")
    private String tdzbh;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 国家/地区
     */
    @ApiModelProperty(value = "国家/地区")
    private String gj;
    /**
     * 户籍所在省市
     */
    @ApiModelProperty(value = "户籍所在省市")
    private String hjszss;
    /**
     * 权利人编号
     */
    @ApiModelProperty(value = "权利人编号")
    private String qlrbh;
    /**
     * 不动产权证号
     */
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String xb;
    /**
     * 单位性质
     */
    @ApiModelProperty(value = "单位性质")
    private String dwxz;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String dz;
    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String dzyj;
    /**
     * 共有方式
     */
    @ApiModelProperty(value = "共有方式")
    private String gyfs;
    /**
     * 工作单位
     */
    @ApiModelProperty(value = "工作单位")
    private String gzdw;
    /**
     * 权利比例
     */
    @ApiModelProperty(value = "权利比例")
    private String qlbl;
    /**
     * 权利面积
     */
    @ApiModelProperty(value = "权利面积")
    private Double qlmj;
    /**
     * 权利人性质
     */
    @Zd(tableClass = SZdQlrxzDO.class)
    @ApiModelProperty(value = "权利人性质")
    private String qlrxz;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String dh;
    /**
     * 邮编
     */
    @ApiModelProperty(value = "邮编")
    private String yb;
    /**
     * 原房产证号
     */
    @ApiModelProperty(value = "原房产证号")
    private String yfczbh;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private String zt;

    /**
     * 权利人特征
     */
    @ApiModelProperty(value = "权利人特征")
    private String qlrtz;


    public String getFwFcqlrIndex() {
        return fwFcqlrIndex;
    }

    public void setFwFcqlrIndex(String fwFcqlrIndex) {
        this.fwFcqlrIndex = fwFcqlrIndex;
    }

    public String getQlrxz() {
        return qlrxz;
    }

    public void setQlrxz(String qlrxz) {
        this.qlrxz = qlrxz;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getDwxz() {
        return dwxz;
    }

    public void setDwxz(String dwxz) {
        this.dwxz = dwxz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getFwIndex() {
        return fwIndex;
    }

    public void setFwIndex(String fwIndex) {
        this.fwIndex = fwIndex;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getFczbh() {
        return fczbh;
    }

    public void setFczbh(String fczbh) {
        this.fczbh = fczbh;
    }

    public String getTdzbh() {
        return tdzbh;
    }

    public void setTdzbh(String tdzbh) {
        this.tdzbh = tdzbh;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getQlrzjlx() {
        return qlrzjlx;
    }

    public void setQlrzjlx(String qlrzjlx) {
        this.qlrzjlx = qlrzjlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlrbh() {
        return qlrbh;
    }

    public void setQlrbh(String qlrbh) {
        this.qlrbh = qlrbh;
    }

    public String getYfczbh() {
        return yfczbh;
    }

    public void setYfczbh(String yfczbh) {
        this.yfczbh = yfczbh;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getHjszss() {
        return hjszss;
    }

    public void setHjszss(String hjszss) {
        this.hjszss = hjszss;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getDzyj() {
        return dzyj;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    public Double getQlmj() {
        return qlmj;
    }

    public void setQlmj(Double qlmj) {
        this.qlmj = qlmj;
    }

    public String getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(String qlrtz) {
        this.qlrtz = qlrtz;
    }

    @Override
    public String toString() {
        return "FwFcqlrDO{" +
                "fwFcqlrIndex='" + fwFcqlrIndex + '\'' +
                ", fwIndex='" + fwIndex + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjlx='" + qlrzjlx + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", fczbh='" + fczbh + '\'' +
                ", fzjg='" + fzjg + '\'' +
                ", sshy='" + sshy + '\'' +
                ", tdzbh='" + tdzbh + '\'' +
                ", bz='" + bz + '\'' +
                ", gj='" + gj + '\'' +
                ", hjszss='" + hjszss + '\'' +
                ", qlrbh='" + qlrbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", xb='" + xb + '\'' +
                ", dwxz='" + dwxz + '\'' +
                ", dz='" + dz + '\'' +
                ", dzyj='" + dzyj + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gzdw='" + gzdw + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", qlmj=" + qlmj +
                ", qlrxz='" + qlrxz + '\'' +
                ", dh='" + dh + '\'' +
                ", yb='" + yb + '\'' +
                ", yfczbh='" + yfczbh + '\'' +
                ", zt='" + zt + '\'' +
                ", qlrtz='" + qlrtz + '\'' +
                '}';
    }
}
