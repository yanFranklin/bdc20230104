package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version: V1.0, 2019.03.18
 * @description:
 */
@Api(value = "FccxDO", description = "动态查询传输对象")
public class FccxDO {
    @ApiModelProperty("序号")
    private String xh;
    @ApiModelProperty("权利人名称")
    private String qlrmc;
    @ApiModelProperty("证件号")
    private String zjh;

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    @Override
    public String toString() {
        return "FccxDO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", zjh='" + zjh + '\'' +
                '}';
    }
}