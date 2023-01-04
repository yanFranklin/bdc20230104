package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 供地审批信息文件dto
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-12-28 08:52
 **/
public class PropertiesFileDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "文件名称")
    private String text;

    @ApiModelProperty(value = "子节点")
    private List<ChildrenFileDTO> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ChildrenFileDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildrenFileDTO> children) {
        this.children = children;
    }
}
