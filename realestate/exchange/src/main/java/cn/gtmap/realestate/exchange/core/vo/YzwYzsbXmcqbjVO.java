package cn.gtmap.realestate.exchange.core.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/12/4
 * @description
 */
@ApiModel(value = "YzwYzsbSjcsVO", description = "项目超期办结信息")
public class YzwYzsbXmcqbjVO {

    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    @ApiModelProperty(value = "承诺时间")
    private Date clsj;

    @ApiModelProperty(value = "办结时间")
    private Date bjsj;

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }
}
