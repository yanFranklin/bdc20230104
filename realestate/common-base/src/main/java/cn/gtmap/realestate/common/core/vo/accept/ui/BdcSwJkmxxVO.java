package cn.gtmap.realestate.common.core.vo.accept.ui;


import cn.gtmap.realestate.common.core.domain.accept.BdcSlYjkxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2022/9/23
 * @description 税务信息VO
 */
@ApiModel(value = "BdcSwJkmxxVO", description = "税务缴款码信息VO")
public class BdcSwJkmxxVO extends BdcSlYjkxxDO {

    @ApiModelProperty(value = "缴税二维码图片base64编码")
    private String jsewm;

    public String getJsewm() {
        return jsewm;
    }

    public void setJsewm(String jsewm) {
        this.jsewm = jsewm;
    }
}
