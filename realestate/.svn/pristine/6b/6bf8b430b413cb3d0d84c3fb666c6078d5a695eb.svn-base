package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.annotations.SaveBdcdyhZt;
import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name = "fw_ljz")
@ApiModel(value = "FwLjzDO", description = "逻辑幢基本信息实体")
public class FwLjzDO {
    @Id
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private String fwDcbIndex;
    /**
     * 项目信息主键
     */
    @ApiModelProperty(value = "项目信息主键")
    private String fwXmxxIndex;
    /**
     * 要素代码
     */
    @ApiModelProperty(value = "要素代码")
    private String ysdm;
    /**
     * 隶属宗地
     */
    @ApiModelProperty(value = "隶属宗地")
    @RequiredFk
    private String lszd;
    /**
     * 自然幢号
     */
    @ApiModelProperty(value = "自然幢号")
    private String zrzh;
    /**
     * 不动产单元号
     */
    @ApiModelProperty(value = "不动产单元号")
    @SaveBdcdyhZt
    private String bdcdyh;
    /**
     * 逻辑幢号
     */
    @ApiModelProperty(value = "逻辑幢号")
    private String ljzh;
    /**
     * 不动产单元类型
     */
    @Zd(tableClass = SZdBdcdyFwlxDO.class)
    @ApiModelProperty(value = "不动产单元类型")
    private String bdcdyfwlx;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    private String xmmc;
    /**
     * 门牌号
     */
    @ApiModelProperty(value = "门牌号")
    private String mph;
    /**
     * 房屋名称
     */
    @ApiModelProperty(value = "房屋名称")
    private String fwmc;
    /**
     * 幢号
     */
    @ApiModelProperty(value = "幢号")
    private String dh;
    /**
     * 坐落地址
     */
    @ApiModelProperty(value = "坐落地址")
    @SaveBdcdyhZt("zl")
    private String zldz;
    /**
     * 房屋结构
     */
    @Zd(tableClass = SZdFwjgDO.class)
    @ApiModelProperty(value = "房屋结构")
    private String fwjg;
    /**
     * 房屋结构2
     */
    @Zd(tableClass = SZdFwjgDO.class)
    @ApiModelProperty(value = "房屋结构2")
    private String fwjg2;
    /**
     * 房屋结构3
     */
    @Zd(tableClass = SZdFwjgDO.class)
    @ApiModelProperty(value = "房屋结构3")
    private String fwjg3;
    /**
     * 房屋层数
     */
    @ApiModelProperty(value = "房屋层数")
    private Integer fwcs;
    /**
     * 总套数
     */
    @ApiModelProperty(value = "总套数")
    private Integer zts;
    /**
     * 房屋用途
     */
    @Zd(tableClass = SZdFwytDO.class)
    @ApiModelProperty(value = "房屋用途")
    @SaveBdcdyhZt("yt")
    private String fwyt;
    /**
     * 房屋用途2
     */
    @Zd(tableClass = SZdFwytDO.class)
    @ApiModelProperty(value = "房屋用途2")
    private String fwyt2;
    /**
     * 房屋用途3
     */
    @Zd(tableClass = SZdFwytDO.class)
    @ApiModelProperty(value = "房屋用途3")
    private String fwyt3;
    /**
     * 竣工日期
     */
    @ApiModelProperty(value = "竣工日期", example = "2018-10-01 12:18:21")
    private Date jgrq;
    /**
     * 预测建筑面积
     */
    @ApiModelProperty(value = "预测建筑面积")
    private Double ycjzmj;
    /**
     * 预测地下面积
     */
    @ApiModelProperty(value = "预测地下面积")
    private Double ycdxmj;
    /**
     * 预测其他面积
     */
    @ApiModelProperty(value = "预测其他面积")
    private Double ycqtmj;
    /**
     * 实测建筑面积
     */
    @ApiModelProperty(value = "实测建筑面积")
    private Double scjzmj;
    /**
     * 实测地下面积
     */
    @ApiModelProperty(value = "实测地下面积")
    private Double scdxmj;
    /**
     * 实测其他面积
     */
    @ApiModelProperty(value = "实测其他面积")
    private Double scqtmj;

