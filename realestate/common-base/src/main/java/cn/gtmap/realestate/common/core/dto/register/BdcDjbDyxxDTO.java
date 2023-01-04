package cn.gtmap.realestate.common.core.dto.register;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu/a>"
 * @version 3.0, 2022/3/14
 * @description 常州自助查询机查询登记簿结果-抵押权信息
 */
public class BdcDjbDyxxDTO {
    /**
     * 不动产证明号
     */
    private String bdczmh;

    /**
     * 抵押权人
     */
    private String dyqr;

    /**
     * 抵押期限
     */
    private String dyqx;

    /**
     * 担保范围
     */
    private String dbfw;

    /**
     * 被担保主债权金额/最高债权额(万元)
     */
    private String jg;

    /**
     * 是否存在禁止或限制转让抵押不动产的约定
     */
    private String sfjz;

    public String getBdczmh() {
        return bdczmh;
    }

    public void setBdczmh(String bdczmh) {
        this.bdczmh = bdczmh;
    }

    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    public String getDyqx() {
        return dyqx;
    }

    public void setDyqx(String dyqx) {
        this.dyqx = dyqx;
    }

    public String getDbfw() {
        return dbfw;
    }

    public void setDbfw(String dbfw) {
        this.dbfw = dbfw;
    }

    public String getJg() {
        return jg;
    }

    public void setJg(String jg) {
        this.jg = jg;
    }

    public String getSfjz() {
        return sfjz;
    }

    public void setSfjz(String sfjz) {
        this.sfjz = sfjz;
    }

    @Override
    public String toString() {
        return "BdcDjbDyxxDTO{" +
                "bdczmh='" + bdczmh + '\'' +
                ", dyqr='" + dyqr + '\'' +
                ", dyqx='" + dyqx + '\'' +
                ", dbfw='" + dbfw + '\'' +
                ", jg='" + jg + '\'' +
                ", sfjz='" + sfjz + '\'' +
                '}';
    }
}
