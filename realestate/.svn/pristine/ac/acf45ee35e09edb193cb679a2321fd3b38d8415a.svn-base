package cn.gtmap.realestate.common.core.dto.exchange.openapi;

import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkCsDO;
import cn.gtmap.realestate.common.core.domain.exchange.BdcDwJkDO;
import cn.gtmap.realestate.common.core.enums.OpenApiParamConstansEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.com">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/9/29 10:43
 */
public class BdcOpenApiParamDTO {

    @ApiModelProperty(value = "主键,参数id")
    private String id;

    @ApiModelProperty(value = "参数父id")
    private String parentId;

    @ApiModelProperty(value = "参数名")
    private String fieldName;

    @ApiModelProperty(value = "父参数名")
    private String parentFieldName;

    @ApiModelProperty(value = "字段类型")
    private String fieldType;

    @ApiModelProperty(value = "参数说明")
    private String fieldRemark;

    @ApiModelProperty(value = "参数类型")
    private Integer paramType;

    @ApiModelProperty(value = "默认值")
    private String defaultValue;

    @ApiModelProperty(value = "层级")
    private String level;

    @ApiModelProperty(value = "是否必填 0是 1否")
    private Integer required;

    @ApiModelProperty(value = "创建者")
    private String createdBy;

    @ApiModelProperty(value = "修改者")
    private String updatedBy;

    private List<BdcOpenApiParamDTO> childParamInfo;

    private String childParamId;

    private String sql;

    private String isListFlag;

    @ApiModelProperty(value = "参数字典名称")
    private String cszdmc;

    @ApiModelProperty(value = "参数拼接配置")
    private String cspj;

    public String getCspj() {
        return cspj;
    }

    public void setCspj(String cspj) {
        this.cspj = cspj;
    }

    public String getCszdmc() {
        return cszdmc;
    }

    public void setCszdmc(String cszdmc) {
        this.cszdmc = cszdmc;
    }

    public String getChildParamId() {
        return childParamId;
    }

    public void setChildParamId(String childParamId) {
        this.childParamId = childParamId;
    }

    public List<BdcOpenApiParamDTO> getChildParamInfo() {
        return childParamInfo;
    }

    public void setChildParamInfo(List<BdcOpenApiParamDTO> childParamInfo) {
        this.childParamInfo = childParamInfo;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getIsListFlag() {
        return isListFlag;
    }

    public void setIsListFlag(String isListFlag) {
        this.isListFlag = isListFlag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getParentFieldName() {
        return parentFieldName;
    }

    public void setParentFieldName(String parentFieldName) {
        this.parentFieldName = parentFieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String fieldRemark) {
        this.fieldRemark = fieldRemark;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getRequired() {
        return required;
    }

    public void setRequired(Integer required) {
        this.required = required;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void convertDO(BdcDwJkCsDO bdcDwJkCsDO){
        if (bdcDwJkCsDO != null){
            this.setId(bdcDwJkCsDO.getCsid());
            this.setParentId(bdcDwJkCsDO.getCsfid());
            this.setFieldName(bdcDwJkCsDO.getCsm());
            this.setParentFieldName(parentFieldName);
            this.setFieldType(bdcDwJkCsDO.getCszdlx());
            this.setFieldRemark(bdcDwJkCsDO.getCssm());
            this.setParamType(bdcDwJkCsDO.getCslx());
            this.setDefaultValue(bdcDwJkCsDO.getCsmrz());
            this.setLevel(bdcDwJkCsDO.getCscj());
            this.setRequired(bdcDwJkCsDO.getSfbt());
            this.setCszdmc(bdcDwJkCsDO.getCszdmc());
            this.setCspj(bdcDwJkCsDO.getCspj());
            if (StringUtils.isNotBlank(bdcDwJkCsDO.getJkcsext())){
                JSONObject jsonObject = JSON.parseObject(bdcDwJkCsDO.getJkcsext());
                this.setChildParamId(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_CURRENT_JKID.getKey()));
                this.setSql(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_SQL.getKey()));
                if (StringUtils.isNotBlank(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey())) && OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getValue().equals(jsonObject.getString(OpenApiParamConstansEnum.INTERFACE_PARAM_EXT_IS_LIST_YES.getKey()))){
                    this.setIsListFlag("on");
                }
            }
        }
    }

}
