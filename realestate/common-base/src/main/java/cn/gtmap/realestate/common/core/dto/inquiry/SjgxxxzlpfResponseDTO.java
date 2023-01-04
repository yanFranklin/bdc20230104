package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version: 2022/07/15/17:37
 * @Description: 省级质量评分responseDTO
 */
public class SjgxxxzlpfResponseDTO {
    @ApiModelProperty("序号")
    String xh;
    @ApiModelProperty("区划代码")
    String qhdm;
    @ApiModelProperty("区划名称")
    String qhmc;
    @ApiModelProperty("应下载总数量")
    String yxzzsl;
    @ApiModelProperty("已下载数量")
    String yxzsl;
    @ApiModelProperty("反馈成功数量")
    String fkcgsl;
    @ApiModelProperty("下载率")
    String xzl;
    @ApiModelProperty("反馈成功率")
    String fkcgl;
    @ApiModelProperty("综合评分")
    String df;

    public String getXh() { return xh; }

    public void setXh(String xh) { this.xh = xh; }

    public String getQhdm() { return qhdm; }

    public void setQhdm(String qhdm) { this.qhdm = qhdm; }

    public String getQhmc() { return qhmc; }

    public void setQhmc(String qhmc) { this.qhmc = qhmc; }

    public String getYxzzsl() { return yxzzsl; }

    public void setYxzzsl(String yxzzsl) { this.yxzzsl = yxzzsl; }

    public String getYxzsl() { return yxzsl; }

    public void setYxzsl(String yxzsl) { this.yxzsl = yxzsl; }

    public String getFkcgsl() { return fkcgsl; }

    public void setFkcgsl(String fkcgsl) { this.fkcgsl = fkcgsl; }

    public String getXzl() { return xzl; }

    public void setXzl(String xzl) { this.xzl = xzl; }

    public String getFkcgl() { return fkcgl; }

    public void setFkcgl(String fkcgl) { this.fkcgl = fkcgl; }

    public String getDf() { return df; }

    public void setDf(String df) { this.df = df; }

    @Override
    public String toString() {
        return "SjgxxxzlpfResponseDTO{" +
                "xh='" + xh + '\'' +
                ", qhdm='" + qhdm + '\'' +
                ", qhmc='" + qhmc + '\'' +
                ", yxzzsl='" + yxzzsl + '\'' +
                ", yxzsl='" + yxzsl + '\'' +
                ", fkcgsl='" + fkcgsl + '\'' +
                ", xzl='" + xzl + '\'' +
                ", fkcgl='" + fkcgl + '\'' +
                ", df='" + df + '\'' +
                '}';
    }
}
