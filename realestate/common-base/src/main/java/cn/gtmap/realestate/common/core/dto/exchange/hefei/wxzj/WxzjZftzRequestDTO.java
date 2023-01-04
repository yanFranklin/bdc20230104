package cn.gtmap.realestate.common.core.dto.exchange.hefei.wxzj;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfssDdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlWxjjxxDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/11/25.
 * @description 维修资金支付通知DTO
 */
public class WxzjZftzRequestDTO {

    @ApiModelProperty(value = "不动产项目信息")
    private BdcXmDO bdcXmDO;
    @ApiModelProperty(value = "不动产权利人信息")
    private BdcQlrDO bdcQlrDO;
    @ApiModelProperty(value = "不动产受理收费信息")
    private BdcSlSfxxDO bdcSlSfxxDO;
    @ApiModelProperty(value = "不动产受理收费收税订单信息")
    private BdcSlSfssDdxxDO bdcSlSfssDdxxDO;
    @ApiModelProperty(value = "维修项目信息")
    private BdcSlWxjjxxDO bdcSlWxjjxxDO;

    public BdcXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public BdcQlrDO getBdcQlrDO() {
        return bdcQlrDO;
    }

    public void setBdcQlrDO(BdcQlrDO bdcQlrDO) {
        this.bdcQlrDO = bdcQlrDO;
    }

    public BdcSlSfxxDO getBdcSlSfxxDO() {
        return bdcSlSfxxDO;
    }

    public void setBdcSlSfxxDO(BdcSlSfxxDO bdcSlSfxxDO) {
        this.bdcSlSfxxDO = bdcSlSfxxDO;
    }

    public BdcSlSfssDdxxDO getBdcSlSfssDdxxDO() {
        return bdcSlSfssDdxxDO;
    }

    public void setBdcSlSfssDdxxDO(BdcSlSfssDdxxDO bdcSlSfssDdxxDO) {
        this.bdcSlSfssDdxxDO = bdcSlSfssDdxxDO;
    }

    public BdcSlWxjjxxDO getBdcSlWxjjxxDO() {
        return bdcSlWxjjxxDO;
    }

    public void setBdcSlWxjjxxDO(BdcSlWxjjxxDO bdcSlWxjjxxDO) {
        this.bdcSlWxjjxxDO = bdcSlWxjjxxDO;
    }
}
