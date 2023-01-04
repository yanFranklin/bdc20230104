package cn.gtmap.realestate.exchange.core.dto.wwsq.bdcdygzyz.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/1/21
 * @description 关联土地证信息DTO
 */
public class BdcGzyaGltdzxxDTO {


    @ApiModelProperty(value = "土地项目id")
    private String tdzxmid;

    @ApiModelProperty(value = "房产项目id")
    private String fcqzxmid;

    public String getTdzxmid() {
        return tdzxmid;
    }

    public void setTdzxmid(String tdzxmid) {
        this.tdzxmid = tdzxmid;
    }

    public String getFcqzxmid() {
        return fcqzxmid;
    }

    public void setFcqzxmid(String fcqzxmid) {
        this.fcqzxmid = fcqzxmid;
    }

    @Override
    public String toString() {
        return "BdcGzyaGltdzxxDTO{" +
                "tdzxmid='" + tdzxmid + '\'' +
                ", fcqzxmid='" + fcqzxmid + '\'' +
                '}';
    }
}
