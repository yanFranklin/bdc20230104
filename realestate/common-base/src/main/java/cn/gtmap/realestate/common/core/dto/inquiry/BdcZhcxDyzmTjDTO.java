package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/11/29
 * @description  不动产打印证明统计展示结果DTO
 */
@ApiModel(value = "BdcZhcxDyzmTjDTO", description = "不动产打印证明统计展示结果DTO")
public class BdcZhcxDyzmTjDTO {
    @ApiModelProperty(value = "主键值集合")
    private LinkedHashSet<String> keySet;

    @ApiModelProperty(value = "统计结果数据")
    private Map<String, List<Integer>> valueMap;


    public Set<String> getKeySet() {
        return keySet;
    }

    public void setKeySet(LinkedHashSet<String> keySet) {
        this.keySet = keySet;
    }

    public Map<String, List<Integer>> getValueMap() {
        return valueMap;
    }

    public void setValueMap(Map<String, List<Integer>> valueMap) {
        this.valueMap = valueMap;
    }
}
