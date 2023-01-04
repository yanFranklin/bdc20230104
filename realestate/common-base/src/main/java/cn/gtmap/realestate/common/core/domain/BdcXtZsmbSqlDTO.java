package cn.gtmap.realestate.common.core.domain;

/**
 * 不动产系统证书模板验证SQL相关属性
 * @author <a href="mailto:hongqin@gtmap.cn">hongqin</a>
 * @version 1.0  2022/6/29.
 * @description
 */
public class BdcXtZsmbSqlDTO {
    /**
     * 模板SQL语句 参数xmid 可配置多个语句用;隔开
     */
    private String sql;
    /**
     * 模板SQL语句 的参数名称 多个参数用,隔开
     */
    private String csmc;
    /**
     * 模板SQL语句 的参数值与参数名称一一对应
     */
    private String csz;

    public BdcXtZsmbSqlDTO() {
    }

    public BdcXtZsmbSqlDTO(String sql, String csmc, String csz) {
        this.sql = sql;
        this.csmc = csmc;
        this.csz = csz;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getCsmc() {
        return csmc;
    }

    public void setCsmc(String csmc) {
        this.csmc = csmc;
    }

    public String getCsz() {
        return csz;
    }

    public void setCsz(String csz) {
        this.csz = csz;
    }

    @Override
    public String toString() {
        return "BdcMbsqlcsDTO{" +
                "sql='" + sql + '\'' +
                ", csmc='" + csmc + '\'' +
                ", csz='" + csz + '\'' +
                '}';
    }
}
