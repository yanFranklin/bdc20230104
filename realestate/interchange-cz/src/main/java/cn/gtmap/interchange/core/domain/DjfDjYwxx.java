package cn.gtmap.interchange.core.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Date;

/**
 * 登记项目业务信息
 *
 * @author <a href="mailto:shenjian@gtmap.cn">jane</a>
 * @version 1.0, 2015/12/24
 */
@XmlRootElement(name = "DJF_DJ_YWXX")
@Table(name = "DJF_DJ_YWXX")
public class DjfDjYwxx implements Serializable {
    //业务号，全局唯一、当前登记项目的业务号（主键）

    @Id
    private String ywh;
    //原业务号，上一手（上次）登记项目的业务号（主键），存在多个时逗号隔开

    private String yywh;
    //业务名称

    private String ywmc;
    //受理号

    private String slh;
    //开始时间

    private Date kssj;
    //结束时间

    private Date jssj;
    //行政区代码

    private String xzqdm;
    //登记类型

    private String djlx;
    //权利类型

    private String qllx;
    //登记机构名称

    private String djjgmc;

    //登记子项
    private String djzx;
    //备注

    private String bz;
    //原不动产权证号，上一手（上次）登记项目产生的不动产权证号，存在多个时逗号隔开

    private String ybdcqzh;
    //添加申请类型，业务子类细分，共享数据 对方需要区分

    private String sqlx;

    //审批系统业务号，如交易系统业务号
    private String spxtywh;
    /**
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/3/15
     * @description 项目状态，字典项
     */
    private String xmzt;
    /**
     * 为防止ybdcqzh重复或不规范，造成房产交易查询不到问题，添加yqlid 存取过渡的qlid
     *
     * @author <a href="mailto:zhangqiang@gtmap.cn">zhangqiang</a>
     * @description
     */
    private String yqlid;
    /**
     * 插入更新时间
     *
     * @author <a href="mailto:zhangqiang@gtmap.cn">zhangqiang</a>
     * @description
     */
    private Date updatetime;
    /**
     * 插入创建时间
     *
     * @author <a href="mailto:zhangqiang@gtmap.cn">zhangqiang</a>
     * @description
     */
    private Date createtime;

    /**
     * 插入坐落
     *
     * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
     * @description
     */
    private String zl;

    /**
     * 插入流程节点名称
     *
     * @author <a href="mailto:zx@gtmap.cn">zx</a>
     * @description
     */
    private String lcjdmc;
    /**
     * 插入审批系统类型
     *1不动产2交易3银行4地税
     * @author <a href="mailto:zx@gtmap.cn">zx</a>
     * @description
     */
    private String spxtlx;
    /**
     * 插入审批意见
     *
     * @author <a href="mailto:zx@gtmap.cn">zx</a>
     * @description
     */
    private String spyj;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 一张网编号
     */
    private String yzwbh;

    /**
     * @author <a href="mailto:xuchao@gtmap.cn">xuchao</a>
     * @description 外网申请受理编号
     */
    private String wwslbh;
    /**
     * @author <a href="mailto:xuchao@gtmap.cn">xuchao</a>
     * @description 过度项目的proid
     */
    private String gdywh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 完税状态
     */
    private String wszt;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 不动产类型
     */
    private String bdclx;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 受理点名称
     */
    private String sldmc;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 交易 不动产单元编号
     */
    private String bdcdybh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 交易原证号
     */
    private String jyyzh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 流程状态
     */
    private String lczt;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 资金托管协议编号
     */
    private String zjtgxybh;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 交易备案合同号
     */
    private String htbh;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 土地用途
     */
    private String tdyt;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 土地权利性质
     */
    private String tdqlxz;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 原房产证号
     */
    private String yfczh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 原土地证号
     */
    private String ytdzh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 水卡户号
     */
    private String skhh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 电卡户号
     */
    private String dkhh;

    /**
     * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
     * @description 燃气卡户号
     */
    private String rqkhh;

    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 单位名称
     */
    private String dwmc;
    /**
     * @author <a href="mailto:zhuruijie@gtmap.cn">zhuruijie</a>
     * @description 电视卡号
     */
    private String dskh;
    /**
     * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
     * @description 不动产单元房屋类型
     */
    private String bdcdyfwlx;
    /**
     * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
     * @description proid
     */
    private String proid;

    /**
     * @author <a href="mailto:xiejianan@gtmap.cn">xiejianan</a>
     * @description 推送状态
     */
    private String tszt;

    /**
     * @author <a href="mailto:xiejianan@gtmap.cn">xiejianan</a>
     * @description 领证日期
     */
    private Date lzrq;

    private String sffsjf;//是否分时缴费
    private String yhzdkgxsfjb;//原户主代扣关系是否解绑
    private String sfsfdw;//水费收费单位
    private String dfsfdw;//电费收费单位
    private String rqsfdw;//燃气收费单位
    private String dssfdw;//广电卡收费单位(电视收费)




    @XmlTransient
    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    @XmlTransient
    public String getSkhh() {
        return skhh;
    }

    public void setSkhh(String skhh) {
        this.skhh = skhh;
    }

    @XmlTransient
    public String getDkhh() {
        return dkhh;
    }

    public void setDkhh(String dkhh) {
        this.dkhh = dkhh;
    }

    @XmlTransient
    public String getRqkhh() {
        return rqkhh;
    }

    public void setRqkhh(String rqkhh) {
        this.rqkhh = rqkhh;
    }

