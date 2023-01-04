package cn.gtmap.realestate.common.core.dto.init;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:lisongtao@gtmap.cn">lisongtao</a>
 * @version 1.0, 2019/12/18
 * @description 权籍同步对照信息
 */
@ApiModel(value = "BdcQjtbxxDTO",description = "不动产权籍同步对照信息")
public class BdcQjtbxxDTO {

    @ApiModelProperty(value = "表名")
    private String table;

    @ApiModelProperty(value = "表名描述")
    private String tableDesc;

    @ApiModelProperty(value = "字段名")
    private String field;

    @ApiModelProperty(value = "字段描述")
    private String fieldDesc;

    @ApiModelProperty(value = "原值")
    private String sourceVal;

    @ApiModelProperty(value = "转换后的值")
    private String targetVal;

    @ApiModelProperty(value = "是否相等")
    private Integer sfxd;

    public Integer getSfxd() {
        return sfxd;
    }

    public void setSfxd(Integer sfxd) {
        this.sfxd = sfxd;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public void setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldDesc() {
        return fieldDesc;
    }

    public void setFieldDesc(String fieldDesc) {
        this.fieldDesc = fieldDesc;
    }

    public String getSourceVal() {
        return sourceVal;
    }

    public void setSourceVal(String sourceVal) {
        this.sourceVal = sourceVal;
    }

    public String getTargetVal() {
        return targetVal;
    }

    public void setTargetVal(String targetVal) {
        this.targetVal = targetVal;
    }

    @Override
    public String toString() {
        return "BdcQjtbxxDTO{" +
                "table='" + table + '\'' +
                ", tableDesc='" + tableDesc + '\'' +
                ", field='" + field + '\'' +
                ", fieldDesc='" + fieldDesc + '\'' +
                ", sourceVal='" + sourceVal + '\'' +
                ", targetVal='" + targetVal + '\'' +
                ", sfxd=" + sfxd +
                '}';
    }
}
