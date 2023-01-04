package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.clfskxxhq.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 2.12.存量房计税信息获取【A005】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "CLFSKXXHQLIST")
public class YrbClfskxxhqRequestDTO {


    /**
     * SJBH : 收件编号
     * SJRQ : 收件日期
     * ZLFCLFBZ : 增量房存量房标志
     * SJGSDQ : 数据归属地区
     * LRRDM : 录入人员代码
     * HTBH : 合同编号
     * JYUUID : 交易编号
     * FWUUID : 房屋编号
     * <NSRSBH>纳税人识别号</NSRSBH>
     * <FWZLDZ>房屋坐落地址</FWZLDZ>
     * <SBPZXH>申报凭证序号</SBPZXH>
     */
    @JSONField(name = "sjbh")
    private String SJBH;

    @JSONField(name = "sjrq")
    private String SJRQ;

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

    @JSONField(name = "fwzldz")
    private String FWZLDZ;

    @JSONField(name = "sbpzxh")
    private String SBPZXH;

    @XmlElement(name = "SJBH")
    public String getSJBH() {
        return SJBH;
    }

    public void setSJBH(String SJBH) {
        this.SJBH = SJBH;
    }

    @XmlElement(name = "SJRQ")
    public String getSJRQ() {
        return SJRQ;
    }

    public void setSJRQ(String SJRQ) {
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

    @XmlElement(name = "FWZLDZ")
    public String getFWZLDZ() {
        return FWZLDZ;
    }

    public void setFWZLDZ(String FWZLDZ) {
        this.FWZLDZ = FWZLDZ;
    }

    @XmlElement(name = "SBPZXH")
    public String getSBPZXH() {
        return SBPZXH;
    }

    public void setSBPZXH(String SBPZXH) {
        this.SBPZXH = SBPZXH;
    }

    @Override
    public String toString() {
        return "YrbClfskxxhqRequestDTO{" +
                "SJBH='" + SJBH + '\'' +
                ", SJRQ='" + SJRQ + '\'' +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", JYUUID='" + JYUUID + '\'' +
                ", FWUUID='" + FWUUID + '\'' +
                ", NSRSBH='" + NSRSBH + '\'' +
                ", FWZLDZ='" + FWZLDZ + '\'' +
                ", SBPZXH='" + SBPZXH + '\'' +
                '}';
    }
}
