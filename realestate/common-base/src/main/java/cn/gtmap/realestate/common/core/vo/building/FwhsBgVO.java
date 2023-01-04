package cn.gtmap.realestate.common.core.vo.building;

import cn.gtmap.realestate.common.core.annotations.Zd;
import cn.gtmap.realestate.common.core.domain.building.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-22
 * @description
 */
public class FwhsBgVO {

    /**
     * 主键
     */
    @NotBlank(message = "房屋户室主键不能为空")
    private String fwHsIndex;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    @NotBlank(message = "变更编号不能为空")
    private String bgbh;

    /**
     * 房间号
     */
    private String fjh;
    /**
     * 定义层数
     */
    private String dycs;
    /**
     * 单元号
     */
    private String dyh;
    /**
     * 室序号
     */
    private Integer sxh;
    /**
     * 层号
     */
    private Integer ch;
    /**
     * 层高
     */
    private Double cg;
    /**
     * 权利ID
     */
    private String qlid;
    /**
     * 共有土地面积
     */
    private Double gytdmj;
    /**
     * 分摊土地面积
     */
    private Double fttdmj;
    /**
     * 独用土地面积
     */
    private Double dytdmj;
    /**
     * 预测建筑面积
     */
    private Double ycjzmj;
    /**
     * 预测套内建筑面积
     */
    private Double yctnjzmj;
    /**
     * 预测分摊建筑面积
     */
    private Double ycftjzmj;
    /**
     * 预测地下部分建筑面积
     */
    private Double ycdxbfjzmj;
    /**
     * 预测其它建筑面积
     */
    private Double ycqtjzmj;
    /**
     * 预测分摊系数
     */
    private Double ycftxs;
    /**
     * 实测建筑面积
     */
    private Double scjzmj;
    /**
     * 实测套内建筑面积
     */
    private Double sctnjzmj;
    /**
     * 实测分摊建筑面积
     */
    private Double scftjzmj;
    /**
     * 实测地下部分建筑面积
     */
    private Double scdxbfjzmj;
    /**
     * 实测其它建筑面积
     */
    private Double scqtjzmj;
    /**
     * 实测分摊系数
     */
    private Double scftxs;
    /**
     * 交易价格
     */
    private Double jyjg;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 规划用途
     */
    @Zd(tableClass = SZdFwytDO.class)
    private String ghyt;
    /**
     * 房屋类型
     */
    @Zd(tableClass = SZdFwlxDO.class)
    private String fwlx;
    /**
     * 房屋性质
     */
    @Zd(tableClass = SZdFwxzDO.class)
    private String fwxz;
    /**
     * 东
     */
    private String d;
    /**
     * 南
     */
    private String n;
    /**
     * 西
     */
    private String x;
    /**
     * 北
     */
    private String b;

    /**
     * 产权来源
     */
    private String cqly;
    /**
     * 建成时装修程度
     */
    @Zd(tableClass = SZdJczxcdDO.class)
    private String jczxcd;
    /**
     * 户型结构
     */
    @Zd(tableClass = SZdHxjgDO.class)
    private String hxjg;
    /**
     * 房产档案号
     */
    private String fcdah;
    /**
     * 权利状态
     */
    @Zd(tableClass = SZdQlztDO.class)
    private String qlzt;

    /**
     * 房屋户型
     */
    private String fwhx;
    /**
     * 备注
     */
    private String bz;
    /**
     * 房屋编码
     */
    private String fwbm;

    /**
     * 起始日期
     */
    private Date qsrq;

    /**
     * 终止日期
     */
    private Date zzrq;

    /**
     * 土地使用权类型
     */
    @Zd(tableClass = SZdTdsyqlxDO.class)
    private String tdsyqlx;

    /**
     * 土地用途
     */
    private String tdyt;

    /**
     * 产别
     */
    private String cb;

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

    public String getFwhx() {
        return fwhx;
    }

    public void setFwhx(String fwhx) {
        this.fwhx = fwhx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
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

    public String getCb() {
        return cb;
    }

    public void setCb(String cb) {
        this.cb = cb;
    }

    public String getFwHsIndex() {
        return fwHsIndex;
    }

    public void setFwHsIndex(String fwHsIndex) {
        this.fwHsIndex = fwHsIndex;
    }
}
