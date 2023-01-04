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
@Table(name = "fw_hs")
@ApiModel(value = "FwHsDO", description = "户室基本信息实体")
public class FwHsDO {

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
    private String ch;
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
    @ApiModelProperty(value = "实测其他建筑面积")
    private Double scqtjzmj;
    /**
     * 批准建筑面积
     */
    @ApiModelProperty(value = "批准建筑面积")
    private Double pzjzmj;
    /**
     * 实测分摊系数
     */
    @ApiModelProperty(value = "实测分摊系数")
    private Double scftxs;
    /**
     * 交易价格(万元)
     */
    @ApiModelProperty(value = "交易价格(万元)")
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
    @Zd(tableClass = SZdQtgsDO.class)
    @ApiModelProperty(value = "东")
    private String d;
    /**
     * 南
     */
    @Zd(tableClass = SZdQtgsDO.class)
    @ApiModelProperty(value = "南")
    private String n;
    /**
     * 西
     */
    @Zd(tableClass = SZdQtgsDO.class)
    @ApiModelProperty(value = "西")
    private String x;
    /**
     * 北
     */
    @Zd(tableClass = SZdQtgsDO.class)
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

    @Zd(tableClass = SZdFwhxDO.class)
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
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String bz;
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

    @Zd(tableClass = SZdSyfttdmjjsDO.class)
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
    /**
     * 预测房屋编码
     */
    @ApiModelProperty(value = "预测房屋编码")
    private String ycfwbm;

    /**
     * 预售房屋编码（房产系统的）
     */
    @ApiModelProperty(value = "房产预售房屋编码")
    private String ysfwbm;

    @ApiModelProperty(value = "起始名义层")
    private Integer qsmyc;

    @ApiModelProperty(value = "终止名义层")
    private Integer zzmyc;

    @ApiModelProperty(value = "修改原因")
    private String xgyy;

    @Zd(tableClass = SZdFwjgDO.class)
    @ApiModelProperty(value = "房屋结构")
    private String fwjg;

    @ApiModelProperty(value = "规划用途名称")
    private String ytmc;

    @ApiModelProperty(value = "批准用途")
    private String pzyt;

    @ApiModelProperty(value = "实际用途")
    private String sjyt;

    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;

    @ApiModelProperty(value = "所属区域代码")
    private String xzssqydm;

    @ApiModelProperty(value = "行政区")
    private String xzq;

    @ApiModelProperty(value = "土地抵押是否释放")
    private String tddysfsf;

    @ApiModelProperty(value = "土地抵押释放时间")
    private Date tddysfsj;

    @ApiModelProperty(value = "土地抵押释放操作人")
    private String tddysfczr;

    public String getYsfwbm() {
        return ysfwbm;
    }

    public void setYsfwbm(String ysfwbm) {
        this.ysfwbm = ysfwbm;
    }

    public String getYcfwbm() {
        return ycfwbm;
    }

    public void setYcfwbm(String ycfwbm) {
        this.ycfwbm = ycfwbm;
    }

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
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

    public String getFwbm() {
        return fwbm;
    }

    public void setFwbm(String fwbm) {
        this.fwbm = fwbm;
    }

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

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
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

    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    public String getXzssqydm() {
        return xzssqydm;
    }

    public void setXzssqydm(String xzssqydm) {
        this.xzssqydm = xzssqydm;
    }

    public String getXzq() {
        return xzq;
    }

    public void setXzq(String xzq) {
        this.xzq = xzq;
    }

    public Double getPzjzmj() {
        return pzjzmj;
    }

    public void setPzjzmj(Double pzjzmj) {
        this.pzjzmj = pzjzmj;
    }

    public Integer getQsmyc() {
        return qsmyc;
    }

    public void setQsmyc(Integer qsmyc) {
        this.qsmyc = qsmyc;
    }

    public Integer getZzmyc() {
        return zzmyc;
    }

