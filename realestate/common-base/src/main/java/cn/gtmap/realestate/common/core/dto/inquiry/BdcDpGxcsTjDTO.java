package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/05/18/19:10
 * @Description:
 */
@ApiModel(value = "BdcDpGxcsTjDTO", description = "不动产大屏共享次数统计DTO")
public class BdcDpGxcsTjDTO {
    @ApiModelProperty("共享总次数")
    private BigDecimal gxzcs;
    @ApiModelProperty("共享详情")
    private List<Map> gxxq;

    public BigDecimal getGxzcs() {
        return gxzcs;
    }

    public void setGxzcs(BigDecimal gxzcs) {
        this.gxzcs = gxzcs;
    }

    public List<Map> getGxxq() {
        return gxxq;
    }

    public void setGxxq(List<Map> gxxq) {
        this.gxxq = gxxq;
    }

    @Override
    public String toString() {
        return "BdcDpGxcsTjDTO{" +
                "gxzcs=" + gxzcs +
                ", gxxq=" + gxxq +
                '}';
    }
}
