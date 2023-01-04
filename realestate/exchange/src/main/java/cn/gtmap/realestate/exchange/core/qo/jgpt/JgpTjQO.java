package cn.gtmap.realestate.exchange.core.qo.jgpt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/8
 * @description 监管平台统计QO
 */
@ApiModel(value = "JgpTjQO", description = "监管平台统计QO")
public class JgpTjQO {

    @ApiModelProperty(value = "起始时间")
    private String qssj;

    @ApiModelProperty(value = "结束时间")
    private String jssj;

    @ApiModelProperty(value = "部门名称")
    private List<String> bmmc;

    public String getQssj() {
        return qssj;
    }

    public void setQssj(String qssj) {
        this.qssj = qssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public List<String> getBmmc() {
        return bmmc;
    }

    public void setBmmc(List<String> bmmc) {
        this.bmmc = bmmc;
    }

    @Override
    public String toString() {
        return "JgpTjQO{" +
                "qssj=" + qssj +
                ", jssj=" + jssj +
                ", bmmc=" + bmmc +
                '}';
    }
}
