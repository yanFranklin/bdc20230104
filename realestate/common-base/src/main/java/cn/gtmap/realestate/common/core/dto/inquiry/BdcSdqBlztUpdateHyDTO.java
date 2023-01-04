package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Author  <a href="mailto:gaoyu@gtmap.cn">gaoyu</a>
 * @Description 水电气核验dto
 * @Date 2022/4/28
 **/
@Api(value = "BdcSdqBlztUpdateHyDTO", description = "水电气核验dto")
public class BdcSdqBlztUpdateHyDTO {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "核验结果 1：成功，0：失败")
    private Integer hyjg;

    @ApiModelProperty(value = "核验详情")
    private String hyxq;

    @ApiModelProperty(value = "户主名称")
    private String hzmc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHyjg() {
        return hyjg;
    }

    public void setHyjg(Integer hyjg) {
        this.hyjg = hyjg;
    }

    public String getHyxq() {
        return hyxq;
    }

    public void setHyxq(String hyxq) {
        this.hyxq = hyxq;
    }

    public String getHzmc() {
        return hzmc;
    }

    public void setHzmc(String hzmc) {
        this.hzmc = hzmc;
    }

    @Override
    public String toString() {
        return "BdcSdqBlztUpdateHyDTO{" +
                "id='" + id + '\'' +
                ", hyjg=" + hyjg +
                ", hyxq='" + hyxq + '\'' +
                '}';
    }
}
