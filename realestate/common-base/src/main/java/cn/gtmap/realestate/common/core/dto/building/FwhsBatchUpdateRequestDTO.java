package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-12-19
 * @description 批量修改 DTO
 */
@ApiModel(value = "FwhsBatchUpdateRequestDTO", description = "户室批量修改DTO")
public class FwhsBatchUpdateRequestDTO {

    /**
     * 主键列表
     */
    @ApiModelProperty(value = "主键列表")
    private List<String> fwHsIndexList;

    @ApiModelProperty(value = "是否只更新空值")
    private String updateNullOnly;

    @ApiModelProperty(value = "参数列表")
    private Map paramMap;

    /**
     * 替换操作
     */
    @ApiModelProperty(value = "参数列表")
    private String replace;

    /**
     * 替换属性
     */
    @ApiModelProperty(value = "替换属性")
    private String replaceColumn;

    /**
     * 替换值
     */
    @ApiModelProperty(value = "替换值")
    private String replaceThz;

    /**
     * 目标值
     */
    @ApiModelProperty(value = "目标值")
    private String replaceMbz;

    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;

    public String getReplace() {
        return replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

    public String getReplaceColumn() {
        return replaceColumn;
    }

    public void setReplaceColumn(String replaceColumn) {
        this.replaceColumn = replaceColumn;
    }

    public String getReplaceThz() {
        return replaceThz;
    }

    public void setReplaceThz(String replaceThz) {
        this.replaceThz = replaceThz;
    }

    public String getReplaceMbz() {
        return replaceMbz;
    }

    public void setReplaceMbz(String replaceMbz) {
        this.replaceMbz = replaceMbz;
    }

    public List<String> getFwHsIndexList() {
        return fwHsIndexList;
    }

    public void setFwHsIndexList(List<String> fwHsIndexList) {
        this.fwHsIndexList = fwHsIndexList;
    }

    public String getUpdateNullOnly() {
        return updateNullOnly;
    }

    public void setUpdateNullOnly(String updateNullOnly) {
        this.updateNullOnly = updateNullOnly;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwhsBatchUpdateRequestDTO{");
        sb.append("fwHsIndexList=").append(fwHsIndexList);
        sb.append(", updateNullOnly='").append(updateNullOnly).append('\'');
        sb.append(", paramMap=").append(paramMap);
        sb.append('}');
        return sb.toString();
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }
}
