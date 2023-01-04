package cn.gtmap.realestate.exchange.core.dto.zzcxj.moke;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;

@JSONType(serialzeFeatures = {
        SerializerFeature.WriteNullStringAsEmpty,
        SerializerFeature.WriteNullListAsEmpty,
        SerializerFeature.WriteMapNullValue
})
public class MokeZzdzjPlszDTO {


    /**
     * YWBH : 201901040002
     * BDCQZH : 粤（2019）东莞不动产权第0000906号
     * ZHSF : 粤
     * ZHNF : 2019
     * ZHQX : 东莞
     * ZHLSH : 0000906
     * QLR : 测试
     * GYQK : 单独所有
     * ZL : 东莞市南城区元美路14号黄金花园B区4号楼金涛楼1004号
     * BDCDYH : 441903012003GB00001F00020063
     * QLLX : 国有建设用地使用权/房屋（构筑物）所有权
     * QLXZ : 土地：国有土地 / 房屋：市场化商品房
     * YT : 土地：城镇住宅用地 / 房屋：住宅
     * MJ : 土地：宗地面积123.30㎡ / 房屋：建筑面积104.61㎡
     * SYQX : 土地终止日期：2019年01月28日
     * QLQTZK :
     * FJ :
     * QRCode :
     * QRCode1 :
     * DJSJ : 2019-02-18 23:10:05
     * SJN : 2019
     * SJY : 02
     * SJR : 18
     * CREATEDATE : 2019-05-16
     * ZDT : URL地址/base64
     * FHT : URL地址/base64
     */
    @JSONField(name = "YWBH")
    private String YWBH;
    @JSONField(name = "BDCQZH")
    private String BDCQZH;
    @JSONField(name = "ZHSF")
    private String ZHSF;
    @JSONField(name = "ZHNF")
    private String ZHNF;
    @JSONField(name = "ZHQX")
    private String ZHQX;
    @JSONField(name = "ZHLSH")
    private String ZHLSH;
    @JSONField(name = "QLR")
    private String QLR;
    @JSONField(name = "GYQK")
    private String GYQK;
    @JSONField(name = "ZL")
    private String ZL;
    @JSONField(name = "BDCDYH")
    private String BDCDYH;
    @JSONField(name = "QLLX")
    private String QLLX;
    @JSONField(name = "QLXZ")
    private String QLXZ;
    @JSONField(name = "YT")
    private String YT;
    @JSONField(name = "MJ")
    private String MJ;
    @JSONField(name = "SYQX")
    private String SYQX;
    @JSONField(name = "QLQTZK")
    private String QLQTZK;
    @JSONField(name = "FJ")
    private String FJ;
    @JSONField(name = "QRCode")
    private String QRCode;
    @JSONField(name = "QRCode1")
    private String QRCode1;
    @JSONField(name = "DJSJ")
    private String DJSJ;
    @JSONField(name = "SJN")
    private String SJN;
    @JSONField(name = "SJY")
    private String SJY;
    @JSONField(name = "SJR")
    private String SJR;
    @JSONField(name = "CREATEDATE")
    private String CREATEDATE;
    @JSONField(name = "ZDT")
    private String ZDT;
    @JSONField(name = "FHT")
    private String FHT;


    public String getYWBH() {
        return YWBH;
    }

    public void setYWBH(String YWBH) {
        this.YWBH = YWBH;
    }

    public String getBDCQZH() {
        return BDCQZH;
    }

    public void setBDCQZH(String BDCQZH) {
        this.BDCQZH = BDCQZH;
    }

    public String getZHSF() {
        return ZHSF;
    }

    public void setZHSF(String ZHSF) {
        this.ZHSF = ZHSF;
    }

    public String getZHNF() {
        return ZHNF;
    }

    public void setZHNF(String ZHNF) {
        this.ZHNF = ZHNF;
    }

    public String getZHQX() {
        return ZHQX;
    }

    public void setZHQX(String ZHQX) {
        this.ZHQX = ZHQX;
    }

    public String getZHLSH() {
        return ZHLSH;
    }

    public void setZHLSH(String ZHLSH) {
        this.ZHLSH = ZHLSH;
    }

    public String getQLR() {
        return QLR;
    }

    public void setQLR(String QLR) {
        this.QLR = QLR;
    }

