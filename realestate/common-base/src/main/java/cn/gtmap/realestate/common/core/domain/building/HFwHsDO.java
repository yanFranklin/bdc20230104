package cn.gtmap.realestate.common.core.domain.building;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import cn.gtmap.realestate.common.core.annotations.SaveBdcdyhZt;
import cn.gtmap.realestate.common.core.annotations.Zd;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-10-27
 * @description
 */
@Table(name="h_fw_hs")
public class HFwHsDO {
    /**
     * 逻辑幢主键
     */
    @RequiredFk
    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;
    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键")
    private String fwHsIndex;
    /**
     * 房间不动产单元编号
     */
    @ApiModelProperty(value = "房间不动产单元编号")
    @SaveBdcdyhZt
    private String bdcdyh;
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
     * 预测其它建筑面积
     */
    @ApiModelProperty(value = "预测其它建筑面积")
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
     * 实测套内建筑面积
     */
    @ApiModelProperty(value = "实测套内建筑面积")
    private Double sctnjzmj;
    /**
     * 实测分摊建筑面积
     */
    @ApiModelProperty(value = "实测分摊建筑面积")
    private Double scftjzmj;
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
     * 交易价格
     */
    @ApiModelProperty(value = "交易价格")
    private Double jyjg;
    /**
     * 坐落
     */
    @ApiModelProperty(value = "坐落")
    @SaveBdcdyhZt
    private String zl;
    /**
     * 规划用途
     */
    @Zd(tableClass = SZdFwytDO.class)
    @ApiModelProperty(value = "规划用途")
    @SaveBdcdyhZt("yt")
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
    @Zd(tableClass = SZdFwlxDO.class)
    @ApiModelProperty(value = "房屋类型")
    private String fwlx;
    /**
     * 房屋性质
     */
    @Zd(tableClass = SZdFwxzDO.class)
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
     */
    @ApiModelProperty(value = "要素代码")
    private String ysdm;
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
    @Zd(tableClass = SZdJczxcdDO.class)
    @ApiModelProperty(value = "建成时装修程度")
    private String jczxcd;
    /**
     * 户型结构
     */
    @Zd(tableClass = SZdHxjgDO.class)
    @ApiModelProperty(value = "户型结构")
    private String hxjg;
    /**
     * 房产档案号
     */
    @ApiModelProperty(value = "房产档案号")
    private String fcdah;
    /**
     * 权利状态
     */
    @Zd(tableClass = SZdQlztDO.class)
    @ApiModelProperty(value = "权利状态")
    private String qlzt;
    /**
     * 合并方向
     */
    @ApiModelProperty(value = "合并方向")
    private String hbfx;
    /**
     * 合并户数
     */
    @ApiModelProperty(value = "合并户数")
    private Integer hbhs;
    /**
     * 共有情况
     */
    @Zd(tableClass = SZdGyfsDO.class)
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    /**
     * 是否从属编号
     */
    @Zd(tableClass = SZdBoolDO.class)
    @ApiModelProperty(value = "是否从属编号")
    private String sfcsbh;
    /**
     * 户室图主键
     */
    @ApiModelProperty(value = "户室图主键")
    private String fwHstIndex;
    /**
     * 不动产状态
     */
    @ApiModelProperty(value = "不动产状态")
    @SaveBdcdyhZt("bdcdyzt")
    private String bdczt;
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;
    /**
     * 房屋编码
     */
    @ApiModelProperty(value = "房屋编码")
    private String fwbm;

    /**
     * 起始日期
     */
    @ApiModelProperty(value = "起始日期",example = "2018-10-01 12:18:48")
    private Date qsrq;

    /**
     * 终止日期
     */
    @ApiModelProperty(value = "终止日期",example = "2018-10-01 12:18:48")
    private Date zzrq;

    /**
     * 是否参与分摊土地面积计算
     */
    @ApiModelProperty(value = "是否参与分摊土地面积计算")
    private String syfttdmjjs;

    /**
     * 土地使用权类型
     */
    @Zd(tableClass = SZdTdsyqlxDO.class)
    @ApiModelProperty(value = "土地使用权类型")
    private String tdsyqlx;

    /**
     * 土地用途
     */
    @ApiModelProperty(value = "土地用途")
    private String tdyt;

    /**
     * 是否附属设施
     */
    @Zd(tableClass = SZdBoolDO.class)
    @ApiModelProperty(value = "是否附属设施")
    private String isfsss;

    /**
     * 产别
     */
    @ApiModelProperty(value = "产别")
    private String cb;

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
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

    public Double getSctnjzmj() {
        return sctnjzmj;
    }

    public void setSctnjzmj(Double sctnjzmj) {
        this.sctnjzmj = sctnjzmj;
    }

    public Double getScftjzmj() {
        return scftjzmj;
    }

