package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxjgDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxCxtjDO;
import cn.gtmap.realestate.common.core.domain.inquiry.DtcxDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
@Api(value = "BdcDtcxDTO", description = "动态查询传输对象")
public class BdcDtcxDTO extends DtcxDO {
    @ApiModelProperty("查询条件集")
    private List<DtcxCxtjDO> cxtjDOList;
    @ApiModelProperty("查询结果列")
    private List<DtcxCxjgDO> cxjgDOList;

    public List<DtcxCxtjDO> getCxtjDOList() {
        return cxtjDOList;
    }

    public void setCxtjDOList(List<DtcxCxtjDO> cxtjDOList) {
        this.cxtjDOList = cxtjDOList;
    }

    public List<DtcxCxjgDO> getCxjgDOList() {
        return cxjgDOList;
    }

    public void setCxjgDOList(List<DtcxCxjgDO> cxjgDOList) {
        this.cxjgDOList = cxjgDOList;
    }

    @Override
    public String toString() {
        return "BdcDtcxDTO{" +
                "cxtjDOList=" + cxtjDOList +
                ", cxjgDOList=" + cxjgDOList +
                '}';
    }
}