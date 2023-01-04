package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import cn.gtmap.realestate.common.core.domain.exchange.AccessData;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "QLT_FW_FDCQ_YZ")
@XmlRootElement(name = "QLT_FW_FDCQ_YZ")
@XmlAccessorType(XmlAccessType.NONE)
public class QltFwFdcqYzOldDO implements Serializable, AccessData {

    private String ysdm = "6002010200";//要素代码
    @ApiModelProperty(value = "不动产单元号非空")
    @Id
    private String bdcdyh;
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "权利类型权利类型字典表")
    private String qllx;
    @ApiModelProperty(value = "登记类型登记类型字典表")
    private String djlx;
    @ApiModelProperty(value = "登记原因非空")
    private String djyy;
    @ApiModelProperty(value = "房地坐落非空")
    private String fdzl;
    @ApiModelProperty(value = "土地使用权人非空")
    private String tdsyqr;
    @ApiModelProperty(value = "独用土地面积＞0单位：平方米")
    private String dytdmj;
    @ApiModelProperty(value = "分摊土地面积＞0单位：平方米")
    private String fttdmj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "土地使用起始时间非空")
    private Date tdsyqssj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "土地使用结束时间非空")
    private Date tdsyjssj;
    @ApiModelProperty(value = "房地产交易价格＞=0单位：万元")
    private String fdcjyjg;
    @ApiModelProperty(value = "规划用途房屋用途字典表")
    private String ghyt;
    @ApiModelProperty(value = "房屋性质房屋性质字典表")
    private String fwxz;
    @ApiModelProperty(value = "房屋结构房屋结构字典表")
    private String fwjg;
    @ApiModelProperty(value = "所在层")
    private String szc;
    @ApiModelProperty(value = "总层数＞0")
    private String zcs;
    @ApiModelProperty(value = "建筑面积＞0单位：平方米")
    private String jzmj;
    @ApiModelProperty(value = "专有建筑面积＞0单位：平方米")
    private String zyjzmj;
    @ApiModelProperty(value = "分摊建筑面积＞0单位：平方米")
    private String ftjzmj;
    @ApiModelProperty(value = "竣工时间")
    private String jgsj;
    @ApiModelProperty(value = "不动产权证号对于不动产登记之前的历史数据可不填写，否则应填写。")
    @Id
    private String bdcqzh;
    @ApiModelProperty(value = "区县代码区县字典表记录属地。")
    private String qxdm;
    @ApiModelProperty(value = "登记机构非空")
    private String djjg;
    @ApiModelProperty(value = "登簿人非空")
    private String dbr;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登记时间非空")
    private Date djsj;
    @ApiModelProperty(value = "附记")
    private String fj;
    @ApiModelProperty(value = "权属状态权属状态字典表")
    private String qszt;
    private String szmyc;
    private Date createtime;
    private Date updatetime;
    private String myzcs;
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;
    @ApiModelProperty(value = "登记类型名称")
    private String djlxmc;
    @ApiModelProperty(value = "规划用途名称")
    private String ghytmc;
    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;
    @ApiModelProperty(value = "房屋结构名称")
    private String fwjgmc;
    @ApiModelProperty(value = "不动产权证号简称")
    private String bdcqzhjc;
    @ApiModelProperty(value = "区县代码名称")
    private String qxdmmc;
    @ApiModelProperty(value = "权属状态名称")
    private String qsztmc;
    @ApiModelProperty(value = "过度房屋的fwbm，过度土地的djh")
    private String dzwbm;
    @ApiModelProperty(value = "房地产交易合同号")
    private String fdcjyhth;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "QLLX")
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    @XmlAttribute(name = "DJLX")
    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    @XmlAttribute(name = "DJYY")
    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    @XmlAttribute(name = "FDZL")
    public String getFdzl() {
        return fdzl;
    }

    public void setFdzl(String fdzl) {
        this.fdzl = fdzl;
    }

    @XmlAttribute(name = "TDSYQR")
    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    @XmlAttribute(name = "DYTDMJ")
    public String getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(String dytdmj) {
        this.dytdmj = dytdmj;
    }

    @XmlAttribute(name = "FTTDMJ")
    public String getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(String fttdmj) {
        this.fttdmj = fttdmj;
    }

    @XmlAttribute(name = "TDSYQSSJ")
    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    @XmlAttribute(name = "TDSYJSSJ")
    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
    }

    @XmlAttribute(name = "FDCJYJG")
    public String getFdcjyjg() {
        return fdcjyjg;
    }

    public void setFdcjyjg(String fdcjyjg) {
        this.fdcjyjg = fdcjyjg;
    }

    @XmlAttribute(name = "GHYT")
    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @XmlAttribute(name = "FWXZ")
    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    @XmlAttribute(name = "FWJG")
    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    @XmlAttribute(name = "SZC")
    public String getSzc() {
        return szc;
    }

    public void setSzc(String szc) {
        this.szc = szc;
    }

    @XmlAttribute(name = "ZCS")
    public String getZcs() {
        return zcs;
    }

    public void setZcs(String zcs) {
        this.zcs = zcs;
    }

    @XmlAttribute(name = "JZMJ")
    public String getJzmj() {
        return jzmj;
    }

    public void setJzmj(String jzmj) {
        this.jzmj = jzmj;
    }

    @XmlAttribute(name = "ZYJZMJ")
    public String getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(String zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    @XmlAttribute(name = "FTJZMJ")
    public String getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(String ftjzmj) {
        this.ftjzmj = ftjzmj;
    }

    @XmlAttribute(name = "JGSJ")
    public String getJgsj() {
        return jgsj;
    }

    public void setJgsj(String jgsj) {
        this.jgsj = jgsj;
    }

    @XmlAttribute(name = "BDCQZH")
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlAttribute(name = "DJJG")
    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @XmlAttribute(name = "DBR")
    public String getDbr() {
        return dbr;
    }

    public void setDbr(String dbr) {
        this.dbr = dbr;
    }

    @XmlAttribute(name = "DJSJ")
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlAttribute(name = "FJ")
    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    @XmlAttribute(name = "QSZT")
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getSzmyc() {
        return szmyc;
    }

    public void setSzmyc(String szmyc) {
        this.szmyc = szmyc;
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

    public String getMyzcs() {
        return myzcs;
    }

    public void setMyzcs(String myzcs) {
        this.myzcs = myzcs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getDjlxmc() {
        return djlxmc;
    }

    public void setDjlxmc(String djlxmc) {
        this.djlxmc = djlxmc;
    }

    public String getGhytmc() {
        return ghytmc;
    }

    public void setGhytmc(String ghytmc) {
        this.ghytmc = ghytmc;
    }

    public String getFwxzmc() {
        return fwxzmc;
    }

    public void setFwxzmc(String fwxzmc) {
        this.fwxzmc = fwxzmc;
    }

    public String getFwjgmc() {
        return fwjgmc;
    }

    public void setFwjgmc(String fwjgmc) {
        this.fwjgmc = fwjgmc;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    public String getQxdmmc() {
        return qxdmmc;
    }

    public void setQxdmmc(String qxdmmc) {
        this.qxdmmc = qxdmmc;
    }

    public String getQsztmc() {
        return qsztmc;
    }

    public void setQsztmc(String qsztmc) {
        this.qsztmc = qsztmc;
    }

    public String getDzwbm() {
        return dzwbm;
    }

    public void setDzwbm(String dzwbm) {
        this.dzwbm = dzwbm;
    }

    public String getFdcjyhth() {
        return fdcjyhth;
    }

    public void setFdcjyhth(String fdcjyhth) {
        this.fdcjyhth = fdcjyhth;
    }
}
