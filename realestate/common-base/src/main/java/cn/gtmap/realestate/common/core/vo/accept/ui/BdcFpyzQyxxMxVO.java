package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/27
 * @description 分屏验证-企业信息明细
 */
@ApiModel(value = "BdcFpyzQyxxMxVO", description = "分屏验证-企业信息明细")
public class BdcFpyzQyxxMxVO {

    @ApiModelProperty(value = "企业名称")
    private String qymc;

    @ApiModelProperty(value = "企业证件号")
    private String zjh;

    public String getQymc() {
        return qymc;
    }

    public void setQymc(String qymc) {
        this.qymc = qymc;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }
}
