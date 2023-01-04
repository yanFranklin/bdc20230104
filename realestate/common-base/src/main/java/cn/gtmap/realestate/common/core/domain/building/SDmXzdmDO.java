package cn.gtmap.realestate.common.core.domain.building;

import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-01-24
 * @description
 */
@Table(name = "s_dm_xzdm")
public class SDmXzdmDO {
    /**
     * 行政级别
     */
    private Integer xzjb;
    /**
     * 行政简称
     */
    private String xzjc;
    /**
     * 行政名称
     */
    private String xzmc;
    /**
     * 行政代码
     */
    private String xzdm;
    /**
     * 备注
     */
    private String bz;

    public Integer getXzjb() {
        return xzjb;
    }

    public void setXzjb(Integer xzjb) {
        this.xzjb = xzjb;
    }

    public String getXzjc() {
        return xzjc;
    }

    public void setXzjc(String xzjc) {
        this.xzjc = xzjc;
    }

    public String getXzmc() {
        return xzmc;
    }

    public void setXzmc(String xzmc) {
        this.xzmc = xzmc;
    }

    public String getXzdm() {
        return xzdm;
    }

    public void setXzdm(String xzdm) {
        this.xzdm = xzdm;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }
}
