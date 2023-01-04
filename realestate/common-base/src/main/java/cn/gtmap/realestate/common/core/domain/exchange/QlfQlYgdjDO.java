package cn.gtmap.realestate.common.core.domain.exchange;

import com.alibaba.fastjson.annotation.JSONField;
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
@Table(name = "QLF_QL_YGDJ")
@XmlRootElement(name = "QLF_QL_YGDJ")
@XmlAccessorType(XmlAccessType.NONE)
public class QlfQlYgdjDO implements Serializable, AccessData {

    private String ysdm = "6002040100";//要素代码
    @ApiModelProperty(value = "不动产单元号非空")
    @Id
    private String bdcdyh;
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "不动产坐落非空")
    private String bdczl;
    @ApiModelProperty(value = "义务人非空")
    private String ywr;
    @ApiModelProperty(value = "义务人证件种类证件种类字典表")
    private String ywrzjzl;
    @ApiModelProperty(value = "义务人证件号")
    private String ywrzjh;
    @ApiModelProperty(value = "预告登记种类预告登记种类字典表")
    private String ygdjzl;
    @ApiModelProperty(value = "土地使用权人非空")
    private String tdsyqr;
    @ApiModelProperty(value = "规划用途房屋用途字典表")
    private String ghyt;
    @ApiModelProperty(value = "房屋性质房屋性质字典表")
    private String fwxz;
    @ApiModelProperty(value = "房屋结构房屋结构字典表")
    private String fwjg;
    @ApiModelProperty(value = "所在层非空")
    private String szc;
    @ApiModelProperty(value = "总层数＞0")
    private String zcs;
    @ApiModelProperty(value = "建筑面积＞=0单位：平方米")
    private Double jzmj;
    @ApiModelProperty(value = "取得价格/被担保主债权数额＞=0单位：万元")
    private Double qdjg;
    @ApiModelProperty(value = "不动产登记证明号非空")
    @Id
    private String bdcdjzmh;
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
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "登记类型")
    private String djlx;

    @ApiModelProperty(value = "null")
    private Date zwlxksqx;
    @ApiModelProperty(value = "null")
    private Date zwlxjsqx;
    @ApiModelProperty(value = "null")
    private String jyhth;
    @ApiModelProperty(value = "null")
    private Date createtime;
    @ApiModelProperty(value = "null")
    private Date updatetime;
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "义务人证件种类名称")
    private String ywrzjzlmc;
    @ApiModelProperty(value = "预告登记种类名称")
    private String ygdjzlmc;
    @ApiModelProperty(value = "登记类型名称")
    private String djlxmc;
    @ApiModelProperty(value = "规划用途名称")
    private String ghytmc;
    @ApiModelProperty(value = "房屋性质名称")
    private String fwxzmc;
    @ApiModelProperty(value = "房屋结构名称")
    private String fwjgmc;
    @ApiModelProperty(value = "nf+zhlsh")
    private String bdcdjzmhjc;
    @ApiModelProperty(value = "区县代码名称")
    private String qxdmmc;
    @ApiModelProperty(value = "权属状态名称")
    private String qsztmc;
    @ApiModelProperty(value = "过度房屋的fwbm，过度土地的djh")
    private String dzwbm;
    @ApiModelProperty(value = "注销业务号")
    private String zxygywh;
    @ApiModelProperty(value = "注销预告原因")
    private String zxygyy;
    @ApiModelProperty(value = "注销时间")
    private Date zxsj;
    @ApiModelProperty(value = "上一手业务号")
    private String ssywh;
    @ApiModelProperty(value = "用途名称")
    private String ytmc;
    @ApiModelProperty(value = "金额单位")
    private String jedw;
    @ApiModelProperty(value = "担保范围")
    private String dbfw;
    @ApiModelProperty(value = "是否存在禁止或限制转让抵押不动产的约定")
    private String sfczjzhxz;

    @XmlAttribute(name = "ZXYGYWH")
    public String getZxygywh() {
        return zxygywh;
    }

    public void setZxygywh(String zxygywh) {
        this.zxygywh = zxygywh;
    }

    @XmlAttribute(name = "ZXYGYY")
    public String getZxygyy() {
        return zxygyy;
    }

    public void setZxygyy(String zxygyy) {
        this.zxygyy = zxygyy;
    }

    @XmlAttribute(name = "ZXSJ")
    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

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

    @XmlAttribute(name = "BDCZL")
    public String getBdczl() {
        return bdczl;
    }

    public void setBdczl(String bdczl) {
        this.bdczl = bdczl;
    }

    @XmlAttribute(name = "YWR")
    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    @XmlAttribute(name = "YWRZJZL")
    public String getYwrzjzl() {
        return ywrzjzl;
    }

    public void setYwrzjzl(String ywrzjzl) {
        this.ywrzjzl = ywrzjzl;
    }

    @XmlAttribute(name = "YWRZJH")
    public String getYwrzjh() {
        return ywrzjh;
    }

    public void setYwrzjh(String ywrzjh) {
        this.ywrzjh = ywrzjh;
    }

    @XmlAttribute(name = "YGDJZL")
    public String getYgdjzl() {
        return ygdjzl;
    }

    public void setYgdjzl(String ygdjzl) {
        this.ygdjzl = ygdjzl;
    }

    @XmlAttribute(name = "TDSYQR")
    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
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
    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    @XmlAttribute(name = "QDJG")
    public Double getQdjg() {
        return qdjg;
    }

    public void setQdjg(Double qdjg) {
        this.qdjg = qdjg;
    }

    @XmlAttribute(name = "BDCDJZMH")
    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
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

    @XmlAttribute(name = "DJYY")
    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    @XmlAttribute(name = "DJLX")
    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }


    public Date getZwlxksqx() {
        return zwlxksqx;
    }

    public void setZwlxksqx(Date zwlxksqx) {
        this.zwlxksqx = zwlxksqx;
    }

    public Date getZwlxjsqx() {
        return zwlxjsqx;
    }

    public void setZwlxjsqx(Date zwlxjsqx) {
        this.zwlxjsqx = zwlxjsqx;
    }

    @XmlTransient
    public String getJyhth() {
        return jyhth;
    }

    public void setJyhth(String jyhth) {
        this.jyhth = jyhth;
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

    public String getYwrzjzlmc() {
        return ywrzjzlmc;
    }

    public void setYwrzjzlmc(String ywrzjzlmc) {
        this.ywrzjzlmc = ywrzjzlmc;
    }

    public String getYgdjzlmc() {
        return ygdjzlmc;
    }

    public void setYgdjzlmc(String ygdjzlmc) {
        this.ygdjzlmc = ygdjzlmc;
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

    public String getBdcdjzmhjc() {
        return bdcdjzmhjc;
    }

    public void setBdcdjzmhjc(String bdcdjzmhjc) {
        this.bdcdjzmhjc = bdcdjzmhjc;
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

    @XmlAttribute(name = "SSYWH")
    public String getSsywh() {
        return ssywh;
    }

    @XmlAttribute(name = "YTMC")
    public String getYtmc() {
        return ytmc;
    }

    @XmlAttribute(name = "JEDW")
    public String getJedw() {
        return jedw;
    }

    @XmlAttribute(name = "DBFW")
    public String getDbfw() {
        return dbfw;
    }

    @XmlAttribute(name = "SFCZJZHXZ")
    public String getSfczjzhxz() {
        return sfczjzhxz;
    }

    public void setSsywh(String ssywh) {
        this.ssywh = ssywh;
    }

    public void setYtmc(String ytmc) {
        this.ytmc = ytmc;
    }

    public void setJedw(String jedw) {
        this.jedw = jedw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public void setSfczjzhxz(String sfczjzhxz) {
        this.sfczjzhxz = sfczjzhxz;
    }
}
