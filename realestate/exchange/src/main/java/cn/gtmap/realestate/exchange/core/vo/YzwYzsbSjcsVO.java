package cn.gtmap.realestate.exchange.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description
 */
@ApiModel(value = "YzwYzsbSjcsVO", description = "收件超时信息")
public class YzwYzsbSjcsVO {

    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }
}
