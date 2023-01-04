package cn.gtmap.realestate.etl.core.dto.ggxypt;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/8/31
 * @description 字段说明
 */
public class ColumnDTO {


    /**
     * columnname : QYMC
     * dataitemname : 权利人名称
     * datatype : 字符型
     */

    private String columnname;
    private String dataitemname;
    private String datatype;

    public String getColumnname() {
        return columnname;
    }

    public void setColumnname(String columnname) {
        this.columnname = columnname;
    }

    public String getDataitemname() {
        return dataitemname;
    }

    public void setDataitemname(String dataitemname) {
        this.dataitemname = dataitemname;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Override
    public String toString() {
        return "ColumnDTO{" +
                "columnname='" + columnname + '\'' +
                ", dataitemname='" + dataitemname + '\'' +
                ", datatype='" + datatype + '\'' +
                '}';
    }
}
