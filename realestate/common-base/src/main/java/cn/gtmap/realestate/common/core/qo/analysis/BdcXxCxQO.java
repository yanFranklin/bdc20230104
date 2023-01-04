package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Api(value = "BdcXxCxQO", description = "接口请求参数")
public class BdcXxCxQO{

    @ApiModelProperty("接口请求头")
    private BdcHeadQO head;

    @ApiModelProperty("查询参数列表")
    private List<BdcParamQO> paramList;

    @ApiModelProperty("查询配置参数列表")
    private BdcConfigParamQO configParams;

    @ApiModelProperty("过滤参数列表")
    private BdcFilterParamQO filterParams;

    public BdcHeadQO getHead() {
        return head;
    }

    public void setHead(BdcHeadQO head) {
        this.head = head;
    }

    public List<BdcParamQO> getParamList() {
        return paramList;
    }

    public void setParamList(List<BdcParamQO> paramList) {
        this.paramList = paramList;
    }

    public BdcConfigParamQO getConfigParams() {
        return configParams;
    }

    public void setConfigParams(BdcConfigParamQO configParams) {
        this.configParams = configParams;
    }

    public BdcFilterParamQO getFilterParams() {
        return filterParams;
    }

    public void setFilterParams(BdcFilterParamQO filterParams) {
        this.filterParams = filterParams;
    }
}