    public void setScftjzmj(Double scftjzmj) {
        this.scftjzmj = scftjzmj;
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

    public Double getJyjg() {
        return jyjg;
    }

    public void setJyjg(Double jyjg) {
        this.jyjg = jyjg;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
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

    public String getYsdm() {
        return ysdm;
    }

    public void setYsdm(String ysdm) {
        this.ysdm = ysdm;
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

    public String getFcdah() {
        return fcdah;
    }

    public void setFcdah(String fcdah) {
        this.fcdah = fcdah;
    }

    public String getQlzt() {
        return qlzt;
    }

    public void setQlzt(String qlzt) {
        this.qlzt = qlzt;
    }

    public String getHbfx() {
        return hbfx;
    }

    public void setHbfx(String hbfx) {
        this.hbfx = hbfx;
    }

    public Integer getHbhs() {
        return hbhs;
    }

    public void setHbhs(Integer hbhs) {
        this.hbhs = hbhs;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getSfcsbh() {
        return sfcsbh;
    }

    public void setSfcsbh(String sfcsbh) {
        this.sfcsbh = sfcsbh;
    }

    public String getFwHstIndex() {
        return fwHstIndex;
    }

    public void setFwHstIndex(String fwHstIndex) {
        this.fwHstIndex = fwHstIndex;
    }

    public String getBdczt() {
        return bdczt;
    }

    public void setBdczt(String bdczt) {
        this.bdczt = bdczt;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

    public Date getQsrq() {
        return qsrq;
    }

    public void setQsrq(Date qsrq) {
        this.qsrq = qsrq;
    }

    public Date getZzrq() {
        return zzrq;
    }

    public void setZzrq(Date zzrq) {
        this.zzrq = zzrq;
    }

    public String getSyfttdmjjs() {
        return syfttdmjjs;
    }

    public void setSyfttdmjjs(String syfttdmjjs) {
        this.syfttdmjjs = syfttdmjjs;
    }

    public String getTdsyqlx() {
        return tdsyqlx;
    }

    public void setTdsyqlx(String tdsyqlx) {
        this.tdsyqlx = tdsyqlx;
    }

    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    public String getIsfsss() {
        return isfsss;
    }

    public void setIsfsss(String isfsss) {
        this.isfsss = isfsss;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HFwHsDO{");
        sb.append("fwDcbIndex='").append(fwDcbIndex).append('\'');
        sb.append(", fwHsIndex='").append(fwHsIndex).append('\'');
        sb.append(", bdcdyh='").append(bdcdyh).append('\'');
        sb.append(", wlcs=").append(wlcs);
        sb.append(", fjh='").append(fjh).append('\'');
        sb.append(", dycs='").append(dycs).append('\'');
        sb.append(", dyh='").append(dyh).append('\'');
        sb.append(", sxh=").append(sxh);
        sb.append(", ch=").append(ch);
        sb.append(", cg=").append(cg);
        sb.append(", qlid='").append(qlid).append('\'');
        sb.append(", gytdmj=").append(gytdmj);
        sb.append(", fttdmj=").append(fttdmj);
        sb.append(", dytdmj=").append(dytdmj);
        sb.append(", ycjzmj=").append(ycjzmj);
        sb.append(", yctnjzmj=").append(yctnjzmj);
        sb.append(", ycftjzmj=").append(ycftjzmj);
        sb.append(", ycdxbfjzmj=").append(ycdxbfjzmj);
        sb.append(", ycqtjzmj=").append(ycqtjzmj);
        sb.append(", ycftxs=").append(ycftxs);
        sb.append(", scjzmj=").append(scjzmj);
        sb.append(", sctnjzmj=").append(sctnjzmj);
        sb.append(", scftjzmj=").append(scftjzmj);
        sb.append(", scdxbfjzmj=").append(scdxbfjzmj);
        sb.append(", scqtjzmj=").append(scqtjzmj);
        sb.append(", scftxs=").append(scftxs);
        sb.append(", jyjg=").append(jyjg);
        sb.append(", zl='").append(zl).append('\'');
        sb.append(", ghyt='").append(ghyt).append('\'');
        sb.append(", ghyt2='").append(ghyt2).append('\'');
        sb.append(", ghyt3='").append(ghyt3).append('\'');
        sb.append(", fwlx='").append(fwlx).append('\'');
        sb.append(", fwxz='").append(fwxz).append('\'');
        sb.append(", d='").append(d).append('\'');
        sb.append(", n='").append(n).append('\'');
        sb.append(", x='").append(x).append('\'');
        sb.append(", b='").append(b).append('\'');
        sb.append(", cqly='").append(cqly).append('\'');
        sb.append(", ysdm='").append(ysdm).append('\'');
        sb.append(", fjsm='").append(fjsm).append('\'');
        sb.append(", dcyj='").append(dcyj).append('\'');
        sb.append(", dcz='").append(dcz).append('\'');
        sb.append(", dcsj=").append(dcsj);
        sb.append(", fwhx='").append(fwhx).append('\'');
        sb.append(", jczxcd='").append(jczxcd).append('\'');
        sb.append(", hxjg='").append(hxjg).append('\'');
        sb.append(", fcdah='").append(fcdah).append('\'');
        sb.append(", qlzt='").append(qlzt).append('\'');
        sb.append(", hbfx='").append(hbfx).append('\'');
        sb.append(", hbhs=").append(hbhs);
        sb.append(", gyqk='").append(gyqk).append('\'');
        sb.append(", sfcsbh='").append(sfcsbh).append('\'');
        sb.append(", fwHstIndex='").append(fwHstIndex).append('\'');
        sb.append(", bdczt='").append(bdczt).append('\'');
        sb.append(", bgbh='").append(bgbh).append('\'');
        sb.append(", fwbm='").append(fwbm).append('\'');
        sb.append(", qsrq=").append(qsrq);
        sb.append(", zzrq=").append(zzrq);
        sb.append(", syfttdmjjs='").append(syfttdmjjs).append('\'');
        sb.append(", tdsyqlx='").append(tdsyqlx).append('\'');
        sb.append(", tdyt='").append(tdyt).append('\'');
        sb.append(", isfsss='").append(isfsss).append('\'');
        sb.append(", cb='").append(cb).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
