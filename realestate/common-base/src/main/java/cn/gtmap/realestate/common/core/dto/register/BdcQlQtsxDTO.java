package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/11/8
 * @description 不动产权利及其他事项登记信息
 */
public class BdcQlQtsxDTO {
    /**
     * 权利类型
     */
    @ApiModelProperty(value = "产权权利类型")
    Integer qllx;
    /**
     * 登记页码
     */
    @ApiModelProperty(value = "产权登记页码")
    String djym;

    /**
     * 业主共有登记页码
     */
    @ApiModelProperty(value = "业主共有登记页码")
    String yzgydjym;

    /**
     * 抵押权登记页码
     */
    @ApiModelProperty(value = "抵押权登记页码")
    String dyadjym;
    /**
     * 地役权登记页码
     */
    @ApiModelProperty(value = "地役权登记页码")
    String dyidjym;
    /**
     * 预告登记页码
     */
    @ApiModelProperty(value = "预告登记页码")
    String ygdjym;
    /**
     * 异议登记页码
     */
    @ApiModelProperty(value = "异议登记页码")
    String yydjym;

    /**
     * 查封登记页码
     */
    @ApiModelProperty(value = "查封登记页码")
    String cfdjym;

    /**
     * 经营权登记页码
     */
    @ApiModelProperty(value = "经营权登记页码")
    String jyqdjym;

    /**
     * 居住权登记页码
     */
    @ApiModelProperty(value = "居住权登记页码")
    String jzqdjym;

    public String getDjym() {
        return djym;
    }

    public void setDjym(String djym) {
        this.djym = djym;
    }

    public String getDyadjym() {
        return dyadjym;
    }

    public void setDyadjym(String dyadjym) {
        this.dyadjym = dyadjym;
    }

    public String getDyidjym() {
        return dyidjym;
    }

    public void setDyidjym(String dyidjym) {
        this.dyidjym = dyidjym;
    }

    public String getYgdjym() {
        return ygdjym;
    }

    public void setYgdjym(String ygdjym) {
        this.ygdjym = ygdjym;
    }

    public String getYydjym() {
        return yydjym;
    }

    public void setYydjym(String yydjym) {
        this.yydjym = yydjym;
    }

    public String getCfdjym() {
        return cfdjym;
    }

    public void setCfdjym(String cfdjym) {
        this.cfdjym = cfdjym;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public String getYzgydjym() {
        return yzgydjym;
    }

    public void setYzgydjym(String yzgydjym) {
        this.yzgydjym = yzgydjym;
    }

    public String getJyqdjym() {
        return jyqdjym;
    }

    public void setJyqdjym(String jyqdjym) {
        this.jyqdjym = jyqdjym;
    }

    public String getJzqdjym() {
        return jzqdjym;
    }

    public void setJzqdjym(String jzqdjym) {
        this.jzqdjym = jzqdjym;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BdcQlQtsxDTO{");
        sb.append("qllx=").append(qllx);
        sb.append(", djym='").append(djym).append('\'');
        sb.append(", yzgydjym='").append(yzgydjym).append('\'');
        sb.append(", dyadjym='").append(dyadjym).append('\'');
        sb.append(", dyidjym='").append(dyidjym).append('\'');
        sb.append(", ygdjym='").append(ygdjym).append('\'');
        sb.append(", yydjym='").append(yydjym).append('\'');
        sb.append(", cfdjym='").append(cfdjym).append('\'');
        sb.append(", jyqdjym='").append(jyqdjym).append('\'');
        sb.append(", jzqdjym='").append(jzqdjym).append('\'');
        sb.append('}');
        return sb.toString();
    }
}