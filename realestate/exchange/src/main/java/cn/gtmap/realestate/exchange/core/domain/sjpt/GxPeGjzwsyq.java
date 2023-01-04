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
@Table(name = "gx_pe_gjzwsyq")
@XmlRootElement(name = "gzwsyq")
public class GxPeGjzwsyq {
    @Id
    private String qlid;
    private String bdcdyh;
    private String zl;
    private String gjzwghyt;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double gjzwmj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date tdhysyqssj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date tdhysyjssj;
    private String bdcqzh;
    private String djjg;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date djsj;
    private String gyfs;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cjsj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date gxsj;
    private String cxsqdh;
    private String wsbh;
    private String gyr;
    private String gyqk;
    private String qszt;
    private String ywh;
    private String qlrdh;
    private String qxdm;
    private String gjzwlx;
    private String qllx;
    private String sfdy;
    private String sfcf;
    private String tdhysyqr;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double tdhysymj;

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

    @XmlAttribute(name = "zl",required = true)
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "gjzwghyt",required = true)
    public String getGjzwghyt() {
        return gjzwghyt;
    }

    public void setGjzwghyt(String gjzwghyt) {
        this.gjzwghyt = gjzwghyt;
    }

    @XmlAttribute(name = "gjzwmj",required = true)
    public Double getGjzwmj() {
        return gjzwmj;
    }

    public void setGjzwmj(Double gjzwmj) {
        this.gjzwmj = gjzwmj;
    }

    @XmlAttribute(name = "tdhysyqssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getTdhysyqssj() {
        return tdhysyqssj;
    }

    public void setTdhysyqssj(Date tdhysyqssj) {
        this.tdhysyqssj = tdhysyqssj;
    }

    @XmlAttribute(name = "tdhysyjssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getTdhysyjssj() {
        return tdhysyjssj;
    }

    public void setTdhysyjssj(Date tdhysyjssj) {
        this.tdhysyjssj = tdhysyjssj;
    }

    @XmlAttribute(name = "bdcqzh",required = true)
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
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

    @XmlAttribute(name = "gyfs",required = true)
    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
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

    @XmlAttribute(name = "gyr",required = true)
    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    @XmlAttribute(name = "gyqk",required = true)
    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    @XmlAttribute(name = "qszt",required = true)
    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    @XmlTransient
    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    @XmlTransient
    public String getQlrdh() {
        return qlrdh;
    }

    public void setQlrdh(String qlrdh) {
        this.qlrdh = qlrdh;
    }

    @XmlTransient
    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    @XmlTransient
    public String getGjzwlx() {
        return gjzwlx;
    }

    public void setGjzwlx(String gjzwlx) {
        this.gjzwlx = gjzwlx;
    }

    @XmlTransient
    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    @XmlTransient
    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    @XmlTransient
    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    @XmlTransient
    public String getTdhysyqr() {
        return tdhysyqr;
    }

    public void setTdhysyqr(String tdhysyqr) {
        this.tdhysyqr = tdhysyqr;
    }

    @XmlTransient
    public Double getTdhysymj() {
        return tdhysymj;
    }

    public void setTdhysymj(Double tdhysymj) {
        this.tdhysymj = tdhysymj;
    }
}
