package cn.gtmap.realestate.common.core.domain.exchange;

import cn.gtmap.realestate.common.util.jaxb.JaxbDateAdapter;
import cn.gtmap.realestate.common.util.jaxb.JaxbIntegerAdapter;
import cn.gtmap.realestate.common.util.jaxb.JaxbStringAdapter;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;


/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 2019/0417,1.0
 * @description
 */
@Table(name = "DJF_DJ_FZ")
@XmlRootElement(name = "DJF_DJ_FZ")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjFzDO implements Serializable, AccessData {

    private String ysdm = "6004050000";
    @ApiModelProperty(value = "业务号")
    @Id
    private String ywh;
    @ApiModelProperty(value = "发证人员")
    private String fzry;
    @ApiModelProperty(value = "发证时间")
    private Date fzsj;
    @ApiModelProperty(value = "发证名称")
    private String fzmc;
    @ApiModelProperty(value = "发证数量")
    private Integer fzsl;
    @ApiModelProperty(value = "核发证书号")
    @Id
    private String hfzsh;
    @ApiModelProperty(value = "领证人姓名")
    private String lzrxm;
    @ApiModelProperty(value = "领证人证件类别")
    private String lzrzjlb;
    @ApiModelProperty(value = "领证人证件号码")
    private String lzrzjhm;
    @ApiModelProperty(value = "领证人电话")
    private String lzrdh;
    @ApiModelProperty(value = "领证人地址")
    private String lzrdz;
    @ApiModelProperty(value = "领证人邮编")
    private String lzryb;
    @ApiModelProperty(value = "备注")
    private String bz;
    @ApiModelProperty(value = "区县代码")
    private String qxdm;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "更新时间")
    private Date updatetime;
    @ApiModelProperty(value = "印刷序列号")
    private String ysxlh;

    @XmlAttribute(name = "YSXLH")
    public String getYsxlh() {
        return ysxlh;
    }

    public void setYsxlh(String ysxlh) {
        this.ysxlh = ysxlh;
    }

    @XmlAttribute(name = "YWH")
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlAttribute(name = "YSDM")
    public String getYsdm() {
        return ysdm;
    }

    @XmlAttribute(name = "FZRY")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getFzry() {
        return fzry;
    }

    public void setFzry(String fzry) {
        this.fzry = fzry;
    }

    @XmlAttribute(name = "FZSJ")
    @XmlJavaTypeAdapter(JaxbDateAdapter.class)
    public Date getFzsj() {
        return fzsj;
    }

    public void setFzsj(Date fzsj) {
        this.fzsj = fzsj;
    }

    @XmlAttribute(name = "FZMC")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getFzmc() {
        return fzmc;
    }

    public void setFzmc(String fzmc) {
        this.fzmc = fzmc;
    }

    @XmlAttribute(name = "FZSL")
    @XmlJavaTypeAdapter(JaxbIntegerAdapter.class)
    public Integer getFzsl() {
        return fzsl;
    }

    public void setFzsl(Integer fzsl) {
        this.fzsl = fzsl;
    }

    @XmlAttribute(name = "HFZSH")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getHfzsh() {
        return hfzsh;
    }

    public void setHfzsh(String hfzsh) {
        this.hfzsh = hfzsh;
    }

    @XmlAttribute(name = "LZRXM")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getLzrxm() {
        return lzrxm;
    }

    public void setLzrxm(String lzrxm) {
        this.lzrxm = lzrxm;
    }

    @XmlAttribute(name = "LZRZJLB")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getLzrzjlb() {
        return lzrzjlb;
    }

    public void setLzrzjlb(String lzrzjlb) {
        this.lzrzjlb = lzrzjlb;
    }

    @XmlAttribute(name = "LZRZJHM")
    @XmlJavaTypeAdapter(JaxbStringAdapter.class)
    public String getLzrzjhm() {
        return lzrzjhm;
    }

    public void setLzrzjhm(String lzrzjhm) {
        this.lzrzjhm = lzrzjhm;
    }

    @XmlAttribute(name = "LZRDH")
    public String getLzrdh() {
        return lzrdh;
    }

    public void setLzrdh(String lzrdh) {
        this.lzrdh = lzrdh;
    }

    @XmlAttribute(name = "LZRDZ")
    public String getLzrdz() {
        return lzrdz;
    }

    public void setLzrdz(String lzrdz) {
        this.lzrdz = lzrdz;
    }

    @XmlAttribute(name = "LZRYB")
    public String getLzryb() {
        return lzryb;
    }

    public void setLzryb(String lzryb) {
        this.lzryb = lzryb;
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
