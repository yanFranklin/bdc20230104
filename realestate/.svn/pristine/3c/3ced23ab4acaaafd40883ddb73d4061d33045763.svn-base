package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0  2019-05-22
 * @description 打印日志保存dto
 */
@Api(value = "BdcPrintLogDTO", description = "打印日志保存dto")
public class BdcPrintLogDTO {

    @ApiModelProperty("模板url")
    private String modelUrl;

    @ApiModelProperty("打印数据源请求")
    private String dataUrl;

    @ApiModelProperty("打印数据源xml")
    private String xmlStr;

    @ApiModelProperty(value = "私有属性")
    private Map<String,Object> privateAttrMap;

    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getXmlStr() {
        return xmlStr;
    }

    public void setXmlStr(String xmlStr) {
        this.xmlStr = xmlStr;
    }

    public Map<String, Object> getPrivateAttrMap() {
        return privateAttrMap;
    }

    public void setPrivateAttrMap(Map<String, Object> privateAttrMap) {
        this.privateAttrMap = privateAttrMap;
    }
}
