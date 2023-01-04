package cn.gtmap.realestate.exchange.core.dto.jgpt.ywsl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2022/9/9
 * @description 线上线下业务返回DTO
 */
@ApiModel(description = "线上线下业务返回DTO")
public class JgptTjywResponseDTO {

    @ApiModelProperty("线上业务")
    private JgptYwslDTO xsyw;

    @ApiModelProperty("线下业务")
    private JgptYwslDTO xxyw;

    public JgptYwslDTO getXsyw() {
        return xsyw;
    }

    public void setXsyw(JgptYwslDTO xsyw) {
        this.xsyw = xsyw;
    }

    public JgptYwslDTO getXxyw() {
        return xxyw;
    }

    public void setXxyw(JgptYwslDTO xxyw) {
        this.xxyw = xxyw;
    }

    @Override
    public String toString() {
        return "JgptTjywResponseDTO{" +
                "xsyw=" + xsyw +
                ", xxyw=" + xxyw +
                '}';
    }
}