    /**
     * 批准建筑面积
     */
    @ApiModelProperty(value = "批准建筑面积")
    private Double pzjzmj;

    /**
     * 不动产状态
     */
    @ApiModelProperty(value = "不动产状态")
    @SaveBdcdyhZt("bdcdyzt")
    private String bdczt;
    /**
     * 建筑物状态
     */
    @Zd(tableClass = SZdJzwztDO.class)
    @ApiModelProperty(value = "建筑物状态")
    private String jzwzt;
    /**
     * 变更日期
     */
    @ApiModelProperty(value = "变更日期", example = "2018-10-01 12:18:21")
    private Date gxrq;
    /**
     * 状态日期
     */
    @ApiModelProperty(value = "状态日期", example = "2018-10-01 12:18:21")
    private Date ztrq;
    /**
     * 房屋状态
     */
    @ApiModelProperty(value = "房屋状态")
    private String fwzt;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
    /**
     * 幢占地面积
     */
    @ApiModelProperty(value = "幢占地面积")
    private String zzdmj;

    /**
     * 地上层数
     */
    @ApiModelProperty(value = "地上层数")
    private Integer dscs;
    /**
     * 地下层数
     */
    @ApiModelProperty(value = "地下层数")
    private Integer dxcs;
    /**
     * 幢用地面积
     */
    @ApiModelProperty(value = "幢用地面积")
    private Double zydmj;

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
     * 产别
     */
    @ApiModelProperty(value = "产别")
    private String cb;

    @ApiModelProperty(value = "调查者")
    private String dcz;

    @ApiModelProperty(value = "调查时间", example = "2018-10-01 12:18:21")
    private Date dcsj;

    @ApiModelProperty(value = "调查意见")
    private String dcyj;

    @ApiModelProperty(value = "产权来源")
    private String cqly;

    @ApiModelProperty(value = "附加说明")
    private String fjsm;

