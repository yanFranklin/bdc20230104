package cn.gtmap.realestate.common.core.dto.exchange.nantong.yrb.zlfrwjs.request;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/12
 * @description 1.1.    增量房任务接收【A001】登记推送计税  全局数据
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "HOUSEVO")
public class YrbZlfHouseRequest {


    /**
     * sjbh : 收件编号
     * sjrq : 收件日期
     * zlfclfbz : 增量房存量房标志
     * sjgsdq : 数据归属地区
     * lrrdm : 录入人员代码
     */

    private String sjbh;
    private String sjrq;
    private String zlfclfbz;
    private String sjgsdq;
    private String lrrdm;

    @XmlElement(name = "SJBH")
    public String getSjbh() {
        return sjbh;
    }

    public void setSjbh(String sjbh) {
        this.sjbh = sjbh;
    }

    @XmlElement(name = "SJRQ")
    public String getSjrq() {
        return sjrq;
    }

    public void setSjrq(String sjrq) {
        this.sjrq = sjrq;
    }

    @XmlElement(name = "ZLFCLFBZ")
    public String getZlfclfbz() {
        return zlfclfbz;
    }

    public void setZlfclfbz(String zlfclfbz) {
        this.zlfclfbz = zlfclfbz;
    }

    @XmlElement(name = "SJGSDQ")
    public String getSjgsdq() {
        return sjgsdq;
    }

    public void setSjgsdq(String sjgsdq) {
        this.sjgsdq = sjgsdq;
    }

    @XmlElement(name = "LRRDM")
    public String getLrrdm() {
        return lrrdm;
    }

    public void setLrrdm(String lrrdm) {
        this.lrrdm = lrrdm;
    }

    @Override
    public String toString() {
        return "YrbZlfHouseRequest{" +
                "sjbh='" + sjbh + '\'' +
                ", sjrq=" + sjrq +
                ", zlfclfbz='" + zlfclfbz + '\'' +
                ", sjgsdq='" + sjgsdq + '\'' +
                ", lrrdm='" + lrrdm + '\'' +
                '}';
    }
}
