package cn.gtmap.realestate.exchange.core.domain;

import javax.persistence.Table;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-11-26
 * @description 默认值对照表
 */
@Table(name = "bdc_exchange_default_value")
public class BdcExchangeDefaultValueDO {
    /**
     * 表名
     */
    private String tablename;
    /**
     * 工作流定义ID (可为空)
     */
    private String gzldyid;
    /**
     * 登记细类 (可为空，不为空时流程id必须不为空)
     */
    private String djxl;
    /**
     * 字段名称
     */
    private String fieldname;
    /**
     * 默认值
     */
    private String defaultvalue;


    public String getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(String gzldyid) {
        this.gzldyid = gzldyid;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    public String getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }
}
