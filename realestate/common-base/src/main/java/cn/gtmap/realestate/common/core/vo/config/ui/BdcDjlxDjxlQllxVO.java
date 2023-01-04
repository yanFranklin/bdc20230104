package cn.gtmap.realestate.common.core.vo.config.ui;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/6/24
 * @description 登记小类、登记类型、权利类型、登记原因
 */
public class BdcDjlxDjxlQllxVO {
    /**
     * 序号
     */
    private String xh;
    /**
     * 登记类型-登记小类 关系id
     */
    private String djlxDjxlGxId;
    /**
     *登记小类-权利类型关系 id
     */
    private String djxlQllxGxId;
    /**
     *登记小类-登记原因 关系id
     */
    private String djxlDjyyGxId;
    /**
     *登记小类代码
     */
    private String djxlDm;
    /**
     *登记小类名称
     */
    private String djxlMc;
    /**
     * 登记类型代码
     */
    private Integer djlxDm;
    /**
     *登记类型名称
     */
    private String djlxMc;
    /**
     *权利类型代码
     */
    private Integer qllxDm;
    /**
     *权利类型名称
     */
    private String qllxMc;
    /**
     *登记原因
     */
    private String djyy;
    /**
     *登记原因是否默认
     */
    private Integer djyySfmr;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getDjlxDjxlGxId() {
        return djlxDjxlGxId;
    }

    public void setDjlxDjxlGxId(String djlxDjxlGxId) {
        this.djlxDjxlGxId = djlxDjxlGxId;
    }

    public String getDjxlQllxGxId() {
        return djxlQllxGxId;
    }

    public void setDjxlQllxGxId(String djxlQllxGxId) {
        this.djxlQllxGxId = djxlQllxGxId;
    }

    public String getDjxlDjyyGxId() {
        return djxlDjyyGxId;
    }

    public void setDjxlDjyyGxId(String djxlDjyyGxId) {
        this.djxlDjyyGxId = djxlDjyyGxId;
    }

    public String getDjxlDm() {
        return djxlDm;
    }

    public void setDjxlDm(String djxlDm) {
        this.djxlDm = djxlDm;
    }

    public String getDjxlMc() {
        return djxlMc;
    }

    public void setDjxlMc(String djxlMc) {
        this.djxlMc = djxlMc;
    }

    public Integer getDjlxDm() {
        return djlxDm;
    }

    public void setDjlxDm(Integer djlxDm) {
        this.djlxDm = djlxDm;
    }

    public String getDjlxMc() {
        return djlxMc;
    }

    public void setDjlxMc(String djlxMc) {
        this.djlxMc = djlxMc;
    }

    public Integer getQllxDm() {
        return qllxDm;
    }

    public void setQllxDm(Integer qllxDm) {
        this.qllxDm = qllxDm;
    }

    public String getQllxMc() {
        return qllxMc;
    }

    public void setQllxMc(String qllxMc) {
        this.qllxMc = qllxMc;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Integer getDjyySfmr() {
        return djyySfmr;
    }

    public void setDjyySfmr(Integer djyySfmr) {
        this.djyySfmr = djyySfmr;
    }

    @Override
    public String toString() {
        return "BdcDjlxDjxlQllxVO{" +
                "xh='" + xh + '\'' +
                ", djlxDjxlGxId='" + djlxDjxlGxId + '\'' +
                ", djxlQllxGxId='" + djxlQllxGxId + '\'' +
                ", djxlDjyyGxId='" + djxlDjyyGxId + '\'' +
                ", djxlDm='" + djxlDm + '\'' +
                ", djxlMc='" + djxlMc + '\'' +
                ", djlxDm=" + djlxDm +
                ", djlxMc='" + djlxMc + '\'' +
                ", qllxDm=" + qllxDm +
                ", qllxMc='" + qllxMc + '\'' +
                ", djyy='" + djyy + '\'' +
                ", djyySfmr=" + djyySfmr +
                '}';
    }
}
