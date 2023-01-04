package cn.gtmap.realestate.exchange.core.dto.hefei.swxx.tssw.request;/*
 * @author <a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 * @version 1.0, 2018/10/22
 * @description
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *@Author:<a href="mailto:huangzijian@gtmap.cn">huangzijian</a>
 *@Description: 核税明细表
 *@Date 13:19 2018/10/22
 */
@Entity
@Table( name ="YCSL_HS_MX" )
public class YcslHsMx implements Serializable {
    /**
     * 明细id
     */
    @Column(name = "MXID" )
    @Id
    private String mxid;
    /**
     * 核税id
     */
    @Column(name = "HSID" )
    private String hsid;
    /**
     * 明细种类代码
     */
    @Column(name = "MXZLDM" )
    private String mxzldm;
    /**
     * 明细种类名称
     */
    @Column(name = "MXZLMC" )
    private String mxzlmc;
    /**
     * 应纳税额
     */
    @Column(name = "YNSE" )
    private Double ynse;
    /**
     * 减免税额
     */
    @Column(name = "JMSE" )
    private Double jmse;
    /**
     * 实际应征
     */
    @Column(name = "SJYZ" )
    private Double sjyz;
    /**
     * 顺序号
     */
    @Column(name = "SXH" )
    private Integer sxh;

    public String getMxid() {
        return mxid;
    }

    public void setMxid(String mxid) {
        this.mxid = mxid;
    }

    public String getHsid() {
        return hsid;
    }

    public void setHsid(String hsid) {
        this.hsid = hsid;
    }

    public String getMxzldm() {
        return mxzldm;
    }

    public void setMxzldm(String mxzldm) {
        this.mxzldm = mxzldm;
    }

    public String getMxzlmc() {
        return mxzlmc;
    }

    public void setMxzlmc(String mxzlmc) {
        this.mxzlmc = mxzlmc;
    }

    public Double getYnse() {
        return ynse;
    }

    public void setYnse(Double ynse) {
        this.ynse = ynse;
    }

    public Double getJmse() {
        return jmse;
    }

    public void setJmse(Double jmse) {
        this.jmse = jmse;
    }

    public Double getSjyz() {
        return sjyz;
    }

    public void setSjyz(Double sjyz) {
        this.sjyz = sjyz;
    }

    public Integer getSxh() {
        return sxh;
    }

    public void setSxh(Integer sxh) {
        this.sxh = sxh;
    }
}
