package cn.gtmap.realestate.common.core.dto.exchange.sdqgh.dian.sqgh.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/1/12
 * @description 水电气过户取消请求
 */
@ApiModel(value = "SdqGhCancelRequestDTO",description = "水电气过户取消请求")
public class SdqGhCancelRequestDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }
}
