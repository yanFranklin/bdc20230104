package cn.gtmap.realestate.common.core.dto.exchange.xuancheng.hhxx;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.List;

public class HhxxResponseData {

    /**
     * 逝者姓名
     */
    @JSONField(name = "SZXM")
    private String SZXM;

    /**
     * 逝者证件号
     */
    @JSONField(name = "SZZJH")
    private String SZZJH;

    /**
     * 逝者性别
     */
    @JSONField(name = "SZXB")
    private String SZXB;

    /**
     * 逝者户籍地址
     */
    @JSONField(name = "SZHJDZ")
    private String SZHJDZ;

    /**
     * 死亡日期
     */
    @JSONField(name = "SWRQ")
    private Date SWRQ;

    /**
     * 火化日期
     */
    @JSONField(name = "HHKSSJ")
    private Date HHKSSJ;

    public String getSZXM() {
        return SZXM;
    }

    public void setSZXM(String SZXM) {
        this.SZXM = SZXM;
    }

    public String getSZZJH() {
        return SZZJH;
    }

    public void setSZZJH(String SZZJH) {
        this.SZZJH = SZZJH;
    }

    public String getSZXB() {
        return SZXB;
    }

    public void setSZXB(String SZXB) {
        this.SZXB = SZXB;
    }

    public String getSZHJDZ() {
        return SZHJDZ;
    }

    public void setSZHJDZ(String SZHJDZ) {
        this.SZHJDZ = SZHJDZ;
    }

    public Date getSWRQ() {
        return SWRQ;
    }

    public void setSWRQ(Date SWRQ) {
        this.SWRQ = SWRQ;
    }

    public Date getHHKSSJ() {
        return HHKSSJ;
    }

    public void setHHKSSJ(Date HHKSSJ) {
        this.HHKSSJ = HHKSSJ;
    }

    @Override
    public String toString() {
        return "HhxxResponseData{" +
                "SZXM='" + SZXM + '\'' +
                ", SZZJH='" + SZZJH + '\'' +
                ", SZXB='" + SZXB + '\'' +
                ", SZHJDZ='" + SZHJDZ + '\'' +
                ", SWRQ=" + SWRQ +
                ", HHKSSJ=" + HHKSSJ +
                '}';
    }
}
