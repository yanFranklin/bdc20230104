package cn.gtmap.realestate.common.core.vo.etl;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 回显登记信息入参
 * @author: <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @create: 2022-05-11 19:36
 **/
public class HxdjxxbVO implements Serializable {

    private static final long serialVersionUID = -1006795610209110256L;

    @ApiModelProperty(value = "工作流实例id")
    private String gzlslid;

    @ApiModelProperty(value = "退回原因")
    private String thyy;

    @ApiModelProperty(value = "退回人名称")
    private String thrmc;

    @ApiModelProperty(value = "受理状态")
    private String slzt;

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getThyy() {
        return thyy;
    }

    public void setThyy(String thyy) {
        this.thyy = thyy;
    }

    public String getThrmc() {
        return thrmc;
    }

    public void setThrmc(String thrmc) {
        this.thrmc = thrmc;
    }

    public String getSlzt() {
        return slzt;
    }

    public void setSlzt(String slzt) {
        this.slzt = slzt;
    }

    @Override
    public String toString() {
        return "HxdjxxbVO{" +
                "gzlslid='" + gzlslid + '\'' +
                ", thyy='" + thyy + '\'' +
                ", thrmc='" + thrmc + '\'' +
                ", slzt='" + slzt + '\'' +
                '}';
    }
}
