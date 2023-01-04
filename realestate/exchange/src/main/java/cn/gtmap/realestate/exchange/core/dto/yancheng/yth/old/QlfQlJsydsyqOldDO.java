package cn.gtmap.realestate.exchange.core.dto.yancheng.yth.old;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "QLF_QL_JSYDSYQ")
@XmlRootElement(name = "QLF_QL_JSYDSYQ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlJsydsyqOldDO implements Serializable {

    private String ysdm = "6002020300";//要素代码
    @ApiModelProperty(value = "宗地代码非空")
    private String zddm;
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
    @ApiModelProperty(value = "使用权面积＞0单位：平方米")
    private String syqmj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用权起始时间国有建设用地需填写")
    private Date syqqssj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "使用权结束时间国有建设用地需填写")
    private Date syqjssj;
    @ApiModelProperty(value = "取得价格＞=0单位：万元")
    private String qdjg;
    @ApiModelProperty(value = "不动产权证号非空")
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
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;
    @ApiModelProperty(value = "登记类型名称")
    private String djlxmc;
    @ApiModelProperty(value = "nf+zhlsh")
    private String bdcqzhjc;
    @ApiModelProperty(value = "区县代码名称")
    private String qxdmmc;
    @ApiModelProperty(value = "权属状态名称")
    private String qsztmc;
    @ApiModelProperty(value = "过度房屋的fwbm，过度土地的djh")
    private String dzwbm;
    @ApiModelProperty(value = "分摊土地面积")
    private String fttdmj;
    @ApiModelProperty(value = "独用土地面积")
    private String dytdmj;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "ZDDM")
    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
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

    @XmlAttribute(name = "SYQMJ")
    public String getSyqmj() {
        return syqmj;
    }

    public void setSyqmj(String syqmj) {
        this.syqmj = syqmj;
    }

    @XmlAttribute(name = "SYQQSSJ")
    public Date getSyqqssj() {
        return syqqssj;
    }

    public void setSyqqssj(Date syqqssj) {
        this.syqqssj = syqqssj;
    }

    @XmlAttribute(name = "SYQJSSJ")
    public Date getSyqjssj() {
        return syqjssj;
    }

    public void setSyqjssj(Date syqjssj) {
        this.syqjssj = syqjssj;
    }

    @XmlAttribute(name = "QDJG")
    public String getQdjg() {
        return qdjg;
    }

    public void setQdjg(String qdjg) {
        this.qdjg = qdjg;
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

    @XmlTransient
    public String getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(String fttdmj) {
        this.fttdmj = fttdmj;
    }

    public String getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(String dytdmj) {
        this.dytdmj = dytdmj;
    }
}
