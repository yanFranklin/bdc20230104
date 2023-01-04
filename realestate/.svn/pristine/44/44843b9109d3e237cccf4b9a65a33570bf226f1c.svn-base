package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.BdcZjMxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/03/31
 * @description  质检信息DTO
 */
public class BdcZjxxDTO extends BdcZjDO {

    @ApiModelProperty(value = "质检明细信息集合")
    private List<BdcZjMxDO> zjMxDOList;

    @ApiModelProperty(value = "签名图片地址")
    private String qmtpdz;

    public List<BdcZjMxDO> getZjMxDOList() {
        return zjMxDOList;
    }

    public void setZjMxDOList(List<BdcZjMxDO> zjMxDOList) {
        this.zjMxDOList = zjMxDOList;
    }

    public String getQmtpdz() {
        return qmtpdz;
    }

    public void setQmtpdz(String qmtpdz) {
        this.qmtpdz = qmtpdz;
    }
}
