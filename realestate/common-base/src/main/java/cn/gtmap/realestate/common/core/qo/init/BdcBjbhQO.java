package cn.gtmap.realestate.common.core.qo.init;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/23
 * @description 合肥办件编号
 */
public class BdcBjbhQO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    @ApiModelProperty(value = "登记开始时间")
    private String djkssj;

    @ApiModelProperty(value = "登记结束时间")
    private String djjssj;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

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

    @Override
    public String toString() {
        return "BdcBjbhQO{" +
                "slbh='" + slbh + '\'' +
                ", lcmc='" + lcmc + '\'' +
                ", djkssj='" + djkssj + '\'' +
                ", djjssj='" + djjssj + '\'' +
                '}';
    }
}
