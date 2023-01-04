package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 权利人分组DTO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2019-07-30 20:35
 **/
@ApiModel(value = "BdcQlrGroupDTO", description = "不动产权利人分组DTO")
public class BdcQlrGroupDTO implements Serializable {
    private static final long serialVersionUID = -8440030547296361158L;
    @ApiModelProperty(value = "不动产权利人")
    private BdcQlrDO bdcQlrDO;
    @ApiModelProperty(value = "权利人id集合")
    private List<String> otherBdcQlridList;

    @ApiModelProperty(value = "项目id集合")
    private List<String> otherXmidList;

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public List<String> getOtherBdcQlridList() {
        return otherBdcQlridList;
    }

    public void setOtherBdcQlridList(List<String> otherBdcQlridList) {
        this.otherBdcQlridList = otherBdcQlridList;
    }

    public List<String> getOtherXmidList() {
        return otherXmidList;
    }

    public void setOtherXmidList(List<String> otherXmidList) {
        this.otherXmidList = otherXmidList;
    }

    @Override
    public String toString() {
        return "BdcQlrGroupDTO{" +
                "bdcQlrDO=" + bdcQlrDO +
                ", otherBdcQlridList=" + otherBdcQlridList +
                ", otherXmidList=" + otherXmidList +
                '}';
    }
}
