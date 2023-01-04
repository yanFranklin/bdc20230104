package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/5/15
 * @description 不动产受理项目信息VO
 */
@ApiModel(value = "BdcSlXmVO", description = "不动产受理项目信息VO")
public class BdcSlXmVO extends BdcSlXmDO {

    @ApiModelProperty(value = "原项目ID")
    private String yxmid;

    public String getYxmid() {
        return yxmid;
    }

    public void setYxmid(String yxmid) {
        this.yxmid = yxmid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BdcSlXmVO bdcSlXmVO = (BdcSlXmVO) o;
        return yxmid.equals(bdcSlXmVO.yxmid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yxmid);
    }
}
