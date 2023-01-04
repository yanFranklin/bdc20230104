package cn.gtmap.realestate.building.core.bo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2019/1/16
 * @description
 */
public class FttdMjConfigBO {
    /**
     * 是否计算子户室
     */
    @ApiModelProperty(value = "是否计算子户室")
    private boolean zhs;

    /**
     * 是否计算地下室
     */
    @ApiModelProperty(value = "是否计算地下室")
    private boolean dxs;
    /**
     * 分摊系数
     */
    @ApiModelProperty(value = "分摊系数")
    private double ftxs;
    /**
     * 计算公式序号
     */
    @ApiModelProperty(value = "计算公式序号")
    private String jsgsxh;

    public boolean isZhs() {
        return zhs;
    }

    public void setZhs(boolean zhs) {
        this.zhs = zhs;
    }

    public boolean isDxs() {
        return dxs;
    }

    public void setDxs(boolean dxs) {
        this.dxs = dxs;
    }

    public double getFtxs() {
        return ftxs;
    }

    public void setFtxs(double ftxs) {
        this.ftxs = ftxs;
    }

    public String getJsgsxh() {
        return jsgsxh;
    }

    public void setJsgsxh(String jsgsxh) {
        this.jsgsxh = jsgsxh;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FttdMjConfigBO{");
        sb.append("zhs=").append(zhs);
        sb.append(", dxs=").append(dxs);
        sb.append(", ftxs=").append(ftxs);
        sb.append(", jsgsxh='").append(jsgsxh).append('\'');
        sb.append('}');
        return sb.toString();
    }
}