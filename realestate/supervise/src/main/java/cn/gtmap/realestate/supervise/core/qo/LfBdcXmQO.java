package cn.gtmap.realestate.supervise.core.qo;


import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:caolu@gmail.com">caolu</a>
 * @version V1.0, 2022/03/01
 * @description 不动产项目的查询参数
 */
public class LfBdcXmQO {
    @ApiModelProperty(value = "登记开始时间")
    private String djkssj;

    @ApiModelProperty(value = "登记结束时间")
    private String djjssj;

    public String getDjkssj() {
        return djkssj;
    }

    public void setDjkssj(String djkssj) {
        this.djkssj = djkssj;
    }

    public String getDjjssj() {
        return djjssj;
    }

    public void setDjjssj(String djjssj) {
        this.djjssj = djjssj;
    }
}
