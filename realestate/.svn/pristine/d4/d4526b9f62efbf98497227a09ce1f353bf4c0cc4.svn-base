package cn.gtmap.realestate.exchange.core.domain.exchange;

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
@Table(name = "KTT_GZW")
@XmlRootElement(name = "KTT_GZW")
@XmlAccessorType(XmlAccessType.NONE)
public class KttGzwDO implements Serializable, AccessData {

    private String ysdm = "6001030200";
    @ApiModelProperty(value = "标识码/原为非空")
    private Integer bsm;
    @ApiModelProperty(value = "不动产单元号非空")
    @Id
    private String bdcdyh;
    @ApiModelProperty(value = "宗地/宗海代码非空")
    private String zdzhdm;
    @ApiModelProperty(value = "构筑物名称")
    private String gzwmc;
    @ApiModelProperty(value = "坐落非空")
    private String zl;
    @ApiModelProperty(value = "面积单位面积单位字典表")
    private String mjdw;
    @ApiModelProperty(value = "面积＞0")
    private Double mj;
    @ApiModelProperty(value = "档案号")
    private String dah;
    @ApiModelProperty(value = "状态不动产单元状态字典表")
    private String zt;
    private Date createtime;
    private Date updatetime;

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "BSM")
    public Integer getBsm() {
        return bsm;
    }

    public void setBsm(Integer bsm) {
        this.bsm = bsm;
    }

    @XmlAttribute(name = "BDCDYH")
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "ZDZHDM")
    public String getZdzhdm() {
        return zdzhdm;
    }

    public void setZdzhdm(String zdzhdm) {
        this.zdzhdm = zdzhdm;
    }

    @XmlAttribute(name = "GZWMC")
    public String getGzwmc() {
        return gzwmc;
    }

    public void setGzwmc(String gzwmc) {
        this.gzwmc = gzwmc;
    }

    @XmlAttribute(name = "ZL")
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "MJDW")
    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    @XmlAttribute(name = "MJ")
    public Double getMj() {
        return mj;
    }

    public void setMj(Double mj) {
        this.mj = mj;
    }

    @XmlAttribute(name = "DAH")
    public String getDah() {
        return dah;
    }

    public void setDah(String dah) {
        this.dah = dah;
    }

    @XmlAttribute(name = "ZT")
    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
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
