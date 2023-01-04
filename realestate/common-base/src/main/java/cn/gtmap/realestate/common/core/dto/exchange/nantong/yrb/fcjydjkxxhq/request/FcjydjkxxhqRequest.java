package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.fcjydjkxxhq.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:jinfei@gtmap.cn">jinfei</a>
 * @Date 2022/9/20
 * @description 2.23. 房产交易银行端待缴款信息获取【A022】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "FCJYDJKXXHQLIST")
public class FcjydjkxxhqRequest {

    /**
     * SJBH : 收件编号
     * SJRQ : 收件日期
     * ZLFCLFBZ : 增量房存量房标志
     * SJGSDQ : 数据归属地区
     * HTBH : 合同编号
     * NSRSBH : 纳税人识别号
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

    @XmlElement(name = "NSRSBH")
    public String getNSRSBH() {
        return NSRSBH;
    }

    public void setNSRSBH(String NSRSBH) {
        this.NSRSBH = NSRSBH;
    }

    @Override
    public String toString() {
        return "YrbQswspzhqRequest{" +
                "SJBH='" + SJBH + '\'' +
                ", SJRQ='" + SJRQ + '\'' +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", NSRSBH='" + NSRSBH + '\'' +
                '}';
    }
}
