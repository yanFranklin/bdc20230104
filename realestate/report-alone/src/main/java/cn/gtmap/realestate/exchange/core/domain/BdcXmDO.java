package cn.gtmap.realestate.exchange.core.domain;

import cn.gtmap.realestate.exchange.core.annotations.Zd;
import cn.gtmap.realestate.exchange.core.domain.zd.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @description 不动产项目
 */
@Table(
        name = "BDC_XM"
)
@ApiModel(value = "BdcXmDO",description = "不动产项目")
public class BdcXmDO {
    @Id
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @Zd(tableClass = BdcZdQllxDO.class)
    @ApiModelProperty(value = "权利类型")
    private Integer qllx;
    @Zd(tableClass = BdcZdDjlxDO.class)
    @ApiModelProperty(value = "登记类型")
    private Integer djlx;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "申请证书版式")
    private Integer sqzsbs;
    @ApiModelProperty(value = "原不动产单元号")
    private String ybdcdyh;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "申请分别持证")
    private Integer sqfbcz;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "申请人说明")
    private String sqrsm;
    @Zd(tableClass = BdcZdAjztDO.class)
    @ApiModelProperty(value = "案件状态")
    private Integer ajzt;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "是否问题案件")
    private Integer sfwtaj;
    @ApiModelProperty(value = "工作流节点名称")
    private String gzljdmc;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "受理时间",example = "2018-10-01 12:18:48")
    private Date slsj;
    @ApiModelProperty(value = "受理人ID")
    private String slrid;
    @ApiModelProperty(value = "受理人")
    private String slr;
    @Zd(tableClass = BdcZdXmlyDO.class)
    @ApiModelProperty(value = "项目来源")
    private Integer xmly;
    @ApiModelProperty(value = "结束时间",example = "2018-10-01 12:18:48")
    private Date jssj;
    @Zd(tableClass = BdcZdDjxlDO.class)
    @ApiModelProperty(value = "登记小类")
    private String djxl;
    @Zd(tableClass = BdcZdBdclxDO.class)
    @ApiModelProperty(value = "不动产类型")
    private Integer bdclx;
    @ApiModelProperty(value = "原产权证号")
    private String ycqzh;
    @ApiModelProperty(value = "原房产证号")
    private String yfczh;
    @ApiModelProperty(value = "原土地证号")
    private String ytdzh;
    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;
    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;
    @ApiModelProperty(value = "所属乡镇")
    private String ssxz;
    @ApiModelProperty(value = "登记机构")
    private String djjg;
    @ApiModelProperty(value = "审批系统办理状态")
    private Integer spxtblzt;
    @ApiModelProperty(value = "审批系统办理状态名称")
    private String spxtblztmc;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "不动产唯一编码")
    private String bdcdywybh;
    @Zd(tableClass = BdcZdGyfsDO.class)
    @ApiModelProperty(value = "共有方式")
    private Integer gyfs;
    @ApiModelProperty(value = "登记时间",example = "2018-10-01 12:18:48")
    private Date djsj;
    @ApiModelProperty(value = "登簿人")
    private String dbr;
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "定着物面积")
    private Double dzwmj;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "定着物用途")
    private Integer dzwyt;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "定着物用途2")
    private Integer dzwyt2;
    @Zd(tableClass = BdcZdFwytDO.class)
    @ApiModelProperty(value = "定着物用途3")
    private Integer dzwyt3;
    @Zd(tableClass = BdcZdHysylxaDO.class)
    @ApiModelProperty(value = "用海类型A")
    private Integer yhlxa;
    @Zd(tableClass = BdcZdHysylxbDO.class)
    @ApiModelProperty(value = "用海类型B")
    private Integer yhlxb;
    @Zd(tableClass = BdcZdGjzwlxDO.class)
    @ApiModelProperty(value = "构筑物类型")
    private Integer gzwlx;
    @Zd(tableClass = BdcZdLzDO.class)
    @ApiModelProperty(value = "林种")
    private Integer lz;
    @Zd(tableClass = BdcZdMjdwDO.class)
    @ApiModelProperty(value = "面积单位")
    private Integer mjdw;
    @ApiModelProperty(value = "宗地宗海面积")
    private Double zdzhmj;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;
    @ApiModelProperty(value = "义务人")
    private String ywr;
    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;
    @ApiModelProperty(value = "交易合同号")
    private String jyhth;
    @Zd(tableClass = BdcZdBdcdyfwlxDO.class)
    @ApiModelProperty(value = "不动产单元房屋类型")
    private Integer bdcdyfwlx;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @Zd(tableClass = BdcZdQlxzDO.class)
    @ApiModelProperty(value = "权利性质")
    private String qlxz;
    @Zd(tableClass = BdcZdSfDO.class)
    @ApiModelProperty(value = "存量数据匹配状态")
    private Integer clsjppzt;
    @ApiModelProperty(value = "承诺期限")
    private String cnqx;
    @ApiModelProperty(value = "工作流定义名称")
    private String gzldymc;
    @ApiModelProperty(value = "工作流定义ID")
    private String gzldyid;
    @ApiModelProperty(value = "权利人证件种类")
    private String qlrzjzl;
    @ApiModelProperty(value = "义务人证件种类")
    private String ywrzjzl;
    @ApiModelProperty(value = "权利人类型")
    private String qlrlx;
    @ApiModelProperty(value = "原土地用途名称")
    private String ytdytmc;
    @ApiModelProperty(value = "登记部门代码")
    private String djbmdm;
    @Zd(tableClass = BdcZdTdytDO.class)
    @ApiModelProperty(value = "宗地宗海用途")
    private String zdzhyt;
    @Zd(tableClass = BdcZdTdytDO.class)
    @ApiModelProperty(value = "宗地宗海用途2")
    private String zdzhyt2;
    @Zd(tableClass = BdcZdTdytDO.class)
    @ApiModelProperty(value = "宗地宗海用途3")
    private String zdzhyt3;
    @ApiModelProperty(value = "部分权利其他状况")
    private String bfqlqtzk;
    @ApiModelProperty(value = "房产预售房屋编码")
    private String ysfwbm;
    @ApiModelProperty(value = "政府行政审批编号 备注：合肥版本该字段用于存储从政务获取的办件编号，对应需求34937")
    private String zfxzspbh;
    @ApiModelProperty(value = "原系统产权证号")
    private String yxtcqzh;
    @ApiModelProperty(value = "审批来源")
    private Integer sply;
    @ApiModelProperty(value = "税务房源编号，缴税时发票提供的房源编号")
    private String swfybh;
    @ApiModelProperty(value = "是否一并申请增量房转移业务")
    private Integer zydjybsq;

    public Integer getSply() {
        return sply;
    }

    public void setSply(Integer sply) {
        this.sply = sply;
    }

    public String getZfxzspbh() {
        return zfxzspbh;
    }

    public void setZfxzspbh(String zfxzspbh) {
        this.zfxzspbh = zfxzspbh;
    }

    public String getYsfwbm() {
        return ysfwbm;
    }

    public void setYsfwbm(String ysfwbm) {
        this.ysfwbm = ysfwbm;
    }

    public String getBfqlqtzk() {
        return bfqlqtzk;
    }

    public void setBfqlqtzk(String bfqlqtzk) {
        this.bfqlqtzk = bfqlqtzk;
    }

    public String getSwfybh() {
        return swfybh;
    }

    public void setSwfybh(String swfybh) {
        this.swfybh = swfybh;
    }

    public String getZdzhyt() {
        return zdzhyt;
    }

    public String getZdzhyt2() {
        return zdzhyt2;
    }

    public void setZdzhyt2(String zdzhyt2) {
        this.zdzhyt2 = zdzhyt2;
    }

    @Override
    public String toString() {
        return "BdcXmDO{" +
                "xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qllx=" + qllx +
                ", djlx=" + djlx +
                ", djyy='" + djyy + '\'' +
                ", sqzsbs=" + sqzsbs +
                ", ybdcdyh='" + ybdcdyh + '\'' +
                ", sqfbcz=" + sqfbcz +
                ", bz='" + bz + '\'' +
                ", sqrsm='" + sqrsm + '\'' +
                ", ajzt=" + ajzt +
                ", sfwtaj=" + sfwtaj +
                ", gzljdmc='" + gzljdmc + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", slsj=" + slsj +
                ", slrid='" + slrid + '\'' +
                ", slr='" + slr + '\'' +
                ", xmly=" + xmly +
                ", jssj=" + jssj +
                ", djxl='" + djxl + '\'' +
                ", bdclx=" + bdclx +
                ", ycqzh='" + ycqzh + '\'' +
                ", yfczh='" + yfczh + '\'' +
                ", ytdzh='" + ytdzh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", spxtywh='" + spxtywh + '\'' +
                ", ssxz='" + ssxz + '\'' +
                ", djjg='" + djjg + '\'' +
                ", spxtblzt=" + spxtblzt +
                ", spxtblztmc='" + spxtblztmc + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", bdcdywybh='" + bdcdywybh + '\'' +
                ", gyfs=" + gyfs +
                ", djsj=" + djsj +
                ", dbr='" + dbr + '\'' +
                ", qszt=" + qszt +
                ", zl='" + zl + '\'' +
                ", dzwmj=" + dzwmj +
                ", dzwyt=" + dzwyt +
                ", dzwyt2=" + dzwyt2 +
                ", dzwyt3=" + dzwyt3 +
                ", yhlxa=" + yhlxa +
                ", yhlxb=" + yhlxb +
                ", gzwlx=" + gzwlx +
                ", lz=" + lz +
                ", mjdw=" + mjdw +
                ", zdzhmj=" + zdzhmj +
                ", qlr='" + qlr + '\'' +
                ", qlrzjh='" + qlrzjh + '\'' +
                ", ywr='" + ywr + '\'' +
                ", ywrzjh='" + ywrzjh + '\'' +
                ", jyhth='" + jyhth + '\'' +
                ", bdcdyfwlx=" + bdcdyfwlx +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", qlxz='" + qlxz + '\'' +
                ", clsjppzt=" + clsjppzt +
                ", cnqx=" + cnqx +
                ", gzldymc='" + gzldymc + '\'' +
                ", gzldyid='" + gzldyid + '\'' +
                ", qlrzjzl='" + qlrzjzl + '\'' +
                ", ywrzjzl='" + ywrzjzl + '\'' +
                ", qlrlx='" + qlrlx + '\'' +
                ", ytdytmc='" + ytdytmc + '\'' +
                ", djbmdm='" + djbmdm + '\'' +
                ", zdzhyt='" + zdzhyt + '\'' +
                ", zdzhyt2='" + zdzhyt2 + '\'' +
                ", zdzhyt3='" + zdzhyt3 + '\'' +
                ", bfqlqtzk='" + bfqlqtzk + '\'' +
                ", ysfwbm='" + ysfwbm + '\'' +
                ", zfxzspbh='" + zfxzspbh + '\'' +
                ", yxtcqzh='" + yxtcqzh + '\'' +
                ", sply=" + sply +
                ", swfybh='" + swfybh + '\'' +
                ", zydjybsq='" + zydjybsq + '\'' +
                '}';
    }

    public String getZdzhyt3() {
        return zdzhyt3;
    }

    public void setZdzhyt3(String zdzhyt3) {
        this.zdzhyt3 = zdzhyt3;
    }


    public void setZdzhyt(String zdzhyt) {
        this.zdzhyt = zdzhyt;
    }

    public String getYbdcdyh() {
        return ybdcdyh;
    }

    public void setYbdcdyh(String ybdcdyh) {
        this.ybdcdyh = ybdcdyh;
    }

    public String getDjbmdm() {
        return djbmdm;
    }

    public void setDjbmdm(String djbmdm) {
        this.djbmdm = djbmdm;
    }

    public String getYtdytmc() {
        return ytdytmc;
    }

    public void setYtdytmc(String ytdytmc) {
        this.ytdytmc = ytdytmc;
    }

    public Integer getClsjppzt() {
        return clsjppzt;
    }

    public void setClsjppzt(Integer clsjppzt) {
        this.clsjppzt = clsjppzt;
    }

    public Integer getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(Integer bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getSqzsbs() {
        return sqzsbs;
    }

    public void setSqzsbs(Integer sqzsbs) {
        this.sqzsbs = sqzsbs;
    }

    public Integer getSqfbcz() {
        return sqfbcz;
    }

    public void setSqfbcz(Integer sqfbcz) {
        this.sqfbcz = sqfbcz;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getSqrsm() {
        return sqrsm;
    }

    public void setSqrsm(String sqrsm) {
        this.sqrsm = sqrsm;
    }

    public Integer getAjzt() {
        return ajzt;
    }

    public void setAjzt(Integer ajzt) {
        this.ajzt = ajzt;
    }

    public Integer getSfwtaj() {
        return sfwtaj;
    }

    public void setSfwtaj(Integer sfwtaj) {
        this.sfwtaj = sfwtaj;
    }

    public String getGzljdmc() {
        return gzljdmc;
    }

    public void setGzljdmc(String gzljdmc) {
        this.gzljdmc = gzljdmc;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getSlrid() {
        return slrid;
    }

    public void setSlrid(String slrid) {
        this.slrid = slrid;
    }

    public String getSlr() {
        return slr;
    }

    public void setSlr(String slr) {
        this.slr = slr;
    }

    public Integer getXmly() {
        return xmly;
    }

    public void setXmly(Integer xmly) {
        this.xmly = xmly;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public Integer getBdclx() {
        return bdclx;
    }

    public void setBdclx(Integer bdclx) {
        this.bdclx = bdclx;
    }

    public String getYcqzh() {
        return ycqzh;
    }

    public void setYcqzh(String ycqzh) {
        this.ycqzh = ycqzh;
    }

    public String getYfczh() {
        return yfczh;
    }

    public void setYfczh(String yfczh) {
        this.yfczh = yfczh;
    }

    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    public String getSsxz() {
        return ssxz;
    }

    public void setSsxz(String ssxz) {
        this.ssxz = ssxz;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public Integer getSpxtblzt() {
        return spxtblzt;
    }

    public void setSpxtblzt(Integer spxtblzt) {
        this.spxtblzt = spxtblzt;
    }

    public String getSpxtblztmc() {
        return spxtblztmc;
    }

    public void setSpxtblztmc(String spxtblztmc) {
        this.spxtblztmc = spxtblztmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public Integer getGyfs() {
        return gyfs;
    }

    public void setGyfs(Integer gyfs) {
        this.gyfs = gyfs;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getDzwmj() {
        return dzwmj;
    }

    public void setDzwmj(Double dzwmj) {
        this.dzwmj = dzwmj;
    }

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public Integer getDzwyt2() {
        return dzwyt2;
    }

    public void setDzwyt2(Integer dzwyt2) {
        this.dzwyt2 = dzwyt2;
    }

    public Integer getDzwyt3() {
        return dzwyt3;
    }

    public void setDzwyt3(Integer dzwyt3) {
        this.dzwyt3 = dzwyt3;
    }

    public Integer getYhlxa() {
        return yhlxa;
    }

    public void setYhlxa(Integer yhlxa) {
        this.yhlxa = yhlxa;
    }

    public Integer getYhlxb() {
        return yhlxb;
    }

    public void setYhlxb(Integer yhlxb) {
        this.yhlxb = yhlxb;
    }

    public Integer getGzwlx() {
        return gzwlx;
    }

    public void setGzwlx(Integer gzwlx) {
        this.gzwlx = gzwlx;
    }

    public Integer getLz() {
        return lz;
    }

    public void setLz(Integer lz) {
        this.lz = lz;
    }

    public Integer getMjdw() {
        return mjdw;
    }

    public void setMjdw(Integer mjdw) {
        this.mjdw = mjdw;
    }

    public Double getZdzhmj() {
        return zdzhmj;
    }

    public void setZdzhmj(Double zdzhmj) {
        this.zdzhmj = zdzhmj;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
    }

    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    public String getCnqx() {
        return cnqx;
    }

    public void setCnqx(String cnqx) {
        this.cnqx = cnqx;
    }

    public String getGzldymc() {
        return gzldymc;
    }

    public void setGzldymc(String gzldymc) {
        this.gzldymc = gzldymc;
    }

    public String getQlrzjzl() {
        return qlrzjzl;
    }

    public void setQlrzjzl(String qlrzjzl) {
        this.qlrzjzl = qlrzjzl;
    }

    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    public String getYxtcqzh() {
        return yxtcqzh;
    }

    public void setYxtcqzh(String yxtcqzh) {
        this.yxtcqzh = yxtcqzh;
    }

    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public Integer getZydjybsq() {
        return zydjybsq;
    }

    public void setZydjybsq(Integer zydjybsq) {
        this.zydjybsq = zydjybsq;
    }
}
