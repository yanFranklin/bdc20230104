package cn.gtmap.realestate.common.core.qo.register;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2020/08/11
 * @description 合并不动产单元查询台账QO对象
 */
public class BdcHbBdcdyQO {
    /**
     * 不动产权证号
     */
    private String bdcqzh;
    /**
     * 权利人
     */
    private String qlr;
    /**
     * 不动产单元号
     */
    private String bdcdyh;
    /**
     * 坐落
     */
    private String zl;
    /**
     * 幢号
     */
    private String zh;
    /**
     * 房间号
     */
    private String fjh;
    /**
     * 权利拆分标识
     */
    private String qlcfbs;
    /**
     * 受理编号
     */
    private String slbh;
    /**
     * 权属状态
     */
    private Integer qszt;
    /**
     * 排序字段
     */
    private String field;
    /**
     * 排序类型
     */
    private String order;


    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getZl() {
        return zl;
    }

    public String getQlcfbs() {
        return qlcfbs;
    }

    public void setQlcfbs(String qlcfbs) {
        this.qlcfbs = qlcfbs;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getZh() {
        return zh;
    }

    public void setZh(String zh) {
        this.zh = zh;
    }

    public String getFjh() {
        return fjh;
    }

    public void setFjh(String fjh) {
        this.fjh = fjh;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
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
        return "BdcHbBdcdyQO{" +
                "bdcqzh='" + bdcqzh + '\'' +
                ", qlr='" + qlr + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zl='" + zl + '\'' +
                ", zh='" + zh + '\'' +
                ", fjh='" + fjh + '\'' +
                ", slbh='" + slbh + '\'' +
                ", qszt=" + qszt +
                ", field='" + field + '\'' +
                ", order='" + order + '\'' +
                '}';
    }
}
