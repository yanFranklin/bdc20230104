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
@Table(name = "gx_pe_fdcq")
@XmlRootElement(name = "fdcq")
public class GxPeFdcq {
    @Id
    private String qlid;
    private String bdcdyh;
    private String fdzl;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double jzmj;
    private String ghyt;
    private String fwxz;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date jgsj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date tdsyqssj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date tdsyjssj;
    private String bdcqzh;
    private String djjg;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double zyjzmj;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double ftjzmj;
    @JSONField(format="yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date djsj;
    private String gyfs;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double fdcjyjg;
    private String fwjg;
    private String fwlx;
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
    private String qllx;
    private String sfdy;
    private String sfcf;
    private String tdqlxz;
    private String tdsyqr;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double dytdmj;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double fttdmj;

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

    @XmlAttribute(name = "fdzl",required = true)
    public String getFdzl() {
        return fdzl;
    }

    public void setFdzl(String fdzl) {
        this.fdzl = fdzl;
    }

    @XmlAttribute(name = "jzmj",required = true)
    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    @XmlAttribute(name = "ghyt",required = true)
    public String getGhyt() {
        return ghyt;
    }

    public void setGhyt(String ghyt) {
        this.ghyt = ghyt;
    }

    @XmlAttribute(name = "fwxz",required = true)
    public String getFwxz() {
        return fwxz;
    }

    public void setFwxz(String fwxz) {
        this.fwxz = fwxz;
    }

    @XmlTransient
    public Date getJgsj() {
        return jgsj;
    }

    public void setJgsj(Date jgsj) {
        this.jgsj = jgsj;
    }

    @XmlAttribute(name = "tdsyqssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getTdsyqssj() {
        return tdsyqssj;
    }

    public void setTdsyqssj(Date tdsyqssj) {
        this.tdsyqssj = tdsyqssj;
    }

    @XmlAttribute(name = "tdsyjssj",required = true)
    @XmlJavaTypeAdapter(JaxbDateTsAdapter.class)
    public Date getTdsyjssj() {
        return tdsyjssj;
    }

    public void setTdsyjssj(Date tdsyjssj) {
        this.tdsyjssj = tdsyjssj;
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

    @XmlTransient
    public Double getZyjzmj() {
        return zyjzmj;
    }

    public void setZyjzmj(Double zyjzmj) {
        this.zyjzmj = zyjzmj;
    }

    @XmlTransient
    public Double getFtjzmj() {
        return ftjzmj;
    }

    public void setFtjzmj(Double ftjzmj) {
        this.ftjzmj = ftjzmj;
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
    public Double getFdcjyjg() {
        return fdcjyjg;
    }

    public void setFdcjyjg(Double fdcjyjg) {
        this.fdcjyjg = fdcjyjg;
    }

    @XmlTransient
    public String getFwjg() {
        return fwjg;
    }

    public void setFwjg(String fwjg) {
        this.fwjg = fwjg;
    }

    @XmlTransient
    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
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
    public String getTdqlxz() {
        return tdqlxz;
    }

    public void setTdqlxz(String tdqlxz) {
        this.tdqlxz = tdqlxz;
    }

    @XmlTransient
    public String getTdsyqr() {
        return tdsyqr;
    }

    public void setTdsyqr(String tdsyqr) {
        this.tdsyqr = tdsyqr;
    }

    @XmlTransient
    public Double getDytdmj() {
        return dytdmj;
    }

    public void setDytdmj(Double dytdmj) {
        this.dytdmj = dytdmj;
    }

    @XmlTransient
    public Double getFttdmj() {
        return fttdmj;
    }

    public void setFttdmj(Double fttdmj) {
        this.fttdmj = fttdmj;
    }
}
