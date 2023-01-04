package cn.gtmap.realestate.common.core.domain.exchange;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "DJF_DJ_YWXX")
@XmlRootElement(name = "DJF_DJ_YWXX")
public class DjfDjYwxxDO implements Serializable {

    @Id
    @ApiModelProperty(value = "业务号")
    private String ywh;
    @ApiModelProperty(value = "原业务号")
    private String yywh;
    @ApiModelProperty(value = "业务名称")
    private String ywmc;
    @ApiModelProperty(value = "受理号(业务受理流水号)")
    private String slh;
    @ApiModelProperty(value = "开始时间")
    private Date kssj;
    @ApiModelProperty(value = "结束时间")
    private Date jssj;
    @ApiModelProperty(value = "行政区代码")
    private String xzqdm;
    @ApiModelProperty(value = "登记类型")
    private String djlx;
    @ApiModelProperty(value = "权利类型")
    private String qllx;
    @ApiModelProperty(value = "登记机构名称")
    private String djjgmc;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "null")
    private String ybdcqzh;
    @ApiModelProperty(value = "null")
    private String sqlx;
    @ApiModelProperty(value = "审批系统业务号")
    private String spxtywh;
    @ApiModelProperty(value = "项目状态")
    private String xmzt;
    private Date createtime;
    private Date updatetime;
    private String yqlid;
    private String djzx;
    private String zl;
    @ApiModelProperty(value = "一张网编号")
    private String yzwbh;
    @ApiModelProperty(value = "外网受理编号")
    private String wwslbh;
    @ApiModelProperty(value = "过度项目proid")
    private String gdywh;
    @ApiModelProperty(value = "完税状态")
    private String wszt;
    @ApiModelProperty(value = "不动产类型")
    private String bdclx;
    @ApiModelProperty(value = "受理点名称")
    private String sldmc;
    @ApiModelProperty(value = "交易不动产单元编号")
    private String bdcdybh;
    @ApiModelProperty(value = "交易原证号")
    private String jyyzh;
    @ApiModelProperty(value = "业务所处状态")
    private String lczt;
    @ApiModelProperty(value = "审批系统类型")
    private String spxtlx;
    @ApiModelProperty(value = "审批意见")
    private String spyj;
    @ApiModelProperty(value = "流程节点名称")
    private String lcjdmc;
    @ApiModelProperty(value = "资金托管协议编号")
    private String zjtgxybh;
    @ApiModelProperty(value = "合同编号")
    private String htbh;
    @ApiModelProperty(value = "土地权利性质")
    private String tdqlxz;
    @ApiModelProperty(value = "土地用途")
    private String tdyt;
    @ApiModelProperty(value = "原房产证号")
    private String yfczh;
    @ApiModelProperty(value = "原土地证号")
    private String ytdzh;
    @ApiModelProperty(value = "水卡户号")
    private String skhh;
    @ApiModelProperty(value = "电卡户号")
    private String dkhh;
    @ApiModelProperty(value = "燃气卡户号")
    private String rqkhh;
    @ApiModelProperty(value = "单位名称")
    private String dwmc;

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

    @XmlAttribute(name = "SQLX")
    public String getSqlx() {
        return sqlx;
    }

    public void setSqlx(String sqlx) {
        this.sqlx = sqlx;
    }

    @XmlAttribute(name = "SPXTYWH")
    public String getSpxtywh() {
        return spxtywh;
    }

    public void setSpxtywh(String spxtywh) {
        this.spxtywh = spxtywh;
    }

    @XmlAttribute(name = "XMZT")
    public String getXmzt() {
        return xmzt;
    }

    public void setXmzt(String xmzt) {
        this.xmzt = xmzt;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @XmlAttribute(name = "YQLID")
    public String getYqlid() {
        return yqlid;
    }

    public void setYqlid(String yqlid) {
        this.yqlid = yqlid;
    }

    @XmlAttribute(name = "DJZX")
    public String getDjzx() {
        return djzx;
    }

    public void setDjzx(String djzx) {
        this.djzx = djzx;
    }

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "YZWBH")
    public String getYzwbh() {
        return yzwbh;
    }

    public void setYzwbh(String yzwbh) {
        this.yzwbh = yzwbh;
    }

    @XmlAttribute(name = "WWSLBH")
    public String getWwslbh() {
        return wwslbh;
    }

    public void setWwslbh(String wwslbh) {
        this.wwslbh = wwslbh;
    }

    @XmlAttribute(name = "GDYWH")
    public String getGdywh() {
        return gdywh;
    }

    public void setGdywh(String gdywh) {
        this.gdywh = gdywh;
    }

    @XmlAttribute(name = "WSZT")
    public String getWszt() {
        return wszt;
    }

    public void setWszt(String wszt) {
        this.wszt = wszt;
    }

    @XmlAttribute(name = "BDCLX")
    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    @XmlAttribute(name = "SLDMC")
    public String getSldmc() {
        return sldmc;
    }

    public void setSldmc(String sldmc) {
        this.sldmc = sldmc;
    }

    @XmlAttribute(name = "BDCDYBH")
    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    @XmlAttribute(name = "JYYZH")
    public String getJyyzh() {
        return jyyzh;
    }

    public void setJyyzh(String jyyzh) {
        this.jyyzh = jyyzh;
    }

    @XmlAttribute(name = "LCZT")
    public String getLczt() {
        return lczt;
    }

    public void setLczt(String lczt) {
        this.lczt = lczt;
    }

    @XmlAttribute(name = "SPXTLX")
    public String getSpxtlx() {
        return spxtlx;
    }

    public void setSpxtlx(String spxtlx) {
        this.spxtlx = spxtlx;
    }

    @XmlAttribute(name = "SPYJ")
    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj;
    }

    @XmlAttribute(name = "LCJDMC")
    public String getLcjdmc() {
        return lcjdmc;
    }

    public void setLcjdmc(String lcjdmc) {
        this.lcjdmc = lcjdmc;
    }

    @XmlAttribute(name = "ZJTGXYBH")
    public String getZjtgxybh() {
        return zjtgxybh;
    }

    public void setZjtgxybh(String zjtgxybh) {
        this.zjtgxybh = zjtgxybh;
    }

    @XmlAttribute(name = "HTBH")
    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    @XmlAttribute(name = "TDQLXZ")
    public String getTdqlxz() {
        return tdqlxz;
    }

    public void setTdqlxz(String tdqlxz) {
        this.tdqlxz = tdqlxz;
    }

    @XmlAttribute(name = "TDYT")
    public String getTdyt() {
        return tdyt;
    }

    public void setTdyt(String tdyt) {
        this.tdyt = tdyt;
    }

    @XmlAttribute(name = "YFCZH")
    public String getYfczh() {
        return yfczh;
    }

    public void setYfczh(String yfczh) {
        this.yfczh = yfczh;
    }

    @XmlAttribute(name = "YTDZH")
    public String getYtdzh() {
        return ytdzh;
    }

    public void setYtdzh(String ytdzh) {
        this.ytdzh = ytdzh;
    }

    @XmlAttribute(name = "SKHH")
    public String getSkhh() {
        return skhh;
    }

    public void setSkhh(String skhh) {
        this.skhh = skhh;
    }

    @XmlAttribute(name = "DKHH")
    public String getDkhh() {
        return dkhh;
    }

    public void setDkhh(String dkhh) {
        this.dkhh = dkhh;
    }

    @XmlAttribute(name = "RQKHH")
    public String getRqkhh() {
        return rqkhh;
    }

    public void setRqkhh(String rqkhh) {
        this.rqkhh = rqkhh;
    }

    @XmlAttribute(name = "DWMC")
    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
}
