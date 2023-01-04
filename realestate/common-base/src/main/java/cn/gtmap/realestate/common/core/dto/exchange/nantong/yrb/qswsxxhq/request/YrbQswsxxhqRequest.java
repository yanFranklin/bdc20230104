package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.qswsxxhq.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.7.    契税完税信息获取【A009】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "FCJYSKXXLIST")
public class YrbQswsxxhqRequest {


    /**
     * SJBH : 收件编号
     * SJRQ : 收件日期
     * ZLFCLFBZ : 增量房存量房标志
     * SJGSDQ : 数据归属地区
     * HTBH : 合同编号
     * JYUUID : 交易编号
     * FWUUID : 房屋编号
     * NSRSBH : 纳税人识别号
     * TDFWDZ : 土地房屋地址
     * NSRMC : 纳税人名称
     * DZSPHM : 电子税票号码
     */
    @JSONField(name = "sjbh")
    private String SJBH;

    @JSONField(name = "sjrq")
    private Date SJRQ;

    @JSONField(name = "zlfclfbz")
    private String ZLFCLFBZ;

    @JSONField(name = "sjgsdq")
    private String SJGSDQ;

    @JSONField(name = "htbh")
    private String HTBH;

    @JSONField(name = "jyuuid")
    private String JYUUID;

    @JSONField(name = "fwuuid")
    private String FWUUID;

    @JSONField(name = "nsrsbh")
    private String NSRSBH;

    @JSONField(name = "tdfwdz")
    private String TDFWDZ;

    @JSONField(name = "nsrmc")
    private String NSRMC;

    @JSONField(name = "dzsphm")
    private String DZSPHM;

    @XmlElement(name = "SJBH")
    public String getSJBH() {
        return SJBH;
    }

    public void setSJBH(String SJBH) {
        this.SJBH = SJBH;
    }

    @XmlElement(name = "SJRQ")
    public Date getSJRQ() {
        return SJRQ;
    }

    public void setSJRQ(Date SJRQ) {
        this.SJRQ = SJRQ;
    }

    @XmlElement(name = "ZLFCLFBZ")
    public String getZLFCLFBZ() {
        return ZLFCLFBZ;
    }

    public void setZLFCLFBZ(String ZLFCLFBZ) {
        this.ZLFCLFBZ = ZLFCLFBZ;
    }

    @XmlElement(name = "SJGSDQ")
    public String getSJGSDQ() {
        return SJGSDQ;
    }

    public void setSJGSDQ(String SJGSDQ) {
        this.SJGSDQ = SJGSDQ;
    }

    @XmlElement(name = "HTBH")
    public String getHTBH() {
        return HTBH;
    }

    public void setHTBH(String HTBH) {
        this.HTBH = HTBH;
    }

    @XmlElement(name = "JYUUID")
    public String getJYUUID() {
        return JYUUID;
    }

    public void setJYUUID(String JYUUID) {
        this.JYUUID = JYUUID;
    }

    @XmlElement(name = "FWUUID")
    public String getFWUUID() {
        return FWUUID;
    }

    public void setFWUUID(String FWUUID) {
        this.FWUUID = FWUUID;
    }

    @XmlElement(name = "NSRSBH")
    public String getNSRSBH() {
        return NSRSBH;
    }

    public void setNSRSBH(String NSRSBH) {
        this.NSRSBH = NSRSBH;
    }

    @XmlElement(name = "TDFWDZ")
    public String getTDFWDZ() {
        return TDFWDZ;
    }

    public void setTDFWDZ(String TDFWDZ) {
        this.TDFWDZ = TDFWDZ;
    }

    @XmlElement(name = "NSRMC")
    public String getNSRMC() {
        return NSRMC;
    }

    public void setNSRMC(String NSRMC) {
        this.NSRMC = NSRMC;
    }

    @XmlElement(name = "DZSPHM")
    public String getDZSPHM() {
        return DZSPHM;
    }

    public void setDZSPHM(String DZSPHM) {
        this.DZSPHM = DZSPHM;
    }

    @Override
    public String toString() {
        return "YrbQswsxxhqRequest{" +
                "SJBH='" + SJBH + '\'' +
                ", SJRQ=" + SJRQ +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", JYUUID='" + JYUUID + '\'' +
                ", FWUUID='" + FWUUID + '\'' +
                ", NSRSBH='" + NSRSBH + '\'' +
                ", TDFWDZ='" + TDFWDZ + '\'' +
                ", NSRMC='" + NSRMC + '\'' +
                ", DZSPHM='" + DZSPHM + '\'' +
                '}';
    }
}
