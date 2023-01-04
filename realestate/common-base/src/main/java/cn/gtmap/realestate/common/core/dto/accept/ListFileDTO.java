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
public class ListFileDTO implements Serializable {


    @ApiModelProperty(value = "附件")
    private List<ProcessListFileDTO> processList;

    @ApiModelProperty(value = "其他属性")
    private Map<String,String> attributes;

    public List<ProcessListFileDTO> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessListFileDTO> processList) {
        this.processList = processList;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
