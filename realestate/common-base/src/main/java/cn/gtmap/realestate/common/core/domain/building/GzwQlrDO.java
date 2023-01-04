package cn.gtmap.realestate.common.core.domain.building;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-22
 * @description 构筑物权利人实体
 */
@Table(name = "GZW_QLR")
public class GzwQlrDO {

    /**
     * 主键
     */
    @Id
    private String gzwQlrIndex;

    /**
     * 外键 DZW_DCB 主键
     */
    private String gzwDcbIndex;

    /**
     * 权利人姓名
     */
    private String qlr;

    /**
     * 权利人证件类型
     */
    private String qlrzjlx;

    /**
     * 权利人证件号
     */
    private String qlrzjh;
    /**
     * 发证机关
     */
    private String fzjg;
    /**
     * 所属行业
     */
    private String sshy;
    /**
     * 土地证编号
     */
    private String tdzbh;
    /**
     * 备注
     */
    private String bz;
    /**
     * 国家/地区
     */
    private String gj;
    /**
     * 户籍所在省市
     */
    private String hjszss;

    /**
     * 权利人编号
     */
    private String qlrbh;

    /**
     * 不动产权证号
     */
    private String bdcqzh;

    /**
     * 性别
     */
    private String xb;

    /**
     * 电话
     */
    private String dh;

    /**
     * 单位性质
     */
    private String dwxz;

    /**
     * 地址
     */
    private String dz;

    /**
     * 电子邮件
     */
    private String dzyj;

    /**
     * 共有方式
     */
    private String gyfs;

    /**
     * 工作单位
     */
    private String gzdw;

    /**
     * 权利比例
     */
    private String qlbl;

    /**
     * 权利面积
     */
    private Double qlmj;

    /**
     * 权利人性质
     */
    private String qlrxz;

    /**
     * 邮编
     */
    private String yb;

    /**
     * 邮政编码
     */
    private String yzbm;

    /**
     * 状态
     */
    private String zt;

    /**
     * 变更编号
     */
    private String bgbh;

    /**
     * 权利人特征
     */
    @ApiModelProperty(value = "权利人特征")
    private String qlrtz;

    public String getGzwQlrIndex() {
        return gzwQlrIndex;
    }

    public void setGzwQlrIndex(String gzwQlrIndex) {
        this.gzwQlrIndex = gzwQlrIndex;
    }

    public String getGzwDcbIndex() {
        return gzwDcbIndex;
    }

    public void setGzwDcbIndex(String gzwDcbIndex) {
        this.gzwDcbIndex = gzwDcbIndex;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjlx() {
        return qlrzjlx;
    }

    public void setQlrzjlx(String qlrzjlx) {
        this.qlrzjlx = qlrzjlx;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
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

    public String getTdzbh() {
        return tdzbh;
    }

    public void setTdzbh(String tdzbh) {
        this.tdzbh = tdzbh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public String getQlrbh() {
        return qlrbh;
    }

    public void setQlrbh(String qlrbh) {
        this.qlrbh = qlrbh;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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

    public String getDwxz() {
        return dwxz;
    }

    public void setDwxz(String dwxz) {
        this.dwxz = dwxz;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getDzyj() {
        return dzyj;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    public Double getQlmj() {
        return qlmj;
    }

    public void setQlmj(Double qlmj) {
        this.qlmj = qlmj;
    }

    public String getQlrxz() {
        return qlrxz;
    }

    public void setQlrxz(String qlrxz) {
        this.qlrxz = qlrxz;
    }

    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    public String getYzbm() {
        return yzbm;
    }

    public void setYzbm(String yzbm) {
        this.yzbm = yzbm;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(String qlrtz) {
        this.qlrtz = qlrtz;
    }

    @Override
    public String toString() {
        return "GzwQlrDO{" +
                "gzwQlrIndex='" + gzwQlrIndex + '\'' +
                ", gzwDcbIndex='" + gzwDcbIndex + '\'' +
                ", qlr='" + qlr + '\'' +
                ", qlrzjlx='" + qlrzjlx + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", fzjg='" + fzjg + '\'' +
                ", sshy='" + sshy + '\'' +
                ", tdzbh='" + tdzbh + '\'' +
                ", bz='" + bz + '\'' +
                ", gj='" + gj + '\'' +
                ", hjszss='" + hjszss + '\'' +
                ", qlrbh='" + qlrbh + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", xb='" + xb + '\'' +
                ", dh='" + dh + '\'' +
                ", dwxz='" + dwxz + '\'' +
                ", dz='" + dz + '\'' +
                ", dzyj='" + dzyj + '\'' +
                ", gyfs='" + gyfs + '\'' +
                ", gzdw='" + gzdw + '\'' +
                ", qlbl='" + qlbl + '\'' +
                ", qlmj=" + qlmj +
                ", qlrxz='" + qlrxz + '\'' +
                ", yb='" + yb + '\'' +
                ", yzbm='" + yzbm + '\'' +
                ", zt='" + zt + '\'' +
                ", bgbh='" + bgbh + '\'' +
                ", qlrtz='" + qlrtz + '\'' +
                '}';
    }
}
