package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.core.annotations.RequiredFk;
import io.swagger.annotations.ApiModelProperty;

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
@Table(name = "ZTT_GY_QLR")
@XmlRootElement(name = "ZTT_GY_QLR")
@XmlAccessorType(XmlAccessType.NONE)
public class ZttGyQlrDO implements Serializable, AccessData {

    private String ysdm = "6003000000";//要素代码
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "顺序号")
    private Integer sxh;
    @ApiModelProperty(value = "权利人名称非空")
    private String qlrmc;
    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;
    @ApiModelProperty(value = "权证印刷序列号")
    private String qzysxlh;
    @ApiModelProperty(value = "是否持证人是否字典表分别持证时必选。")
    private String sfczr;
    @ApiModelProperty(value = "证件种类证件种类字典表")
    private String zjzl;
    @ApiModelProperty(value = "证件号")
    private String zjh;
    @ApiModelProperty(value = "发证机关")
    private String fzjg;
    @ApiModelProperty(value = "所属行业 见本表注2")
    private String sshy;
    @ApiModelProperty(value = "国家/地区国家和地区字典表")
    private String gj;
    @ApiModelProperty(value = "户籍所在省市省市字典表")
    private String hjszss;
    @ApiModelProperty(value = "性别性别字典表")
    private String xb;
    @ApiModelProperty(value = "电话")
    private String dh;
    @ApiModelProperty(value = "地址")
    private String dz;
    @ApiModelProperty(value = "邮编")
    private String yb;
    @ApiModelProperty(value = "工作单位")
    private String gzdw;
    @ApiModelProperty(value = "电子邮件")
    private String dzyj;
    @ApiModelProperty(value = "权利人类型权利人类型字典表")
    private String qlrlx;
    @ApiModelProperty(value = "权利比例")
    private String qlbl;
    @ApiModelProperty(value = "共有方式共有方式字典表")
    private String gyfs;
    @ApiModelProperty(value = "共有情况")
    private String gyqk;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "业务号")
    @RequiredFk
    private String ywh;
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "主不动产权证号")
    private String zbdcqzh;
    @ApiModelProperty(value = "不动产权证号简称")
    private String bdcqzhjc;
    @ApiModelProperty(value = "权利类型")
    private String qllx;
    @ApiModelProperty(value = "权利类型名称")
    private String qllxmc;
    @ApiModelProperty(value = "权属状态")
    private String qszt;
    @ApiModelProperty(value = "权属状态名称")
    private String qsztmc;
    @ApiModelProperty(value = "是否持证人名称")
    private String sfczrmc;
    @ApiModelProperty(value = "证件种类名称")
    private String zjzlmc;
    @ApiModelProperty(value = "国家/地区名称")
    private String gjmc;
    @ApiModelProperty(value = "性别名称")
    private String xbmc;
    @ApiModelProperty(value = "权利人类型名称")
    private String qlrlxmc;
    @ApiModelProperty(value = "共有方式名称")
    private String gyfsmc;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;

    @ApiModelProperty(value = "登记时间")
    private Date djsj;

    @ApiModelProperty(value = "权利人特征")
    private String qlrtz;
    @ApiModelProperty(value = "承包方代码")
    private String cbfbm;
    @ApiModelProperty(value = "发包方代码")
    private String fbfbm;

    @XmlAttribute(name = "DJSJ")
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlAttribute(name = "QLRTZ")
    public String getQlrtz() {
        return qlrtz;
    }

    public void setQlrtz(String qlrtz) {
        this.qlrtz = qlrtz;
    }

    @XmlAttribute(name = "CBFBM")
    public String getCbfbm() {
        return cbfbm;
    }

    public void setCbfbm(String cbfbm) {
        this.cbfbm = cbfbm;
    }

    @XmlAttribute(name = "FBFBM")
    public String getFbfbm() {
        return fbfbm;
    }

    public void setFbfbm(String fbfbm) {
        this.fbfbm = fbfbm;
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

    @XmlAttribute(name = "SXH")
    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }

    @XmlAttribute(name = "QLRMC")
    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    @XmlAttribute(name = "BDCQZH")
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @XmlAttribute(name = "QZYSXLH")
    public String getQzysxlh() {
        return qzysxlh;
    }

    public void setQzysxlh(String qzysxlh) {
        this.qzysxlh = qzysxlh;
    }

    @XmlAttribute(name = "SFCZR")
    public String getSfczr() {
        return sfczr;
    }

    public void setSfczr(String sfczr) {
        this.sfczr = sfczr;
    }

    @XmlAttribute(name = "ZJZL")
    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    @XmlAttribute(name = "ZJH")
    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    @XmlAttribute(name = "FZJG")
    public String getFzjg() {
        return fzjg;
    }

    public void setFzjg(String fzjg) {
        this.fzjg = fzjg;
    }

    @XmlAttribute(name = "SSHY")
    public String getSshy() {
        return sshy;
    }

    public void setSshy(String sshy) {
        this.sshy = sshy;
    }

    @XmlAttribute(name = "GJ")
    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    @XmlAttribute(name = "HJSZSS")
    public String getHjszss() {
        return hjszss;
    }

    public void setHjszss(String hjszss) {
        this.hjszss = hjszss;
    }

    @XmlAttribute(name = "XB")
    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    @XmlAttribute(name = "DH")
    public String getDh() {
        return dh;
    }

    public void setDh(String dh) {
        this.dh = dh;
    }

    @XmlAttribute(name = "DZ")
    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    @XmlAttribute(name = "YB")
    public String getYb() {
        return yb;
    }

    public void setYb(String yb) {
        this.yb = yb;
    }

    @XmlAttribute(name = "GZDW")
    public String getGzdw() {
        return gzdw;
    }

    public void setGzdw(String gzdw) {
        this.gzdw = gzdw;
    }

    @XmlAttribute(name = "DZYJ")
    public String getDzyj() {
        return dzyj;
    }

    public void setDzyj(String dzyj) {
        this.dzyj = dzyj;
    }

    @XmlAttribute(name = "QLRLX")
    public String getQlrlx() {
        return qlrlx;
    }

    public void setQlrlx(String qlrlx) {
        this.qlrlx = qlrlx;
    }

    @XmlAttribute(name = "QLBL")
    public String getQlbl() {
        return qlbl;
    }

    public void setQlbl(String qlbl) {
        this.qlbl = qlbl;
    }

    @XmlAttribute(name = "GYFS")
    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    @XmlAttribute(name = "GYQK")
    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    @XmlAttribute(name = "BZ")
    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @XmlAttribute(name = "QXDM")
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZbdcqzh() {
        return zbdcqzh;
    }

    public void setZbdcqzh(String zbdcqzh) {
        this.zbdcqzh = zbdcqzh;
    }

    public String getBdcqzhjc() {
        return bdcqzhjc;
    }

    public void setBdcqzhjc(String bdcqzhjc) {
        this.bdcqzhjc = bdcqzhjc;
    }

    @XmlAttribute(name = "QLLX")
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    @XmlAttribute(name = "QSZT")
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getQsztmc() {
        return qsztmc;
    }

    public void setQsztmc(String qsztmc) {
        this.qsztmc = qsztmc;
    }

    public String getSfczrmc() {
        return sfczrmc;
    }

    public void setSfczrmc(String sfczrmc) {
        this.sfczrmc = sfczrmc;
    }

    public String getZjzlmc() {
        return zjzlmc;
    }

    public void setZjzlmc(String zjzlmc) {
        this.zjzlmc = zjzlmc;
    }

    public String getGjmc() {
        return gjmc;
    }

    public void setGjmc(String gjmc) {
        this.gjmc = gjmc;
    }

    public String getXbmc() {
        return xbmc;
    }

    public void setXbmc(String xbmc) {
        this.xbmc = xbmc;
    }

    public String getQlrlxmc() {
        return qlrlxmc;
    }

    public void setQlrlxmc(String qlrlxmc) {
        this.qlrlxmc = qlrlxmc;
    }

    public String getGyfsmc() {
        return gyfsmc;
    }

    public void setGyfsmc(String gyfsmc) {
        this.gyfsmc = gyfsmc;
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
}
