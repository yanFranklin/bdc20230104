package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.dto.building.DjDcbResponseDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/11/8
 * @description
 */
@Table(name="zd_zjdxx")
public class ZdZjdxxDO{
    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String zdZjdxxIndex;
    /**
     * 调查主表主键
     */
    @ApiModelProperty(value = "调查主表主键")
    private String djdcbIndex;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String djh;
    /**
     * 土地使用者
     */
    @ApiModelProperty(value = "土地使用者")
    private String tdsyz;
    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证号码")
    private String sfzhm;
    /**
     * 门牌号
     */
    @ApiModelProperty(value = "门牌号")
    private String mph;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String lxdh;
    /**
     * 面积单位
     */
    @ApiModelProperty(value = "面积单位")
    private String mjdw;
    /**
     * 土地证号
     */
    @ApiModelProperty(value = "土地证号")
    private String tdzh;
    /**
     * 登记面积
     */
    @ApiModelProperty(value = "登记面积")
    private Double djmj;
    /**
     * 主房建成时间
     */
    @ApiModelProperty(value = "主房建成时间",example = "2018-10-01 12:18:21")
    private Date zfjcsj;
    /**
     * 厢房及院落建成时间
     */
    @ApiModelProperty(value = "厢房及院落建成时间",example = "2018-10-01 12:18:21")
    private Date yljcsj;
    /**
     * 楼层数
     */
    @ApiModelProperty(value = "楼层数")
    private String lcs;
    /**
     * 镇
     */
    @ApiModelProperty(value = "镇")
    private String zlz;
    /**
     * 村
     */
    @ApiModelProperty(value = "村")
    private String zlc;
    /**
     * 组
     */
    @ApiModelProperty(value = "组")
    private String zlg;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 建立日期
     */
    @ApiModelProperty(value = "建立日期",example = "2018-10-01 12:18:21")
    private Date jlrq;
    /**
     * 宅基地取得时间
     */
    @ApiModelProperty(value = "宅基地取得时间",example = "2018-10-01 12:18:21")
    private Date zjdqdsj;
    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期",example = "2018-10-01 12:18:21")
    private Date gxrq;
    /**
     * 宗地超占面积
     */
    @ApiModelProperty(value = "宗地超占面积")
    private Double zdczmj;
    /**
     * 承包土地面积
     */
    @ApiModelProperty(value = "承包土地面积")
    private Double cbtdmj;
    /**
     * 登记发证档案年
     */
    @ApiModelProperty(value = "登记发证档案年")
    private String djfzdan;
    /**
     * 登记发证档案卷
     */
    @ApiModelProperty(value = "登记发证档案卷")
    private String djfzdaj;
    /**
     * 建筑面积
     */
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;
    /**
     * 建筑物类型
     */
    @ApiModelProperty(value = "建筑物类型")
    private String jzwlx;
    /**
     * 房屋情况
     */
    @ApiModelProperty(value = "房屋情况")
    private String fwqk;
    /**
     * 取得方式
     */
    @ApiModelProperty(value = "取得方式")
    private String qdfs;
    /**
     * 证明材料
     */
    @ApiModelProperty(value = "证明材料")
    private String zmcl;
    /**
     * 主房数
     */
    @ApiModelProperty(value = "主房数")
    private String zfs;
    /**
     * 附房数
     */
    @ApiModelProperty(value = "附房数")
    private String ffs;
    /**
     * 原土地使用者
     */
    @ApiModelProperty(value = "原土地使用者")
    private String ytdsyz;
    /**
     * 原地籍号
     */
    @ApiModelProperty(value = "原地籍号")
    private String ydjh;
    /**
     * 实际使用面积
     */
    @ApiModelProperty(value = "实际使用面积")
    private Double sjsymj;
    /**
     * 宅基地批准文号
     */
    @ApiModelProperty(value = "宅基地批准文号")
    private String pzwh;
    /**
     * 超占例外
     */
    @ApiModelProperty(value = "超占例外")
    private String czlw;
    /**
     * 例外原因
     */
    @ApiModelProperty(value = "例外原因")
    private String czlwyy;
    /**
     * 民族
     */
    @ApiModelProperty(value = "民族")
    private String mz;

    public String getZdZjdxxIndex() {
        return zdZjdxxIndex;
    }

    public void setZdZjdxxIndex(String zdZjdxxIndex) {
        this.zdZjdxxIndex = zdZjdxxIndex;
    }

    public String getDjdcbIndex() {
        return djdcbIndex;
    }

