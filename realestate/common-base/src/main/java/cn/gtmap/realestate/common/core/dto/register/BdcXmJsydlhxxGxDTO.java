package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/1/8
 * @description 不动产登记项目与建设用地量化信息关系DTO
 */
@ApiModel(value = "BdcXmJsydlhxxGxDTO", description = "不动产登记项目与建设用地量化信息关系DTO")
public class BdcXmJsydlhxxGxDTO {

    @ApiModelProperty(value = "建设用地自然幢信息主键集合")
    private List<String> jsydzrzxxidList;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "地籍号")
    private String djh;

    public List<String> getJsydzrzxxidList() {
        return jsydzrzxxidList;
    }

    public void setJsydzrzxxidList(List<String> jsydzrzxxidList) {
        this.jsydzrzxxidList = jsydzrzxxidList;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcXmJsydlhxxGxDTO.class.getSimpleName() + "[", "]")
                .add("jsydzrzxxidList=" + jsydzrzxxidList)
                .add("gzlslid='" + gzlslid + "'")
                .add("djh='" + djh + "'")
                .toString();
    }
}
