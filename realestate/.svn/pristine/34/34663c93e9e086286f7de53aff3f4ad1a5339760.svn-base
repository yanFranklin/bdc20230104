package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/6/16
 * @description 已选不动产单元按幢分组
 */
@ApiModel(value = "YxBdcdyAzfzDTO", description = "已选不动产单元按幢分组")
public class YxBdcdyAzfzDTO implements Serializable {
    private static final long serialVersionUID = -4947324178788939065L;
    @ApiModelProperty(value = "幢的坐落")
    private String zl;
    @ApiModelProperty(value = "不动产受理项目前台")
    private List<BdcYxYwxxDTO> bdcYxYwxxDTOList;

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public List<BdcYxYwxxDTO> getBdcYxYwxxDTOList() {
        return bdcYxYwxxDTOList;
    }

    public void setBdcYxYwxxDTOList(List<BdcYxYwxxDTO> bdcYxYwxxDTOList) {
        this.bdcYxYwxxDTOList = bdcYxYwxxDTOList;
    }

    @Override
    public String toString() {
        return "YxBdcdyAzfzDTO{" +
                "zl='" + zl + '\'' +
                ", bdcYxYwxxDTOList=" + bdcYxYwxxDTOList +
                '}';
    }
}
