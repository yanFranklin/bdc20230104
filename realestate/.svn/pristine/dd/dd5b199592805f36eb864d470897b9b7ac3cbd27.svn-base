package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcDlrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJtcyDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/6/25
 * @description 申请人类模型
 */
@ApiModel(value = "BdcSlSqrDTO", description = "申请人类模型")
public class BdcSlSqrDTO {

    @ApiModelProperty(value = "不动产受理申请人信息")
    private BdcSlSqrDO bdcSlSqrDO;
    @ApiModelProperty(value = "不动产受理家庭成员集合")
    private List<BdcSlJtcyDO> bdcSlJtcyDOList;
    @ApiModelProperty(value = "代理人集合")
    private List<BdcDlrDO> bdcDlrDOList;

    public BdcSlSqrDO getBdcSlSqrDO() {
        return bdcSlSqrDO;
    }

    public void setBdcSlSqrDO(BdcSlSqrDO bdcSlSqrDO) {
        this.bdcSlSqrDO = bdcSlSqrDO;
    }

    public List<BdcSlJtcyDO> getBdcSlJtcyDOList() {
        return bdcSlJtcyDOList;
    }

    public void setBdcSlJtcyDOList(List<BdcSlJtcyDO> bdcSlJtcyDOList) {
        this.bdcSlJtcyDOList = bdcSlJtcyDOList;
    }

    public List<BdcDlrDO> getBdcDlrDOList() {
        return bdcDlrDOList;
    }

    public void setBdcDlrDOList(List<BdcDlrDO> bdcDlrDOList) {
        this.bdcDlrDOList = bdcDlrDOList;
    }

    @Override
    public String toString() {
        return "BdcSlSqrDTO{" +
                "bdcSlSqrDO=" + bdcSlSqrDO +
                ", bdcSlJtcyDOList=" + bdcSlJtcyDOList +
                ", bdcDlrDOList=" + bdcDlrDOList +
                '}';
    }
}
