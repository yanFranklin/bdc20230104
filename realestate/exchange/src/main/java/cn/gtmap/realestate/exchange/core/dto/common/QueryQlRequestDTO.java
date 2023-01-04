package cn.gtmap.realestate.exchange.core.dto.common;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-08-16
 * @description 查询权利参数实体
 */
public class QueryQlRequestDTO {

    private String xmid;

    private String bdcdyh;

    private Boolean withXm;

    private Boolean withQlr;

    private String qlrlb;

    private Class qllxClass;

    // 空为 查询 现势    不为空 多个用逗号隔开
    private String qszt;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public Boolean getWithXm() {
        return withXm;
    }

    public void setWithXm(Boolean withXm) {
        this.withXm = withXm;
    }

    public Boolean getWithQlr() {
        return withQlr;
    }

    public void setWithQlr(Boolean withQlr) {
        this.withQlr = withQlr;
    }

    public String getQlrlb() {
        return qlrlb;
    }

    public void setQlrlb(String qlrlb) {
        this.qlrlb = qlrlb;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Class getQllxClass() {
        return qllxClass;
    }

    public void setQllxClass(Class qllxClass) {
        this.qllxClass = qllxClass;
    }

    public String getQszt() {
        return qszt;
    }

    public void setQszt(String qszt) {
        this.qszt = qszt;
    }
}
