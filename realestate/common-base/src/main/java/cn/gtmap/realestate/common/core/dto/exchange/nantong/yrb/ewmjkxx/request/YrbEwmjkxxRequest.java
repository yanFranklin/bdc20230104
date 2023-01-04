package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.ewmjkxx.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/11
 * @description 1.13.    房产交易缴款结果查询【A019】
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "FCJYZFQUERYLIST")
public class YrbEwmjkxxRequest {


    /**
     * SJBH : 收件编号
     * SJGSDQ : 数据归属地区
     * HTBH : 合同编号
     * DZSPHM : 电子税票号码
     * PZXH : 凭证序号
     * YBTSE : 应补（退）税额
     * LRRDM : 录入人员代码
     */

    @JSONField(name = "sjbh")
    private String SJBH;

    @JSONField(name = "sjgsdq")
    private String SJGSDQ;

    @JSONField(name = "htbh")
    private String HTBH;

    @JSONField(name = "dzsphm")
    private String DZSPHM;

    @JSONField(name = "pzxh")
    private String PZXH;

    @JSONField(name = "ybtse")
    private Double YBTSE;

    @JSONField(name = "lrrdm")
    private String LRRDM;

    @XmlElement(name = "SJBH")
    public String getSJBH() {
        return SJBH;
    }

    public void setSJBH(String SJBH) {
        this.SJBH = SJBH;
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

    @XmlElement(name = "DZSPHM")
    public String getDZSPHM() {
        return DZSPHM;
    }

    public void setDZSPHM(String DZSPHM) {
        this.DZSPHM = DZSPHM;
    }

    @XmlElement(name = "PZXH")
    public String getPZXH() {
        return PZXH;
    }

    public void setPZXH(String PZXH) {
        this.PZXH = PZXH;
    }

    @XmlElement(name = "YBTSE")
    public Double getYBTSE() {
        return YBTSE;
    }

    public void setYBTSE(Double YBTSE) {
        this.YBTSE = YBTSE;
    }

    @XmlElement(name = "LRRDM")
    public String getLRRDM() {
        return LRRDM;
    }

    public void setLRRDM(String LRRDM) {
        this.LRRDM = LRRDM;
    }

    @Override
    public String toString() {
        return "YrbEwmjkxxRequest{" +
                "SJBH='" + SJBH + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", DZSPHM='" + DZSPHM + '\'' +
                ", PZXH='" + PZXH + '\'' +
                ", YBTSE=" + YBTSE +
                ", LRRDM='" + LRRDM + '\'' +
                '}';
    }
}
