package cn.gtmap.realestate.common.core.dto.certificate;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcYjdDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/3/1
 * @description 移交单信息结果DTO, 项目信息为附属信息
 */
@ApiModel(value = "BdcYjdDTO", description = "不动产移交单信息DTO对象")
public class BdcYjdDTO extends BdcYjdDO {
    @ApiModelProperty(value = "移交单项目信息")
    List<BdcXmDO> bdcXmDOList;

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    @Override
    public String toString() {
        return "BdcYjdDTO{" +
                "bdcXmDOList=" + bdcXmDOList +
                '}';
    }
}
