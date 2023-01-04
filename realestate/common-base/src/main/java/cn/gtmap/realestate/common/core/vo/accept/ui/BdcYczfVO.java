package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSftjDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlSqrSfxxDTO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSqrSftjxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/10/11
 * @description 不动产一次支付VO
 */
@ApiModel(value = "BdcYczfVO", description = "不动产一次支付VO")
public class BdcYczfVO {

    @ApiModelProperty(value = "权利人税费统缴信息")
    private BdcSqrSftjxxDTO bdcQlrSftjxxDTO;
    @ApiModelProperty(value = "义务人税费统缴信息")
    private BdcSqrSftjxxDTO bdcYwrSftjxxDTO;

    public BdcSqrSftjxxDTO getBdcQlrSftjxxDTO() {
        return bdcQlrSftjxxDTO;
    }

    public void setBdcQlrSftjxxDTO(BdcSqrSftjxxDTO bdcQlrSftjxxDTO) {
        this.bdcQlrSftjxxDTO = bdcQlrSftjxxDTO;
    }

    public BdcSqrSftjxxDTO getBdcYwrSftjxxDTO() {
        return bdcYwrSftjxxDTO;
    }

    public void setBdcYwrSftjxxDTO(BdcSqrSftjxxDTO bdcYwrSftjxxDTO) {
        this.bdcYwrSftjxxDTO = bdcYwrSftjxxDTO;
    }

    @Override
    public String toString() {
        return "BdcYczfVO{" +
                "bdcQlrSftjxxDTO=" + bdcQlrSftjxxDTO +
                ", bdcYwrSftjxxDTO=" + bdcYwrSftjxxDTO +
                '}';
    }
}