    public void setDjdcbIndex(String djdcbIndex) {
        this.djdcbIndex = djdcbIndex;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getTdsyz() {
        return tdsyz;
    }

    public void setTdsyz(String tdsyz) {
        this.tdsyz = tdsyz;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public Double getDjmj() {
        return djmj;
    }

    public void setDjmj(Double djmj) {
        this.djmj = djmj;
    }

    public Date getZfjcsj() {
        return zfjcsj;
    }

    public void setZfjcsj(Date zfjcsj) {
        this.zfjcsj = zfjcsj;
    }

    public Date getYljcsj() {
        return yljcsj;
    }

    public void setYljcsj(Date yljcsj) {
        this.yljcsj = yljcsj;
    }

    public String getLcs() {
        return lcs;
    }

    public void setLcs(String lcs) {
        this.lcs = lcs;
    }

    public String getZlz() {
        return zlz;
    }

    public void setZlz(String zlz) {
        this.zlz = zlz;
    }

    public String getZlc() {
        return zlc;
    }

    public void setZlc(String zlc) {
        this.zlc = zlc;
    }

    public String getZlg() {
        return zlg;
    }

    public void setZlg(String zlg) {
        this.zlg = zlg;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getJlrq() {
        return jlrq;
    }

    public void setJlrq(Date jlrq) {
        this.jlrq = jlrq;
    }

    public Date getZjdqdsj() {
        return zjdqdsj;
    }

    public void setZjdqdsj(Date zjdqdsj) {
        this.zjdqdsj = zjdqdsj;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public Double getZdczmj() {
        return zdczmj;
    }

    public void setZdczmj(Double zdczmj) {
        this.zdczmj = zdczmj;
    }

    public Double getCbtdmj() {
        return cbtdmj;
    }

    public void setCbtdmj(Double cbtdmj) {
        this.cbtdmj = cbtdmj;
    }

    public String getDjfzdan() {
        return djfzdan;
    }

    public void setDjfzdan(String djfzdan) {
        this.djfzdan = djfzdan;
    }

    public String getDjfzdaj() {
        return djfzdaj;
    }

    public void setDjfzdaj(String djfzdaj) {
        this.djfzdaj = djfzdaj;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public String getJzwlx() {
        return jzwlx;
    }

    public void setJzwlx(String jzwlx) {
        this.jzwlx = jzwlx;
    }

    public String getFwqk() {
        return fwqk;
    }

    public void setFwqk(String fwqk) {
        this.fwqk = fwqk;
    }

    public String getQdfs() {
        return qdfs;
    }

    public void setQdfs(String qdfs) {
        this.qdfs = qdfs;
    }

    public String getZmcl() {
        return zmcl;
    }

    public void setZmcl(String zmcl) {
        this.zmcl = zmcl;
    }

    public String getZfs() {
        return zfs;
    }

    public void setZfs(String zfs) {
        this.zfs = zfs;
    }

    public String getFfs() {
        return ffs;
    }

    public void setFfs(String ffs) {
        this.ffs = ffs;
    }

    public String getYtdsyz() {
        return ytdsyz;
    }

    public void setYtdsyz(String ytdsyz) {
        this.ytdsyz = ytdsyz;
    }

    public String getYdjh() {
        return ydjh;
    }

    public void setYdjh(String ydjh) {
        this.ydjh = ydjh;
    }

    public Double getSjsymj() {
        return sjsymj;
    }

    public void setSjsymj(Double sjsymj) {
        this.sjsymj = sjsymj;
    }

    public String getPzwh() {
        return pzwh;
    }

    public void setPzwh(String pzwh) {
        this.pzwh = pzwh;
    }

    public String getCzlw() {
        return czlw;
    }

    public void setCzlw(String czlw) {
        this.czlw = czlw;
    }

    public String getCzlwyy() {
        return czlwyy;
    }

    public void setCzlwyy(String czlwyy) {
        this.czlwyy = czlwyy;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    @Override
    public String toString() {
        return "ZdZjdxxDO{" +
                "zdZjdxxIndex='" + zdZjdxxIndex + '\'' +
                ", djdcbIndex='" + djdcbIndex + '\'' +
                ", djh='" + djh + '\'' +
                ", tdsyz='" + tdsyz + '\'' +
                ", sfzhm='" + sfzhm + '\'' +
                ", mph='" + mph + '\'' +
                ", lxdh='" + lxdh + '\'' +
                ", mjdw='" + mjdw + '\'' +
                ", tdzh='" + tdzh + '\'' +
                ", djmj=" + djmj +
                ", zfjcsj=" + zfjcsj +
                ", yljcsj=" + yljcsj +
                ", lcs='" + lcs + '\'' +
                ", zlz='" + zlz + '\'' +
                ", zlc='" + zlc + '\'' +
                ", zlg='" + zlg + '\'' +
                ", bz='" + bz + '\'' +
                ", jlrq=" + jlrq +
                ", zjdqdsj=" + zjdqdsj +
                ", gxrq=" + gxrq +
                ", zdczmj=" + zdczmj +
                ", cbtdmj=" + cbtdmj +
                ", djfzdan='" + djfzdan + '\'' +
                ", djfzdaj='" + djfzdaj + '\'' +
                ", jzmj=" + jzmj +
                ", jzwlx='" + jzwlx + '\'' +
                ", fwqk='" + fwqk + '\'' +
                ", qdfs='" + qdfs + '\'' +
                ", zmcl='" + zmcl + '\'' +
                ", zfs='" + zfs + '\'' +
                ", ffs='" + ffs + '\'' +
                ", ytdsyz='" + ytdsyz + '\'' +
                ", ydjh='" + ydjh + '\'' +
                ", sjsymj=" + sjsymj +
                ", pzwh='" + pzwh + '\'' +
                ", czlw='" + czlw + '\'' +
                ", czlwyy='" + czlwyy + '\'' +
                ", mz='" + mz + '\'' +
                '}';
    }
}