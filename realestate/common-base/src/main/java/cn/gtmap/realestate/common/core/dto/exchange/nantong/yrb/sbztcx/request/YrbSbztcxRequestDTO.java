package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.sbztcx.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/9
 * @description 2.10.    申报状态信息【A010】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "SBZTXXLIST")
public class YrbSbztcxRequestDTO {


    /**
     * SJBH : 收件编号
     * SJRQ : 收件日期
     * ZLFCLFBZ : 增量房存量房标志
     * SJGSDQ : 数据归属地区
     * LRRDM : 录入人员代码
     * HTBH : 合同编号
     * JYUUID : 交易编号
     * FWUUID : 房屋编号
     * RWZT : 任务状态
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

    @Override
    public String toString() {
        return "YrbSbztcxRequestDTO{" +
                "SJBH='" + SJBH + '\'' +
                ", SJRQ='" + SJRQ + '\'' +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", JYUUID='" + JYUUID + '\'' +
                ", FWUUID='" + FWUUID + '\'' +
                ", NSRSBH='" + NSRSBH + '\'' +
                '}';
    }
}
