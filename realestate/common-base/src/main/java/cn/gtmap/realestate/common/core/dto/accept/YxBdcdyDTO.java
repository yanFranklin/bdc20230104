package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcYxbdcdyKgPzDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/12/15
 * @description 已选不动产单元
 */
@ApiModel(value = "YxBdcdyDTO", description = "已选不动产单元")
public class YxBdcdyDTO implements Serializable {
    private static final long serialVersionUID = -4947324178788939065L;
    @ApiModelProperty(value = "已选不动产单元开关")
    private YxBdcdyKgDTO bdcdyKgDTO;
    @ApiModelProperty(value = "不动产受理项目前台")
    private List<BdcYxYwxxDTO> bdcYxYwxxDTOList;
    @ApiModelProperty(value = "已选不动产单元按幢分组")
    private List<YxBdcdyAzfzDTO> yxBdcdyAzfzDTOList;
    @ApiModelProperty(value = "分页总数")
    private Long count;
    @ApiModelProperty(value = "分页当前页数")
    private int curr;
    @ApiModelProperty(value = "登记小类")
    private String djxl;

    public YxBdcdyKgDTO getBdcdyKgDTO() {
        return bdcdyKgDTO;
    }

    public void setBdcdyKgDTO(YxBdcdyKgDTO bdcdyKgDTO) {
        this.bdcdyKgDTO = bdcdyKgDTO;
    }

    public List<BdcYxYwxxDTO> getBdcYxYwxxDTOList() {
        return bdcYxYwxxDTOList;
    }

    public void setBdcYxYwxxDTOList(List<BdcYxYwxxDTO> bdcYxYwxxDTOList) {
        this.bdcYxYwxxDTOList = bdcYxYwxxDTOList;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public int getCurr() {
        return curr;
    }

    public void setCurr(int curr) {
        this.curr = curr;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public List<YxBdcdyAzfzDTO> getYxBdcdyAzfzDTOList() {
        return yxBdcdyAzfzDTOList;
    }

    public void setYxBdcdyAzfzDTOList(List<YxBdcdyAzfzDTO> yxBdcdyAzfzDTOList) {
        this.yxBdcdyAzfzDTOList = yxBdcdyAzfzDTOList;
    }

    @Override
    public String toString() {
        return "YxBdcdyDTO{" +
                "bdcdyKgDTO=" + bdcdyKgDTO +
                ", bdcYxYwxxDTOList=" + bdcYxYwxxDTOList +
                ", yxBdcdyAzfzDTOList=" + yxBdcdyAzfzDTOList +
                ", count=" + count +
                ", curr=" + curr +
                ", djxl='" + djxl + '\'' +
                '}';
    }
}
