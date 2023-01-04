package cn.gtmap.realestate.common.core.dto.building;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.Link;

import java.util.*;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-15
 * @description 资源DTO 父类
 */
@ApiModel(value = "ResourceDTO", description = "页面资源")
public class ResourceDTO {

    /**
     * 字段
     */
    @ApiModelProperty(value = "字段")
    private LinkedHashMap<String,Object> info;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Map<String,Object> status;

    /**
     * 链接
     */
    @ApiModelProperty(value = "链接")
    private List<Link> links;

    /**
     * 嵌套资源列表
     */
    private List<ResourceDTO> resourceDTOList;

    public ResourceDTO(){
        this.info = new LinkedHashMap<>();
        this.status = new HashMap<>();
        this.links = new LinkedList<>();
        this.resourceDTOList = new LinkedList<>();
    }

    public LinkedHashMap<String, Object> getInfo() {
        return info;
    }

    public void setInfo(LinkedHashMap<String, Object> info) {
        this.info = info;
    }

    public Map<String, Object> getStatus() {
        return status;
    }

    public void setStatus(Map<String, Object> status) {
        this.status = status;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResourceDTO{");
        sb.append("info=").append(info);
        sb.append(", status=").append(status);
        sb.append(", links=").append(links);
        sb.append('}');
        return sb.toString();
    }

    public List<ResourceDTO> getResourceDTOList() {
        return resourceDTOList;
    }

    public void setResourceDTOList(List<ResourceDTO> resourceDTOList) {
        this.resourceDTOList = resourceDTOList;
    }
}
