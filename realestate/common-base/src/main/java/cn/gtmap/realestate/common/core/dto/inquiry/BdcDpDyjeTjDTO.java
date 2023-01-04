package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/28/23:24
 * @Description:
 */
@ApiModel(value = "BdcDpZzdyjeTjDTO", description = "不动产大屏发证量统计DTO")
public class BdcDpDyjeTjDTO {
    @ApiModelProperty(value="月份")
    private String yf;
    @ApiModelProperty(value="抵押金额")
    private BigDecimal dyje;

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf;
    }

    public BigDecimal getDyje() {
        return dyje;
    }

    public void setDyje(BigDecimal dyje) {
        this.dyje = dyje;
    }

    @Override
    public String toString() {
        return "BdcDpZzdyjeTjDTO{" +
                "yf='" + yf + '\'' +
                ", dyje=" + dyje +
                '}';
    }
}
