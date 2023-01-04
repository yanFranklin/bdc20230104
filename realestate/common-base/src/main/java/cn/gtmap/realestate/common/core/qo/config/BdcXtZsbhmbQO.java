package cn.gtmap.realestate.common.core.qo.config;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/01/11
 * @description 证书编号模板查询QO定义
 */
public class BdcXtZsbhmbQO {
    /**
     * 年份
     */
    private String nf;
    /**
     * 区县代码
     */
    private String qxdm;
    /**
     * 不动产权证号
     */
    private String bdcqzh;
    /**
     * 省区市简称
     */
    private String sqsjc;
    /**
     * 所在市县全称
     */
    private String szsxqc;
    /**
     * 省区代码
     */
    private String sqdm;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序类型
     */
    private String order;


    public String getNf() {
        return nf;
    }

    public void setNf(String nf) {
        this.nf = nf;
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getSqsjc() {
        return sqsjc;
    }

    public void setSqsjc(String sqsjc) {
        this.sqsjc = sqsjc;
    }

    public String getSzsxqc() {
        return szsxqc;
    }

    public void setSzsxqc(String szsxqc) {
        this.szsxqc = szsxqc;
    }

    public String getSqdm() {
        return sqdm;
    }

    public void setSqdm(String sqdm) {
        this.sqdm = sqdm;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "BdcXtZsbhmbQO{" +
                "nf='" + nf + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", bdcqzh='" + bdcqzh + '\'' +
                ", sqsjc='" + sqsjc + '\'' +
                ", szsxqc='" + szsxqc + '\'' +
                ", sqdm='" + sqdm + '\'' +
                ", field='" + field + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