    public void setZzmyc(Integer zzmyc) {
        this.zzmyc = zzmyc;
    }

    public String getXgyy() {
        return xgyy;
    }

    public void setXgyy(String xgyy) {
        this.xgyy = xgyy;
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

    public String getFwxzmc() {
        return fwxzmc;
    }

    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

    public String getTddysfsf() {
        return tddysfsf;
    }

    public void setTddysfsf(String tddysfsf) {
        this.tddysfsf = tddysfsf;
    }

    public Date getTddysfsj() {
        return tddysfsj;
    }

    public void setTddysfsj(Date tddysfsj) {
        this.tddysfsj = tddysfsj;
    }

    public String getTddysfczr() {
        return tddysfczr;
    }

    public void setTddysfczr(String tddysfczr) {
        this.tddysfczr = tddysfczr;
    }

    @Override
    public String toString() {
        return "FwHsDO{" +
                "fwDcbIndex='" + fwDcbIndex + '\'' +
                ", fwHsIndex='" + fwHsIndex + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", wlcs=" + wlcs +
                ", fjh='" + fjh + '\'' +
                ", dycs='" + dycs + '\'' +
                ", dyh='" + dyh + '\'' +
                ", sxh=" + sxh +
                ", ch='" + ch + '\'' +
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
                ", sctnjzmj=" + sctnjzmj +
                ", scftjzmj=" + scftjzmj +
                ", scdxbfjzmj=" + scdxbfjzmj +
                ", scqtjzmj=" + scqtjzmj +
                ", pzjzmj=" + pzjzmj +
                ", scftxs=" + scftxs +
                ", jyjg=" + jyjg +
                ", zl='" + zl + '\'' +
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
                ", ysdm='" + ysdm + '\'' +
                ", fjsm='" + fjsm + '\'' +
                ", dcyj='" + dcyj + '\'' +
                ", dcz='" + dcz + '\'' +
                ", dcsj=" + dcsj +
                ", fwhx='" + fwhx + '\'' +
                ", jczxcd='" + jczxcd + '\'' +
                ", hxjg='" + hxjg + '\'' +
                ", fcdah='" + fcdah + '\'' +
                ", qlzt='" + qlzt + '\'' +
                ", hbfx='" + hbfx + '\'' +
                ", hbhs=" + hbhs +
                ", gyqk='" + gyqk + '\'' +
                ", sfcsbh='" + sfcsbh + '\'' +
                ", fwHstIndex='" + fwHstIndex + '\'' +
                ", bdczt='" + bdczt + '\'' +
                ", bz='" + bz + '\'' +
                ", bgbh='" + bgbh + '\'' +
                ", fwbm='" + fwbm + '\'' +
                ", qsrq=" + qsrq +
                ", zzrq=" + zzrq +
                ", syfttdmjjs='" + syfttdmjjs + '\'' +
                ", tdsyqlx='" + tdsyqlx + '\'' +
                ", tdyt='" + tdyt + '\'' +
                ", isfsss='" + isfsss + '\'' +
                ", cb='" + cb + '\'' +
                ", ycfwbm='" + ycfwbm + '\'' +
                ", ysfwbm='" + ysfwbm + '\'' +
                ", qsmyc=" + qsmyc +
                ", zzmyc=" + zzmyc +
                ", xgyy='" + xgyy + '\'' +
                ", fwjg='" + fwjg + '\'' +
                ", ytmc='" + ytmc + '\'' +
                ", pzyt='" + pzyt + '\'' +
                ", sjyt='" + sjyt + '\'' +
                ", fwxzmc='" + fwxzmc + '\'' +
                ", xzssqydm='" + xzssqydm + '\'' +
                ", xzq='" + xzq + '\'' +
                ", tddysfsf='" + tddysfsf + '\'' +
                ", tddysfsj=" + tddysfsj +
                ", tddysfczr='" + tddysfczr + '\'' +
                '}';
    }
}
