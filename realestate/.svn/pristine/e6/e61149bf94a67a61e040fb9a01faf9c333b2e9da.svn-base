package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcZssdDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0  2020-09-02
 * @description 不动产证书锁定VO
 */
@ApiModel(value = "BdcSdVO", description = "不动产锁定VO")
public class BdcSdVO {

    @ApiModelProperty(value = "不动产证书锁定DO集合")
    private List<BdcZssdDO> zssdDOList;
    @ApiModelProperty(value = "不动产单元锁定DO集合")
    private List<BdcDysdDO> dysdDOList;
    @ApiModelProperty(value = "不动产项目DO")
    private BdcXmDO bdcXmDO;

    public List<BdcZssdDO> getZssdDOList() {
        return zssdDOList;
    }

    public void setZssdDOList(List<BdcZssdDO> zssdDOList) {
        this.zssdDOList = zssdDOList;
    }

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public List<BdcDysdDO> getDysdDOList() {
        return dysdDOList;
    }

    public void setDysdDOList(List<BdcDysdDO> dysdDOList) {
        this.dysdDOList = dysdDOList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcSdVO.class.getSimpleName() + "[", "]")
                .add("zssdDOList=" + zssdDOList)
                .add("dysdDOList=" + dysdDOList)
                .add("bdcXmDO=" + bdcXmDO)
                .toString();
    }
}
