package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/05/18/18:49
 * @Description:
 */
@ApiModel(value = "BdcDpFzslTjDTO", description = "不动产大屏发证量统计DTO")
public class BdcDpFzslTjDTO {
    @ApiModelProperty("xAxisData,区县代码")
    private List<Object> xAxisData;
    @ApiModelProperty("证书数量")
    private List<Integer>  zsslList;
    @ApiModelProperty("证明数量")
    private List<Integer>  zmslList;
    @ApiModelProperty("发证量占比")
    private List<BigDecimal> fzlzb;
    @ApiModelProperty("发证总量")
    private Integer fzzs;

    public List<Object> getxAxisData() {
        return xAxisData;
    }

    public void setxAxisData(List<Object> xAxisData) {
        this.xAxisData = xAxisData;
    }

    public List<Integer> getZsslList() {
        return zsslList;
    }

    public void setZsslList(List<Integer> zsslList) {
        this.zsslList = zsslList;
    }

    public List<Integer> getZmslList() {
        return zmslList;
    }

    public void setZmslList(List<Integer> zmslList) {
        this.zmslList = zmslList;
    }

    public List<BigDecimal> getFzlzb() {
        return fzlzb;
    }

    public void setFzlzb(List<BigDecimal> fzlzb) {
        this.fzlzb = fzlzb;
    }

    public Integer getFzzs() {
        return fzzs;
    }

    public void setFzzs(Integer fzzs) {
        this.fzzs = fzzs;
    }

    @Override
    public String toString() {
        return "BdcDpFzslTjDTO{" +
                "xAxisData=" + xAxisData +
                ", zsslList=" + zsslList +
                ", zmslList=" + zmslList +
                ", fzlzb=" + fzlzb +
                ", fzzs=" + fzzs +
                '}';
    }
}
