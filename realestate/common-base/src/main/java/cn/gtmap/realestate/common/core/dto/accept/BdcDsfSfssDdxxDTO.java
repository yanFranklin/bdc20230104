package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/29
 * @description 接收第三方收费收税订单信息DTO
 */
@ApiModel(value = "BdcDsfSfssDdxxDTO", description = "接收第三方收费收税订单信息DTO")
public class BdcDsfSfssDdxxDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "总额")
    private Double ze;

    @ApiModelProperty(value = "订单信息")
    private List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public Double getZe() {
        return ze;
    }

    public void setZe(Double ze) {
        this.ze = ze;
    }

    public List<BdcSlSfssDdxxDO> getBdcSlSfssDdxxDOList() {
        return bdcSlSfssDdxxDOList;
    }

    public void setBdcSlSfssDdxxDOList(List<BdcSlSfssDdxxDO> bdcSlSfssDdxxDOList) {
        this.bdcSlSfssDdxxDOList = bdcSlSfssDdxxDOList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcDsfSfssDdxxDTO{");
        sb.append("slbh='").append(slbh).append('\'');
        sb.append(", ze=").append(ze);
        sb.append(", bdcSlSfssDdxxDOList=").append(bdcSlSfssDdxxDOList);
        sb.append('}');
        return sb.toString();
    }
}
