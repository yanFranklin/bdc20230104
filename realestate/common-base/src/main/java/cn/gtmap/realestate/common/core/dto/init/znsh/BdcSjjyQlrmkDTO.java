package cn.gtmap.realestate.common.core.dto.init.znsh;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/5
 * @description 数据校验权利人信息
 */
public class BdcSjjyQlrmkDTO {

    @ApiModelProperty(value = "权利人信息列表")
    private List<BdcQlrDO> bdcQlrList;

    @ApiModelProperty(value = "权利人比对信息列表")
    private List<BdcSjjyQlrbdDTO> qlrbdList;

    public List<BdcQlrDO> getBdcQlrList() {
        return bdcQlrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
    }

    public List<BdcSjjyQlrbdDTO> getQlrbdList() {
        return qlrbdList;
    }

    public void setQlrbdList(List<BdcSjjyQlrbdDTO> qlrbdList) {
        this.qlrbdList = qlrbdList;
    }
}
