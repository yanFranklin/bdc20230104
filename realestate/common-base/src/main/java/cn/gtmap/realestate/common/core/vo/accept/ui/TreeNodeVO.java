package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2019/12/17.
 * @description 不动产树结构VO
 */
@ApiModel(value = "TreeNodeVO", description = "树结构VO")
public class TreeNodeVO {

    @ApiModelProperty(value = "节点id")
    private String id;
    @ApiModelProperty(value = "节点名称")
    private String name;
    @ApiModelProperty(value = "子节点")
    private List<TreeNodeVO> children;
    @ApiModelProperty(value = "父节点id")
    private String pId;

    public TreeNodeVO(){}

    public TreeNodeVO(String id, String name){
        this.id = id;
        this.name = name;
    }

    public TreeNodeVO(String id, String name,String pId){
        this.id = id;
        this.name = name;
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNodeVO> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNodeVO> children) {
        this.children = children;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    @Override
    public String toString() {
        return "TreeNodeVO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", children=" + children +
                ", pId='" + pId + '\'' +
                '}';
    }
}
