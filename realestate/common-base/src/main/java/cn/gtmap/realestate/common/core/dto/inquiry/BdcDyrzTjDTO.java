package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/04/08/17:03
 * @Description:
 */
@ApiModel(value = "BdcDyrzTjDTO", description = "不动产大屏抵押融资统计DTO")
public class BdcDyrzTjDTO {
    @ApiModelProperty("抵押总金额")
    private List<BdcDpCxTjDTO> dyzje;
    @ApiModelProperty("住宅抵押金额、住宅套数（按月份）")
    private List<Map> zztsdyjeList;

    public List<BdcDpCxTjDTO> getDyzje() {
        return dyzje;
    }

    public void setDyzje(List<BdcDpCxTjDTO> dyzje) {
        this.dyzje = dyzje;
    }

    public List<Map> getZztsdyjeList() {
        return zztsdyjeList;
    }

    public void setZztsdyjeList(List<Map> zztsdyjeList) {
        this.zztsdyjeList = zztsdyjeList;
    }

    @Override
    public String toString() {
        return "BdcDyrzTjDTO{" +
                "dyzje=" + dyzje +
                ", zztsdyjeList=" + zztsdyjeList +
                '}';
    }
}
