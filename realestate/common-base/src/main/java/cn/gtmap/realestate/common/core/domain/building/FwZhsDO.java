package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:xiayuqing@gtmap.cn">xiayuqing</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name = "fw_zhs")
@ApiModel(value = "FwZhsDO", description = "房屋子户室实体")
public class FwZhsDO {
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String fwZhsIndex;
    /**
     * 户室主键
     */
    @RequiredFk
    @ApiModelProperty(value = "户室主键")
    private String fwHsIndex;
    /**
     * 物理层数
     */
    @ApiModelProperty(value = "物理层数")
    private Integer wlcs;
    /**
     * 房间号
     */
    @ApiModelProperty(value = "房间号")
    private String fjh;
    /**
     * 定义层数
     */
    @ApiModelProperty(value = "定义层数")
    private String dycs;
    /**
     * 单元号
     */
    @ApiModelProperty(value = "单元号")
    private String dyh;
    /**
     * 室序号
     */
    @ApiModelProperty(value = "室序号")
    private Integer sxh;
    /**
     * 层号
     */
    @ApiModelProperty(value = "层号")
    private Integer ch;
    /**
     * 层高
     */
    @ApiModelProperty(value = "层高")
    private Double cg;
    /**
     * 权利ID
     */
    @ApiModelProperty(value = "权利ID")
    private String qlid;
    /**
     * 共有土地面积
     */
    @ApiModelProperty(value = "共有土地面积")
    private Double gytdmj;
    /**
     * 分摊土地面积
     */
    @ApiModelProperty(value = "分摊土地面积")
    private Double fttdmj;
    /**
     * 独用土地面积
     */
    @ApiModelProperty(value = "独用土地面积")
    private Double dytdmj;
    /**
     * 预测建筑面积
     */
    @ApiModelProperty(value = "预测建筑面积")
    private Double ycjzmj;
    /**
     * 预测套内建筑面积
     */
    @ApiModelProperty(value = "预测套内建筑面积")
    private Double yctnjzmj;
    /**
     * 预测分摊建筑面积
     */
    @ApiModelProperty(value = "预测分摊建筑面积")
    private Double ycftjzmj;
    /**
     * 预测地下部分建筑面积
     */
    @ApiModelProperty(value = "预测地下部分建筑面积")
    private Double ycdxbfjzmj;
    /**
     * 预测其他建筑面积
     */
    @ApiModelProperty(value = "预测其他建筑面积")
    private Double ycqtjzmj;
    /**
     * 预测分摊系数
     */
    @ApiModelProperty(value = "预测分摊系数")
    private Double ycftxs;
    /**
     * 实测建筑面积
     */
    @ApiModelProperty(value = "实测建筑面积")
    private Double scjzmj;
    /**
     * 实测分摊建筑面积
     */
    @ApiModelProperty(value = "实测分摊建筑面积")
    private Double scftjzmj;
    /**
     * 实测套内建筑面积
     */
    @ApiModelProperty(value = "实测套内建筑面积")
    private Double sctnjzmj;
    /**
     * 实测地下部分建筑面积
     */
    @ApiModelProperty(value = "实测地下部分建筑面积")
    private Double scdxbfjzmj;
    /**
     * 实测其它建筑面积
     */
    @ApiModelProperty(value = "实测其它建筑面积")
    private Double scqtjzmj;
    /**
     * 实测分摊系数
     */
    @ApiModelProperty(value = "实测分摊系数")
    private Double scftxs;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    private String zl;
    /**
     * 交易价格
     */
    @ApiModelProperty(value = "交易价格")
    private Double jyjg;
    /**
     * 规划用途
     */
    @ApiModelProperty(value = "规划用途")
    private String ghyt;
    /**
     * 规划用途2
     */
    @ApiModelProperty(value = "规划用途2")
    private String ghyt2;
    /**
     * 规划用途3
     */
    @ApiModelProperty(value = "规划用途3")
    private String ghyt3;
    /**
     * 房屋类型
     */
    @ApiModelProperty(value = "房屋类型")
    private String fwlx;
    /**
     * 房屋性质
     */
    @ApiModelProperty(value = "房屋性质")
    private String fwxz;
    /**
     * 东
     */
    @ApiModelProperty(value = "东")
    private String d;
    /**
     * 南
     */
    @ApiModelProperty(value = "南")
    private String n;
    /**
     * 西
     */
    @ApiModelProperty(value = "西")
    private String x;
    /**
     * 北
     */
    @ApiModelProperty(value = "北")
    private String b;
    /**
     * 产权来源
     */
    @ApiModelProperty(value = "产权来源")
    private String cqly;
    /**
     * 要素代码
    @ApiModelProperty(value = "要素代码")
    private String ysdm;*/
    /**
     * 附加说明
     */
    @ApiModelProperty(value = "附加说明")
    private String fjsm;
    /**
     * 调查意见
     */
    @ApiModelProperty(value = "调查意见")
    private String dcyj;
    /**
     * 调查者
     */
    @ApiModelProperty(value = "调查者")
    private String dcz;
    /**
     * 调查时间
     */
    @ApiModelProperty(value = "调查时间",example = "2018-10-01 12:18:21")
    private Date dcsj;
    /**
     * 房屋户型
     */
    @ApiModelProperty(value = "房屋户型")
    private String fwhx;
    /**
     * 建成时装修程度
     */
    @ApiModelProperty(value = "建成时装修程度")
    private String jczxcd;
    /**
     * 户型结构
     */
    @ApiModelProperty(value = "户型结构")
    private String hxjg;
    /**
     * 房产档案号
    @ApiModelProperty(value = "房产档案号")
    private String fcdah;*/
    /**
     * 权利状态
    @ApiModelProperty(value = "权利状态")
    private String qlzt;*/
    /**
     * 合并方向
    @ApiModelProperty(value = "合并方向")
    private String hbfx;*/
    /**
     * 合并户数
    @ApiModelProperty(value = "合并户数")
    private String hbhs;*/
    /**
     * 共有情况
     */
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    /**
     * 备注
    @ApiModelProperty(value = "备注")
    private String bz;*/

