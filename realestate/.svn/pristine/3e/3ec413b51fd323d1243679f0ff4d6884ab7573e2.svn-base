package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.ZdzhDjxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019-3-6
 * @description 查询地籍号状态返回的DTO
 */
@ApiModel(value = "DjhZtResponseDTO", description = "查询地籍号状态返回的DTO")
public class DjhZtResponseDTO {
    /**
     * 冻结信息集合
     */
    @ApiModelProperty(value = "冻结信息集合")
    private List<ZdzhDjxxDO> zdzhDjxxDOList;
    /**
     * 地籍号
     */
    @ApiModelProperty(value = "地籍号")
    private String djh;

    public List<ZdzhDjxxDO> getZdzhDjxxDOList() {
        return zdzhDjxxDOList;
    }

    public void setZdzhDjxxDOList(List<ZdzhDjxxDO> zdzhDjxxDOList) {
        this.zdzhDjxxDOList = zdzhDjxxDOList;
    }

    public String getDjh() {
        return djh;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DjhZtResponseDTO{");
        sb.append("zdzhDjxxDOList=").append(zdzhDjxxDOList);
        sb.append(", djh='").append(djh).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
