package cn.gtmap.realestate.common.core.dto.exchange.yancheng.court.lzzt;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0
 * @date 2020/12/07 15:13
 */
public class CourtLzztRequestDTO {

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "CourtLzztRequestDTO{" +
                "bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
