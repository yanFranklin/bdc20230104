package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 义务人分组DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-08-02 09:12
 **/
public class BdcYwrGroupDTO {
    @ApiModelProperty(value = "不动产权利人")
    private BdcQlrDO bdcQlrDO;

    @ApiModelProperty(value = "xmid集合")
    private String xmids;

    @ApiModelProperty(value = "权利人id集合")
    private String qlrids;


    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public String getXmids() {
        return xmids;
    }

    public void setXmids(String xmids) {
        this.xmids = xmids;
    }

    public String getQlrids() {
        return qlrids;
    }

    public void setQlrids(String qlrids) {
        this.qlrids = qlrids;
    }

    @Override
    public String toString() {
        return "BdcYwrGroupDTO{" +
                "bdcQlrDO=" + bdcQlrDO +
                ", xmids=" + xmids +
                ", qlrids=" + qlrids +
                '}';
    }
}
