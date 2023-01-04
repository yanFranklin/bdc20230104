package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @program: realestate
 * @description: 供地审批信息文件dto
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-12-28 08:52
 **/
public class ProcessListFileDTO implements Serializable {

    @ApiModelProperty(value = "节点名")
    private String typeName;

    @ApiModelProperty(value = "codekey")
    private String codeKey;

    @ApiModelProperty(value = "codeValue")
    private String codeValue;

    @ApiModelProperty(value = "子节点")
    private List<PropertiesFileDTO> properties;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public List<PropertiesFileDTO> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesFileDTO> properties) {
        this.properties = properties;
    }
}
