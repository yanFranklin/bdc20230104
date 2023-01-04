package cn.gtmap.realestate.exchange.core.domain.sjpt;

import cn.gtmap.realestate.common.util.jaxb.JaxbDateTsAdapter;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullListAsEmpty
})
@Table(name = "gx_pe_dyaq")
@XmlRootElement(name = "dyaq")
public class GxPeDyaq {
    @Id
    private String qlid;
    private String bdcdyh;
    private String dybdclx;
    private String zl;
    private String dyr;
    private String dyfs;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double bdbzzqse;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date zwlxqssj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date zwlxjssj;
    private String bdcdjzmh;
    private String djjg;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date djsj;
    private String dyqr;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cjsj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date gxsj;
    private String cxsqdh;
    private String wsbh;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date zxsj;
    private String ywh;
    private String qszt;
    private String qxdm;
    private String zjjzwzl;
    private String zjjzwdyfw;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double zgzqse;

    @XmlTransient
    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    @XmlAttribute(name = "bdcdyh",required = true)
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "dybdclx",required = true)
    public String getDybdclx() {
        return dybdclx;
    }

    public void setDybdclx(String dybdclx) {
        this.dybdclx = dybdclx;
    }

    @XmlAttribute(name = "zl",required = true)
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlTransient
    public String getDyr() {
        return dyr;
    }

    public void setDyr(String dyr) {
        this.dyr = dyr;
    }

    @XmlAttribute(name = "dyfs",required = true)
    public String getDyfs() {
        return dyfs;
    }

    public void setDyfs(String dyfs) {
        this.dyfs = dyfs;
    }

    @XmlTransient
    public Double getBdbzzqse() {
        return bdbzzqse;
    }

    public void setBdbzzqse(Double bdbzzqse) {
        this.bdbzzqse = bdbzzqse;
    }

    @XmlAttribute(name = "zwlxqssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getZwlxqssj() {
        return zwlxqssj;
    }

    public void setZwlxqssj(Date zwlxqssj) {
        this.zwlxqssj = zwlxqssj;
    }

    @XmlAttribute(name = "zwlxjssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getZwlxjssj() {
        return zwlxjssj;
    }

    public void setZwlxjssj(Date zwlxjssj) {
        this.zwlxjssj = zwlxjssj;
    }

    @XmlAttribute(name = "bdcdjzmh",required = true)
    public String getBdcdjzmh() {
        return bdcdjzmh;
    }

    public void setBdcdjzmh(String bdcdjzmh) {
        this.bdcdjzmh = bdcdjzmh;
    }

    @XmlAttribute(name = "djjg",required = true)
    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @XmlAttribute(name = "djsj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    @XmlTransient
    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    @XmlTransient
    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    @XmlTransient
    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @XmlTransient
    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    @XmlTransient
    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }

    @XmlAttribute(name = "zxsj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    @XmlTransient
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlTransient
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    @XmlTransient
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlTransient
    public String getZjjzwzl() {
        return zjjzwzl;
    }

    public void setZjjzwzl(String zjjzwzl) {
        this.zjjzwzl = zjjzwzl;
    }

    @XmlTransient
    public String getZjjzwdyfw() {
        return zjjzwdyfw;
    }

    public void setZjjzwdyfw(String zjjzwdyfw) {
        this.zjjzwdyfw = zjjzwdyfw;
    }

    @XmlTransient
    public Double getZgzqse() {
        return zgzqse;
    }

    public void setZgzqse(Double zgzqse) {
        this.zgzqse = zgzqse;
    }
}