    public String getFwZhsIndex() {
        return fwZhsIndex;
    }

    public void setFwZhsIndex(String fwZhsIndex) {
        this.fwZhsIndex = fwZhsIndex;
    }

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }

    public Integer getWlcs() {
        return wlcs;
    }

    public void setWlcs(Integer wlcs) {
        this.wlcs = wlcs;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getDycs() {
        return dycs;
    }

    public void setDycs(String dycs) {
        this.dycs = dycs;
    }

    public String getDyh() {
        return dyh;
    }

    public void setDyh(String dyh) {
        this.dyh = dyh;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    public Integer getCh() {
        return ch;
    }

    public void setCh(Integer ch) {
        this.ch = ch;
    }

    public Double getCg() {
        return cg;
    }

    public void setCg(Double cg) {
        this.cg = cg;
    }

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Double getGytdmj() {
        return gytdmj;
    }

    public void setGytdmj(Double gytdmj) {
        this.gytdmj = gytdmj;
    }

    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }

    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    public Double getYcjzmj() {
        return ycjzmj;
    }

    public void setYcjzmj(Double ycjzmj) {
        this.ycjzmj = ycjzmj;
    }

    public Double getYctnjzmj() {
        return yctnjzmj;
    }

    public void setYctnjzmj(Double yctnjzmj) {
        this.yctnjzmj = yctnjzmj;
    }

    public Double getYcftjzmj() {
        return ycftjzmj;
    }

    public void setYcftjzmj(Double ycftjzmj) {
        this.ycftjzmj = ycftjzmj;
    }

    public Double getYcdxbfjzmj() {
        return ycdxbfjzmj;
    }

    public void setYcdxbfjzmj(Double ycdxbfjzmj) {
        this.ycdxbfjzmj = ycdxbfjzmj;
    }

    public Double getYcqtjzmj() {
        return ycqtjzmj;
    }

    public void setYcqtjzmj(Double ycqtjzmj) {
        this.ycqtjzmj = ycqtjzmj;
    }

    public Double getYcftxs() {
        return ycftxs;
    }

    public void setYcftxs(Double ycftxs) {
        this.ycftxs = ycftxs;
    }

    public Double getScjzmj() {
        return scjzmj;
    }

    public void setScjzmj(Double scjzmj) {
        this.scjzmj = scjzmj;
    }

    public Double getScftjzmj() {
        return scftjzmj;
    }

    public void setScftjzmj(Double scftjzmj) {
        this.scftjzmj = scftjzmj;
    }

    public Double getSctnjzmj() {
        return sctnjzmj;
    }

    public void setSctnjzmj(Double sctnjzmj) {
        this.sctnjzmj = sctnjzmj;
    }

    public Double getScdxbfjzmj() {
        return scdxbfjzmj;
    }

    public void setScdxbfjzmj(Double scdxbfjzmj) {
        this.scdxbfjzmj = scdxbfjzmj;
    }

    public Double getScqtjzmj() {
        return scqtjzmj;
    }

    public void setScqtjzmj(Double scqtjzmj) {
        this.scqtjzmj = scqtjzmj;
    }

    public Double getScftxs() {
        return scftxs;
    }

    public void setScftxs(Double scftxs) {
        this.scftxs = scftxs;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    public String getGhyt2() {
        return ghyt2;
    }

    public void setGhyt2(String ghyt2) {
        this.ghyt2 = ghyt2;
    }

    public String getGhyt3() {
        return ghyt3;
    }

    public void setGhyt3(String ghyt3) {
        this.ghyt3 = ghyt3;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }

    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getCqly() {
        return cqly;
    }

    public void setCqly(String cqly) {
        this.cqly = cqly;
    }

    public String getFjsm() {
        return fjsm;
    }

    public void setFjsm(String fjsm) {
        this.fjsm = fjsm;
    }

    public String getDcyj() {
        return dcyj;
    }

    public void setDcyj(String dcyj) {
        this.dcyj = dcyj;
    }

    public String getDcz() {
        return dcz;
    }

    public void setDcz(String dcz) {
        this.dcz = dcz;
    }

    public Date getDcsj() {
        return dcsj;
    }

    public void setDcsj(Date dcsj) {
        this.dcsj = dcsj;
    }

    public String getFwhx() {
        return fwhx;
    }

    public void setFwhx(String fwhx) {
        this.fwhx = fwhx;
    }

    public String getJczxcd() {
        return jczxcd;
    }

    public void setJczxcd(String jczxcd) {
        this.jczxcd = jczxcd;
    }

    public String getHxjg() {
        return hxjg;
    }

    public void setHxjg(String hxjg) {
        this.hxjg = hxjg;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    @Override
    public String toString() {
        return "FwZhsDO{" +
                "fwZhsIndex='" + fwZhsIndex + '\'' +
                ", fwHsIndex='" + fwHsIndex + '\'' +
                ", wlcs=" + wlcs +
                ", fjh='" + fjh + '\'' +
                ", dycs='" + dycs + '\'' +
                ", dyh='" + dyh + '\'' +
                ", sxh=" + sxh +
                ", ch=" + ch +
                ", cg=" + cg +
                ", qlid='" + qlid + '\'' +
                ", gytdmj=" + gytdmj +
                ", fttdmj=" + fttdmj +
                ", dytdmj=" + dytdmj +
                ", ycjzmj=" + ycjzmj +
                ", yctnjzmj=" + yctnjzmj +
                ", ycftjzmj=" + ycftjzmj +
                ", ycdxbfjzmj=" + ycdxbfjzmj +
                ", ycqtjzmj=" + ycqtjzmj +
                ", ycftxs=" + ycftxs +
                ", scjzmj=" + scjzmj +
                ", scftjzmj=" + scftjzmj +
                ", sctnjzmj=" + sctnjzmj +
                ", scdxbfjzmj=" + scdxbfjzmj +
                ", scqtjzmj=" + scqtjzmj +
                ", scftxs=" + scftxs +
                ", zl='" + zl + '\'' +
                ", jyjg=" + jyjg +
                ", ghyt='" + ghyt + '\'' +
                ", ghyt2='" + ghyt2 + '\'' +
                ", ghyt3='" + ghyt3 + '\'' +
                ", fwlx='" + fwlx + '\'' +
                ", fwxz='" + fwxz + '\'' +
                ", d='" + d + '\'' +
                ", n='" + n + '\'' +
                ", x='" + x + '\'' +
                ", b='" + b + '\'' +
                ", cqly='" + cqly + '\'' +
                ", fjsm='" + fjsm + '\'' +
                ", dcyj='" + dcyj + '\'' +
                ", dcz='" + dcz + '\'' +
                ", dcsj=" + dcsj +
                ", fwhx='" + fwhx + '\'' +
                ", jczxcd='" + jczxcd + '\'' +
                ", hxjg='" + hxjg + '\'' +
                ", gyqk='" + gyqk + '\'' +
                '}';
    }
}
