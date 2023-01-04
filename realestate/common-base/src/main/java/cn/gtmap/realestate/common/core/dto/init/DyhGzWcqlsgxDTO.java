package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcDysdDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.BdcXmLsgxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/10/18
 * @description 单元号更正无产权历史关系数据
 */
public class DyhGzWcqlsgxDTO {

    @ApiModelProperty(value = "限制权利项目信息")
    private List<BdcXmDO> xzqlXmList;

    @ApiModelProperty(value = "单元锁定信息")
    private List<BdcDysdDO> bdcDysdList;


    public List<BdcXmDO> getXzqlXmList() {
        return xzqlXmList;
    }

    public void setXzqlXmList(List<BdcXmDO> xzqlXmList) {
        this.xzqlXmList = xzqlXmList;
    }

    public List<BdcDysdDO> getBdcDysdList() {
        return bdcDysdList;
    }

    public void setBdcDysdList(List<BdcDysdDO> bdcDysdList) {
        this.bdcDysdList = bdcDysdList;
    }

}
