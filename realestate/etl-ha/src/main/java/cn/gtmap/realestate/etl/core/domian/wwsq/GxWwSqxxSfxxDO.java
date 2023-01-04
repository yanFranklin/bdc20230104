package cn.gtmap.realestate.etl.core.domian.wwsq;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/19
 * @description 收费信息
 */
@Table(name = "GX_WW_SQXX_SFXX")
public class GxWwSqxxSfxxDO {

    /**
     * 主键
     */
    @Id
    private String sfxxid;
    /**
     * 申请信息表主键
     */
    private String sqxxid;
    /**
     * 合计
     */
    private Double hj;
    /**
     * 权利人类别
     */
    private String qlrlb;
    /**
     * 权利人名称
     */
    private String qlrmc;
    /**
     * 是否开票
     */
    private String sfkp;
    /**
     * 缴费状态
     */
    private String jfzt;
    /**
     *
     */
    private String sfjzjf;
    /**
     * 缴款书号(收费流水号)
     */
    private String businessnumber;

    public String getSfxxid() {
        return sfxxid;
    }

    public void setSfxxid(String sfxxid) {
        this.sfxxid = sfxxid;
    }

    public String getSqxxid() {
        return sqxxid;
    }

    public void setSqxxid(String sqxxid) {
        this.sqxxid = sqxxid;
    }

    public Double getHj() {
        return hj;
    }

    public void setHj(Double hj) {
        this.hj = hj;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getSfkp() {
        return sfkp;
    }

    public void setSfkp(String sfkp) {
        this.sfkp = sfkp;
    }

    public String getJfzt() {
        return jfzt;
    }

    public void setJfzt(String jfzt) {
        this.jfzt = jfzt;
    }

    public String getSfjzjf() {
        return sfjzjf;
    }

    public void setSfjzjf(String sfjzjf) {
        this.sfjzjf = sfjzjf;
    }

    public String getBusinessnumber() {
        return businessnumber;
    }

    public void setBusinessnumber(String businessnumber) {
        this.businessnumber = businessnumber;
    }
}
