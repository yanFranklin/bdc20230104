package cn.gtmap.realestate.common.core.dto.init;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:lixin1@gtmap.cn">lixin</a>
 * @version v1.0, 2019/7/31 18:55
 */
@ApiModel(value = "BdcQlrIdsDTO", description = "不动产权利人根据 ID 批量更新DTO")
public class BdcQlrIdsDTO implements Serializable {
    @ApiModelProperty(value = "不动产权利人")
    private BdcQlrDO bdcQlrDO;

    @ApiModelProperty(value = "项目 id 集合")
    private List<String> xmidlist;

    @ApiModelProperty(value = "不动产权利人 id 集合")
    private List<String> qlridlist;

    @Override
    public String toString() {
        return "BdcQlrIdsDTO{" +
                "bdcQlrDO=" + bdcQlrDO +
                ", xmidlist=" + xmidlist +
                ", qlridlist=" + qlridlist +
                '}';
    }

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public List<String> getXmidlist() {
        return xmidlist;
    }

    public void setXmidlist(List<String> xmidlist) {
        this.xmidlist = xmidlist;
    }

    public List<String> getQlridlist() {
        return qlridlist;
    }

    public void setQlridlist(List<String> qlridlist) {
        this.qlridlist = qlridlist;
    }
}
