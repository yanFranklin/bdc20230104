package cn.gtmap.realestate.exchange.core.domain.sjpt;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty
})
@Table(name = "gx_pe_nydsyq")
public class GxPeNydsyq {
    @Id
    private String qlid;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cjsj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date gxsj;
    private String cxsqdh;
    private String wsbh;
    private String bdcdyh;
    private String zl;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Double cbmj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cbqssj;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date cbjssj;
    private String bdcqzh;
    private String djjg;
    private String tdsyqxz;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss",serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Date djsj;
    private String gyfs;
    private String gyr;
    private String gyqk;
    private String qszt;
    private String ywh;
    private String qlrdh;
    private String qxdm;
    private String syttlx;
    private String qllx;
    private String sfdy;
    private String sfcf;
    private String yzyfs;
    private String cyzl;
    @JSONField(serialzeFeatures ={SerializerFeature.WriteMapNullValue})
    private Integer syzcl;
    private String fbfdm;
    private String fbfmc;

    public String getQlid() {
        return qlid;
    }

    public void setQlid(String qlid) {
        this.qlid = qlid;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public String getCxsqdh() {
        return cxsqdh;
    }

    public void setCxsqdh(String cxsqdh) {
        this.cxsqdh = cxsqdh;
    }

    public String getWsbh() {
        return wsbh;
    }

    public void setWsbh(String wsbh) {
        this.wsbh = wsbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public Double getCbmj() {
        return cbmj;
    }

    public void setCbmj(Double cbmj) {
        this.cbmj = cbmj;
    }

    public Date getCbqssj() {
        return cbqssj;
    }

    public void setCbqssj(Date cbqssj) {
        this.cbqssj = cbqssj;
    }

    public Date getCbjssj() {
        return cbjssj;
    }

    public void setCbjssj(Date cbjssj) {
        this.cbjssj = cbjssj;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getDjjg() {
        return djjg;
    }

    public void setDjjg(String djjg) {
        this.djjg = djjg;
    }

    public String getTdsyqxz() {
        return tdsyqxz;
    }

    public void setTdsyqxz(String tdsyqxz) {
        this.tdsyqxz = tdsyqxz;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public String getGyfs() {
        return gyfs;
    }

    public void setGyfs(String gyfs) {
        this.gyfs = gyfs;
    }

    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    public String getGyqk() {
        return gyqk;
    }

    public void setGyqk(String gyqk) {
        this.gyqk = gyqk;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getQlrdh() {
        return qlrdh;
    }

    public void setQlrdh(String qlrdh) {
        this.qlrdh = qlrdh;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getSyttlx() {
        return syttlx;
    }

    public void setSyttlx(String syttlx) {
        this.syttlx = syttlx;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getSfdy() {
        return sfdy;
    }

    public void setSfdy(String sfdy) {
        this.sfdy = sfdy;
    }

    public String getSfcf() {
        return sfcf;
    }

    public void setSfcf(String sfcf) {
        this.sfcf = sfcf;
    }

    public String getYzyfs() {
        return yzyfs;
    }

    public void setYzyfs(String yzyfs) {
        this.yzyfs = yzyfs;
    }

    public String getCyzl() {
        return cyzl;
    }

    public void setCyzl(String cyzl) {
        this.cyzl = cyzl;
    }

    public Integer getSyzcl() {
        return syzcl;
    }

    public void setSyzcl(Integer syzcl) {
        this.syzcl = syzcl;
    }

    public String getFbfdm() {
        return fbfdm;
    }

    public void setFbfdm(String fbfdm) {
        this.fbfdm = fbfdm;
    }

    public String getFbfmc() {
        return fbfmc;
    }

    public void setFbfmc(String fbfmc) {
        this.fbfmc = fbfmc;
    }
}