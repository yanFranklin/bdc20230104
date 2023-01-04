package cn.gtmap.realestate.exchange.core.dto;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0  2018/11/2
 * @description map转换字典用到实体 把map中的字典项做对应转换时用到的实体
 */
public class BdcMapZdConvertDTO {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 对应字典表名
     */
    private String table;
    /**
     * 对应字典表中代码字段名 eg. DM
     */
    private String dm;
    /**
     * 对应字典表中名称字段名 eg. MC
     */
    private String mc;
    /**
     * 对应字典表类
     */
    private Class tableClass;

    public BdcMapZdConvertDTO(String name, String table, String dm, String mc, Class tableClass) {
        this.name = name;
        this.table = table;
        this.dm = dm;
        this.mc = mc;
        this.tableClass = tableClass;
    }

    public BdcMapZdConvertDTO(String name, String table) {
        this.name = name;
        this.table = table;
        this.dm = "DM";
        this.mc = "MC";
        this.tableClass = null;
    }

    public BdcMapZdConvertDTO(String name, String table, Class tableClass) {
        this.name = name;
        this.table = table;
        this.dm = "DM";
        this.mc = "MC";
        this.tableClass = tableClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Class getTableClass() {
        return tableClass;
    }

    public void setTableClass(Class tableClass) {
        this.tableClass = tableClass;
    }
}
