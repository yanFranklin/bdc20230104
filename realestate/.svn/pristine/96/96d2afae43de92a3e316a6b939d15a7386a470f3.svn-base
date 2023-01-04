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
@Table(name = "gx_pe_tdsyq")
@XmlRootElement(name = "tdsyq")
public class GxPeTdsyq {
    @Id
    private String qlid;
    private String bdcdyh;
    private String zl;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double zdmj;
    private String mjdw;
    private String yt;
    private String qlxz;
    private String bdcqzh;
    private String djjg;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date djsj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cjsj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date gxsj;
    private String cxsqdh;
    private String wsbh;
    private String gyfs;
    private String gyr;
    private String gyqk;
    private String qszt;
    private String ywh;
    private String qlrdh;
    private String qxdm;
    private String qllx;
    private String sfdy;
    private String sfcf;

    @XmlTransient
    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    @XmlAttribute(name = "bdcdyh", required = true)
    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @XmlAttribute(name = "zl", required = true)
    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @XmlAttribute(name = "zdmj", required = true)
    public Double getZdmj() {
        return zdmj;
    }

    public void setZdmj(Double zdmj) {
        this.zdmj = zdmj;
    }

    @XmlAttribute(name = "mjdw", required = true)
    public String getMjdw() {
        return mjdw;
    }

    public void setMjdw(String mjdw) {
        this.mjdw = mjdw;
    }

    @XmlAttribute(name = "yt", required = true)
    public String getYt() {
        return yt;
    }

    public void setYt(String yt) {
        this.yt = yt;
    }

    @XmlAttribute(name = "qlxz", required = true)
    public String getQlxz() {
        return qlxz;
    }

    public void setQlxz(String qlxz) {
        this.qlxz = qlxz;
    }

    @XmlAttribute(name = "bdcqzh", required = true)
    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    @XmlAttribute(name = "djjg", required = true)
    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    @XmlAttribute(name = "djsj", required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
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

    @XmlAttribute(name = "gyfs", required = true)
    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    @XmlAttribute(name = "gyr", required = true)
    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    @XmlAttribute(name = "gyqk", required = true)
    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    @XmlAttribute(name = "qszt", required = true)
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
}
