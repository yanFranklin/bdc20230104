package cn.gtmap.realestate.common.core.qo.accept;

import cn.gtmap.realestate.common.core.domain.BdcQl;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.dto.accept.BdcSlYwxxDTO;
import cn.gtmap.realestate.common.util.UUIDGenerator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/15
 * @description 组织受理页面权利信息查询QO
 */
@ApiModel(value = "InitBdcSlQlxxymQO", description = "组织受理页面权利信息查询QO")
public class InitBdcSlQlxxymQO {

    @ApiModelProperty(value = "不动产受理权利页面查询QO对象")
    private BdcSlQlxxymQO bdcSlQlxxymQO;

    @ApiModelProperty(value = "存储当前tab页的项目集合")
    private List<BdcXmDO> bdcXmDOList;

    @ApiModelProperty(value = "不动产权利")
    private BdcQl bdcQl;

    @ApiModelProperty(value = "字典集合")
    private Map<String, List<Map>> zdMap;

    public BdcSlQlxxymQO getBdcSlQlxxymQO() {
        return bdcSlQlxxymQO;
    }

    public void setBdcSlQlxxymQO(BdcSlQlxxymQO bdcSlQlxxymQO) {
        this.bdcSlQlxxymQO = bdcSlQlxxymQO;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public Map<String, List<Map>> getZdMap() {
        return zdMap;
    }

    public void setZdMap(Map<String, List<Map>> zdMap) {
        this.zdMap = zdMap;
    }

    public BdcQl getBdcQl() {
        return bdcQl;
    }

    public void setBdcQl(BdcQl bdcQl) {
        this.bdcQl = bdcQl;
    }
}