    @XmlTransient
    public String getYfczh() {
        return yfczh;
    }

    public void setYfczh(String yfczh) {
        this.yfczh = yfczh;
    }

    @XmlTransient
    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    @XmlTransient
    public String getSldmc() {
        return sldmc;
    }

    public void setSldmc(String sldmc) {
        this.sldmc = sldmc;
    }

    @XmlTransient
    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    @XmlTransient
    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    @XmlTransient
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @XmlTransient
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @XmlTransient
    public String getYqlid() {
        return yqlid;
    }

    public void setYqlid(String yqlid) {
        this.yqlid = yqlid;
    }

    @XmlTransient
    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    @XmlAttribute(name = "SQLX")
    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "YYWH")
    public String getYywh() {
        return yywh;
    }

    public void setYywh(String yywh) {
        this.yywh = yywh;
    }

    @XmlAttribute(name = "YWMC")
    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc;
    }

    @XmlAttribute(name = "SLH")
    public String getSlh() {
        return slh;
    }

    public void setSlh(String slh) {
        this.slh = slh;
    }

    @XmlAttribute(name = "KSSJ")
    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    @XmlAttribute(name = "JSSJ")
    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    @XmlAttribute(name = "XZQDM")
    public String getXzqdm() {
        return xzqdm;
    }

    public void setXzqdm(String xzqdm) {
        this.xzqdm = xzqdm;
    }

    @XmlAttribute(name = "DJLX")
    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    @XmlAttribute(name = "QLLX")
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    @XmlAttribute(name = "DJJGMC")
    public String getDjjgmc() {
        return djjgmc;
    }

    public void setDjjgmc(String djjgmc) {
        this.djjgmc = djjgmc;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "YBDCQZH")
    public String getYbdcqzh() {
        return ybdcqzh;
    }

    public void setYbdcqzh(String ybdcqzh) {
        this.ybdcqzh = ybdcqzh;
    }

    @XmlTransient
    public String getXmzt() {
        return xmzt;
    }

    public void setXmzt(String xmzt) {
        this.xmzt = xmzt;
    }

    @XmlTransient
    public String getDjzx() {
        return djzx;
    }

    public void setDjzx(String djzx) {
        this.djzx = djzx;
    }

    @XmlTransient
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlTransient
    public String getLcjdmc() {
        return lcjdmc;
    }

    public void setLcjdmc(String lcjdmc) {
        this.lcjdmc = lcjdmc;
    }
    @XmlTransient
    public String getSpxtlx() {
        return spxtlx;
    }

    public void setSpxtlx(String spxtlx) {
        this.spxtlx = spxtlx;
    }
    @XmlTransient
    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    @XmlTransient
    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }

    @XmlTransient
    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    @XmlTransient
    public String getGdywh() {
        return gdywh;
    }

    public void setGdywh(String gdywh) {
        this.gdywh = gdywh;
    }

    @XmlTransient
    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    @XmlTransient
    public String getJyyzh() {
        return jyyzh;
    }

    public void setJyyzh(String jyyzh) {
        this.jyyzh = jyyzh;
    }

    @XmlTransient
    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }

    @XmlTransient
    public String getZjtgxybh() {
        return zjtgxybh;
    }

    public void setZjtgxybh(String zjtgxybh) {
        this.zjtgxybh = zjtgxybh;
    }

    @XmlTransient
    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    @XmlTransient
    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    @XmlTransient
    public String getTdqlxz() {
        return tdqlxz;
    }

    public void setTdqlxz(String tdqlxz) {
        this.tdqlxz = tdqlxz;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getDskh() {
        return dskh;
    }

    public void setDskh(String dskh) {
        this.dskh = dskh;
    }

    @XmlTransient
    public String getBdcdyfwlx() {
        return bdcdyfwlx;
    }

    public void setBdcdyfwlx(String bdcdyfwlx) {
        this.bdcdyfwlx = bdcdyfwlx;
    }

    @XmlTransient
    public String getTszt() {
        return tszt;
    }

    public void setTszt(String tszt) {
        this.tszt = tszt;
    }

    @XmlTransient
    public Date getLzrq() {
        return lzrq;
    }

    public void setLzrq(Date lzrq) {
        this.lzrq = lzrq;
    }

    @XmlTransient
    public String getSffsjf() {
        return sffsjf;
    }

    public void setSffsjf(String sffsjf) {
        this.sffsjf = sffsjf;
    }

    @XmlTransient
    public String getYhzdkgxsfjb() {
        return yhzdkgxsfjb;
    }

    public void setYhzdkgxsfjb(String yhzdkgxsfjb) {
        this.yhzdkgxsfjb = yhzdkgxsfjb;
    }

    @XmlTransient
    public String getSfsfdw() {
        return sfsfdw;
    }

    public void setSfsfdw(String sfsfdw) {
        this.sfsfdw = sfsfdw;
    }

    @XmlTransient
    public String getDfsfdw() {
        return dfsfdw;
    }

    public void setDfsfdw(String dfsfdw) {
        this.dfsfdw = dfsfdw;
    }

    @XmlTransient
    public String getRqsfdw() {
        return rqsfdw;
    }

    public void setRqsfdw(String rqsfdw) {
        this.rqsfdw = rqsfdw;
    }

    @XmlTransient
    public String getDssfdw() {
        return dssfdw;
    }

    public void setDssfdw(String dssfdw) {
        this.dssfdw = dssfdw;
    }

}
