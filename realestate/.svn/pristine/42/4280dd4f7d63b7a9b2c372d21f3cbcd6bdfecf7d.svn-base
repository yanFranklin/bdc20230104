package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.yxzlxxjs.request;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/8
 * @description 影像文件DTO
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "YXZLXXJSLIST")
public class YrbYxzlxxjsList {

    /**
     * SJBH : 收件编号
     * SJRQ : 收件日期
     * ZLFCLFBZ : 增量房存量房标志
     * SJGSDQ : 数据归属地区
     * HTBH : 合同编号
     * JYUUID : 交易编号
     * FWUUID : 房屋编号
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

    private List<YrbYwwjxx> ywwjxxList;

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

    @XmlElement(name = "YXWJXX")
    public List<YrbYwwjxx> getYwwjxxList() {
        return ywwjxxList;
    }

    public void setYwwjxxList(List<YrbYwwjxx> ywwjxxList) {
        this.ywwjxxList = ywwjxxList;
    }

    @Override
    public String toString() {
        return "YrbYxzlxxjsList{" +
                "SJBH='" + SJBH + '\'' +
                ", SJRQ=" + SJRQ +
                ", ZLFCLFBZ='" + ZLFCLFBZ + '\'' +
                ", SJGSDQ='" + SJGSDQ + '\'' +
                ", HTBH='" + HTBH + '\'' +
                ", JYUUID='" + JYUUID + '\'' +
                ", FWUUID='" + FWUUID + '\'' +
                ", ywwjxxList=" + ywwjxxList +
                '}';
    }
}