    @Zd(tableClass = SZdGyfsDO.class)
    @ApiModelProperty(value = "共有情况")
    private String gyqk;

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "预售许可证号")
    private String ysxkzh;

    @ApiModelProperty(value = "规划许可证号")
    private String ghxkzh;

    @ApiModelProperty(value = "街道")
    private String street;

    @ApiModelProperty(value = "规划用途名称")
    private String ytmc;

    @ApiModelProperty(value = "批准用途")
    private String pzyt;

    @ApiModelProperty(value = "实际用途")
    private String sjyt;

    @ApiModelProperty(value = "所属区域代码")
    private String xzssqydm;

    @ApiModelProperty(value = "规划验收证明编号")
    private String ghyszmbh;

    @ApiModelProperty(value = "规划验收日期")
    private Date ghysrq;

    @ApiModelProperty(value = "竣工验收备案编号")
    private String jgysbabh;

    @ApiModelProperty(value = "竣工验收备案时间")
    private Date jgysbasj;

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

    public String getDcyj() {
        return dcyj;
    }

    public void setDcyj(String dcyj) {
        this.dcyj = dcyj;
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

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public Integer getDscs() {
        return dscs;
    }

    public void setDscs(Integer dscs) {
        this.dscs = dscs;
    }

    public Integer getDxcs() {
        return dxcs;
    }

    public void setDxcs(Integer dxcs) {
        this.dxcs = dxcs;
    }

    public Double getZydmj() {
        return zydmj;
    }

    public void setZydmj(Double zydmj) {
        this.zydmj = zydmj;
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

    public String getZzdmj() {
        return zzdmj;
    }

    public void setZzdmj(String zzdmj) {
        this.zzdmj = zzdmj;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getFwXmxxIndex() {
        return fwXmxxIndex;
    }

    public void setFwXmxxIndex(String fwXmxxIndex) {
        this.fwXmxxIndex = fwXmxxIndex;
    }

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
    }

    public String getLszd() {
        return lszd;
    }

    public void setLszd(String lszd) {
        this.lszd = lszd;
    }

    public String getZrzh() {
        return zrzh;
    }

    public void setZrzh(String zrzh) {
        this.zrzh = zrzh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getLjzh() {
        return ljzh;
    }

    public void setLjzh(String ljzh) {
        this.ljzh = ljzh;
    }

    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getXmmc() {
        return xmmc;
    }

    public void setXmmc(String xmmc) {
        this.xmmc = xmmc;
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    public String getFwmc() {
        return fwmc;
    }

    public void setFwmc(String fwmc) {
        this.fwmc = fwmc;
    }

    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    public String getZldz() {
        return zldz;
    }

    public void setZldz(String zldz) {
        this.zldz = zldz;
    }

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getFwjg2() {
        return fwjg2;
    }

    public void setFwjg2(String fwjg2) {
        this.fwjg2 = fwjg2;
    }

    public String getFwjg3() {
        return fwjg3;
    }

    public void setFwjg3(String fwjg3) {
        this.fwjg3 = fwjg3;
    }

    public Integer getFwcs() {
        return fwcs;
    }

    public void setFwcs(Integer fwcs) {
        this.fwcs = fwcs;
    }

    public Integer getZts() {
        return zts;
    }

    public void setZts(Integer zts) {
        this.zts = zts;
    }

    public String getFwyt() {
        return fwyt;
    }

    public void setFwyt(String fwyt) {
        this.fwyt = fwyt;
    }

    public String getFwyt2() {
        return fwyt2;
    }

    public void setFwyt2(String fwyt2) {
        this.fwyt2 = fwyt2;
    }

    public String getFwyt3() {
        return fwyt3;
    }

    public void setFwyt3(String fwyt3) {
        this.fwyt3 = fwyt3;
    }

    public Date getJgrq() {
        return jgrq;
    }

    public void setJgrq(Date jgrq) {
        this.jgrq = jgrq;
    }

    public Double getYcjzmj() {
        return ycjzmj;
    }

    public void setYcjzmj(Double ycjzmj) {
        this.ycjzmj = ycjzmj;
    }

    public Double getYcdxmj() {
        return ycdxmj;
    }

    public void setYcdxmj(Double ycdxmj) {
        this.ycdxmj = ycdxmj;
    }

    public Double getYcqtmj() {
        return ycqtmj;
    }

    public void setYcqtmj(Double ycqtmj) {
        this.ycqtmj = ycqtmj;
    }

    public Double getScjzmj() {
        return scjzmj;
    }

    public void setScjzmj(Double scjzmj) {
        this.scjzmj = scjzmj;
    }

    public Double getScdxmj() {
        return scdxmj;
    }

    public void setScdxmj(Double scdxmj) {
        this.scdxmj = scdxmj;
    }

    public Double getScqtmj() {
        return scqtmj;
    }

    public void setScqtmj(Double scqtmj) {
        this.scqtmj = scqtmj;
    }

    public String getBdczt() {
        return bdczt;
    }

    public void setBdczt(String bdczt) {
        this.bdczt = bdczt;
    }

    public String getJzwzt() {
        return jzwzt;
    }

    public void setJzwzt(String jzwzt) {
        this.jzwzt = jzwzt;
    }

    public Date getGxrq() {
        return gxrq;
    }

    public void setGxrq(Date gxrq) {
        this.gxrq = gxrq;
    }

    public Date getZtrq() {
        return ztrq;
    }

    public void setZtrq(Date ztrq) {
        this.ztrq = ztrq;
    }

    public String getFwzt() {
        return fwzt;
    }

    public void setFwzt(String fwzt) {
        this.fwzt = fwzt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getYsxkzh() {
        return ysxkzh;
    }

    public void setYsxkzh(String ysxkzh) {
        this.ysxkzh = ysxkzh;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString() {
        return "FwLjzDO{" +
                "fwDcbIndex='" + fwDcbIndex + '\'' +
                ", fwXmxxIndex='" + fwXmxxIndex + '\'' +
                ", ysdm='" + ysdm + '\'' +
                ", lszd='" + lszd + '\'' +
                ", zrzh='" + zrzh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", ljzh='" + ljzh + '\'' +
                ", bdcdyfwlx='" + bdcdyfwlx + '\'' +
                ", xmmc='" + xmmc + '\'' +
                ", mph='" + mph + '\'' +
                ", fwmc='" + fwmc + '\'' +
                ", dh='" + dh + '\'' +
                ", zldz='" + zldz + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", fwjg2='" + fwjg2 + '\'' +
                ", fwjg3='" + fwjg3 + '\'' +
                ", fwcs=" + fwcs +
                ", zts=" + zts +
                ", fwyt='" + fwyt + '\'' +
                ", fwyt2='" + fwyt2 + '\'' +
                ", fwyt3='" + fwyt3 + '\'' +
                ", jgrq=" + jgrq +
                ", ycjzmj=" + ycjzmj +
                ", ycdxmj=" + ycdxmj +
                ", ycqtmj=" + ycqtmj +
                ", scjzmj=" + scjzmj +
                ", scdxmj=" + scdxmj +
                ", scqtmj=" + scqtmj +
                ", bdczt='" + bdczt + '\'' +
                ", jzwzt='" + jzwzt + '\'' +
                ", gxrq=" + gxrq +
                ", ztrq=" + ztrq +
                ", fwzt='" + fwzt + '\'' +
                ", bz='" + bz + '\'' +
                ", zzdmj='" + zzdmj + '\'' +
                ", dscs=" + dscs +
                ", dxcs=" + dxcs +
                ", zydmj=" + zydmj +
                ", d='" + d + '\'' +
                ", n='" + n + '\'' +
                ", x='" + x + '\'' +
                ", b='" + b + '\'' +
                ", cb='" + cb + '\'' +
                ", dcz='" + dcz + '\'' +
                ", dcsj=" + dcsj +
                ", dcyj='" + dcyj + '\'' +
                ", cqly='" + cqly + '\'' +
                ", fjsm='" + fjsm + '\'' +
                ", gyqk='" + gyqk + '\'' +
                ", slbh='" + slbh + '\'' +
                ", ysxkzh='" + ysxkzh + '\'' +
                ", ghxkzh='" + ghxkzh + '\'' +
                ", street='" + street + '\'' +
                ", ytmc='" + ytmc + '\'' +
                ", pzyt='" + pzyt + '\'' +
                ", sjyt='" + sjyt + '\'' +
                ", xzssqydm='" + xzssqydm + '\'' +
                ", ghyszmbh='" + ghyszmbh + '\'' +
                ", ghysrq=" + ghysrq +
                ", jgysbabh='" + jgysbabh + '\'' +
                ", jgysbasj=" + jgysbasj +
                '}';
    }

    public String getGhxkzh() {
        return ghxkzh;
    }

    public void setGhxkzh(String ghxkzh) {
        this.ghxkzh = ghxkzh;
    }

    public String getYtmc() {
        return ytmc;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public String getPzyt() {
        return pzyt;
    }

    public void setPzyt(String pzyt) {
        this.pzyt = pzyt;
    }

    public String getSjyt() {
        return sjyt;
    }

    public void setSjyt(String sjyt) {
        this.sjyt = sjyt;
    }

    public String getXzssqydm() {
        return xzssqydm;
    }

    public void setXzssqydm(String xzssqydm) {
        this.xzssqydm = xzssqydm;
    }

    public String getGhyszmbh() {
        return ghyszmbh;
    }

    public void setGhyszmbh(String ghyszmbh) {
        this.ghyszmbh = ghyszmbh;
    }

    public Date getGhysrq() {
        return ghysrq;
    }

    public void setGhysrq(Date ghysrq) {
        this.ghysrq = ghysrq;
    }

    public String getJgysbabh() {
        return jgysbabh;
    }

    public void setJgysbabh(String jgysbabh) {
        this.jgysbabh = jgysbabh;
    }

    public Date getJgysbasj() {
        return jgysbasj;
    }

    public void setJgysbasj(Date jgysbasj) {
        this.jgysbasj = jgysbasj;
    }

    public Double getPzjzmj() {
        return pzjzmj;
    }

    public void setPzjzmj(Double pzjzmj) {
        this.pzjzmj = pzjzmj;
    }
}
