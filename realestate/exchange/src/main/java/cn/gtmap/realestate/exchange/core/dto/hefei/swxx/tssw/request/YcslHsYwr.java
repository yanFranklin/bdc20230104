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
 *@Description:卖方核税表
 *@Date 13:19 2018/10/22
 */
@Entity
@Table( name ="YCSL_HS_YWR" )
public class YcslHsYwr implements Serializable {
    /**
     * 核税ID
     */
    @Column(name = "HSID" )
    @Id
    private String hsid;
    /**
     * 项目id
     */
    @Column(name = "PROID" )
    private String proid;
    /**
     * 核定计税价格
     */
    @Column(name = "HSDJJG" )
    private Double hsdjjg;
    /**
     * 应征税额合计
     */
    @Column(name = "YZSEHJ" )
    private Double yzsehj;
    /**
     * 减免税额合计
     */
    @Column(name = "JMSEHJ" )
    private Double jmsehj;
    /**
     * 实际应征合计
     */
    @Column(name = "SJYZHJ" )
    private Double sjyzhj;


    public String getHsid() {
        return hsid;
    }

    public void setHsid(String hsid) {
        this.hsid = hsid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public Double getHsdjjg() {
        return hsdjjg;
    }

    public void setHsdjjg(Double hsdjjg) {
        this.hsdjjg = hsdjjg;
    }

    public Double getYzsehj() {
        return yzsehj;
    }

    public void setYzsehj(Double yzsehj) {
        this.yzsehj = yzsehj;
    }

    public Double getJmsehj() {
        return jmsehj;
    }

    public void setJmsehj(Double jmsehj) {
        this.jmsehj = jmsehj;
    }

    public Double getSjyzhj() {
        return sjyzhj;
    }

    public void setSjyzhj(Double sjyzhj) {
        this.sjyzhj = sjyzhj;
    }
}
