package cn.gtmap.realestate.common.core.dto.building;

/**
 * @author <a href="mailto:wangyongming@gtmap.cn">wangyongming</a>
 * @version 1.0  2021-11-18
 * @description 根据籍权信息查询宗地信息出参
 */
public class ZdxxByJqxxDTO {
    /**
     * 宗地代码
     */
    private String zddm;

    /**
     * 坐落
     */
    private String zl;

    /**
     * 不动产单元号
     */
    private String bdcdyh;

    /**
     * 宗地面积
     */
    private Double zdmj;

    /**
     * 权利人名称
     */
    private String qlrmc;

    /**
     * 权利类型代码
     */
    private String qllxdm;

    /**
     * 权利类型名称
     */
    private String qllxmc;

    /**
     * 地籍号
     */
    private String djh;

    /**
     * 不动产类型
     */
    private String bdclx;

    /**
     * 不动产类型名称
     */
    private String bdclxmc;

    /**
     * 宗地用途
     */
    private String zdyt;

    /**
     * 宗地用途名称
     */
    private String zdytmc;

    public String getZddm() {
        return zddm;
    }

    public void setZddm(String zddm) {
        this.zddm = zddm;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Double getZdmj() {
        return zdmj;
    }

    public void setZdmj(Double zdmj) {
        this.zdmj = zdmj;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQllxdm() {
        return qllxdm;
    }

    public void setQllxdm(String qllxdm) {
        this.qllxdm = qllxdm;
    }

    public String getQllxmc() {
        return qllxmc;
    }

    public void setQllxmc(String qllxmc) {
        this.qllxmc = qllxmc;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getBdclx() {
        return bdclx;
    }

    public void setBdclx(String bdclx) {
        this.bdclx = bdclx;
    }

    public String getBdclxmc() {
        return bdclxmc;
    }

    public void setBdclxmc(String bdclxmc) {
        this.bdclxmc = bdclxmc;
    }

    public String getZdyt() {
        return zdyt;
    }

    public void setZdyt(String zdyt) {
        this.zdyt = zdyt;
    }

    public String getZdytmc() {
        return zdytmc;
    }

    public void setZdytmc(String zdytmc) {
        this.zdytmc = zdytmc;
    }

    @Override
    public String toString() {
        return "ZdxxByJqxxDTO{" +
                "zddm='" + zddm + '\'' +
                ", zl='" + zl + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", zdmj=" + zdmj +
                ", qlrmc='" + qlrmc + '\'' +
                ", qllxdm='" + qllxdm + '\'' +
                ", qllxmc='" + qllxmc + '\'' +
                ", djh='" + djh + '\'' +
                ", bdclx='" + bdclx + '\'' +
                ", bdclxmc='" + bdclxmc + '\'' +
                ", zdyt='" + zdyt + '\'' +
                ", zdytmc='" + zdytmc + '\'' +
                '}';
    }
}
