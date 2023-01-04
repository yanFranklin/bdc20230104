package cn.gtmap.realestate.common.core.dto.exchange.wwsq;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/1/21
 * @description 关联土地证信息DTO
 */
public class GltdzxxDTO {


    @ApiModelProperty(value = "土地证号")
    private String tdzh;

    @ApiModelProperty(value = "土地项目id")
    private String xmid;

    @ApiModelProperty(value = "房产项目id")
    private String fcqzxmid;

    public String getFcqzxmid() {
        return fcqzxmid;
    }

    public void setFcqzxmid(String fcqzxmid) {
        this.fcqzxmid = fcqzxmid;
    }

    public String getTdzh() {
        return tdzh;
    }

    public void setTdzh(String tdzh) {
        this.tdzh = tdzh;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    @Override
    public String toString() {
        return "GltdzxxDTO{" +
                "tdzh='" + tdzh + '\'' +
                ", xmid='" + xmid + '\'' +
                '}';
    }
}