    public String getGYQK() {
        return GYQK;
    }

    public void setGYQK(String GYQK) {
        this.GYQK = GYQK;
    }

    public String getZL() {
        return ZL;
    }

    public void setZL(String ZL) {
        this.ZL = ZL;
    }

    public String getBDCDYH() {
        return BDCDYH;
    }

    public void setBDCDYH(String BDCDYH) {
        this.BDCDYH = BDCDYH;
    }

    public String getQLLX() {
        return QLLX;
    }

    public void setQLLX(String QLLX) {
        this.QLLX = QLLX;
    }

    public String getQLXZ() {
        return QLXZ;
    }

    public void setQLXZ(String QLXZ) {
        this.QLXZ = QLXZ;
    }

    public String getYT() {
        return YT;
    }

    public void setYT(String YT) {
        this.YT = YT;
    }

    public String getMJ() {
        return MJ;
    }

    public void setMJ(String MJ) {
        this.MJ = MJ;
    }

    public String getSYQX() {
        return SYQX;
    }

    public void setSYQX(String SYQX) {
        this.SYQX = SYQX;
    }

    public String getQLQTZK() {
        return QLQTZK;
    }

    public void setQLQTZK(String QLQTZK) {
        this.QLQTZK = QLQTZK;
    }

    public String getFJ() {
        return FJ;
    }

    public void setFJ(String FJ) {
        this.FJ = FJ;
    }

    public String getQRCode() {
        return QRCode;
    }

    public void setQRCode(String QRCode) {
        this.QRCode = QRCode;
    }

    public String getQRCode1() {
        return QRCode1;
    }

    public void setQRCode1(String QRCode1) {
        this.QRCode1 = QRCode1;
    }

    public String getDJSJ() {
        return DJSJ;
    }

    public void setDJSJ(String DJSJ) {
        this.DJSJ = DJSJ;
    }

    public String getSJN() {
        return SJN;
    }

    public void setSJN(String SJN) {
        this.SJN = SJN;
    }

    public String getSJY() {
        return SJY;
    }

    public void setSJY(String SJY) {
        this.SJY = SJY;
    }

    public String getSJR() {
        return SJR;
    }

    public void setSJR(String SJR) {
        this.SJR = SJR;
    }

    public String getCREATEDATE() {
        return CREATEDATE;
    }

    public void setCREATEDATE(String CREATEDATE) {
        this.CREATEDATE = CREATEDATE;
    }

    public String getZDT() {
        return ZDT;
    }

    public void setZDT(String ZDT) {
        this.ZDT = ZDT;
    }

    public String getFHT() {
        return FHT;
    }

    public void setFHT(String FHT) {
        this.FHT = FHT;
    }

    @Override
    public String toString() {
        return "MokeZzdzjPlszDTO{" +
                "YWBH='" + YWBH + '\'' +
                ", BDCQZH='" + BDCQZH + '\'' +
                ", ZHSF='" + ZHSF + '\'' +
                ", ZHNF='" + ZHNF + '\'' +
                ", ZHQX='" + ZHQX + '\'' +
                ", ZHLSH='" + ZHLSH + '\'' +
                ", QLR='" + QLR + '\'' +
                ", GYQK='" + GYQK + '\'' +
                ", ZL='" + ZL + '\'' +
                ", BDCDYH='" + BDCDYH + '\'' +
                ", QLLX='" + QLLX + '\'' +
                ", QLXZ='" + QLXZ + '\'' +
                ", YT='" + YT + '\'' +
                ", MJ='" + MJ + '\'' +
                ", SYQX='" + SYQX + '\'' +
                ", QLQTZK='" + QLQTZK + '\'' +
                ", FJ='" + FJ + '\'' +
                ", QRCode='" + QRCode + '\'' +
                ", QRCode1='" + QRCode1 + '\'' +
                ", DJSJ='" + DJSJ + '\'' +
                ", SJN='" + SJN + '\'' +
                ", SJY='" + SJY + '\'' +
                ", SJR='" + SJR + '\'' +
                ", CREATEDATE='" + CREATEDATE + '\'' +
                ", ZDT='" + ZDT + '\'' +
                ", FHT='" + FHT + '\'' +
                '}';
    }
}
