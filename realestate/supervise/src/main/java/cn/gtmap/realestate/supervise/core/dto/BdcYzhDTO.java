package cn.gtmap.realestate.supervise.core.dto;

import cn.gtmap.realestate.common.core.domain.certificate.BdcYzhDO;
import io.swagger.annotations.ApiModelProperty;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2018/12/4
 * @description 廉防5：印制号统计台账，列表信息
 */
public class BdcYzhDTO extends BdcYzhDO {
    @ApiModelProperty(value = "作废原因")
    private String zfyy;

    @ApiModelProperty(value = "作废详情描述")
    private String zfxqms;

    public String getZfyy() {
        return zfyy;
    }

    public void setZfyy(String zfyy) {
        this.zfyy = zfyy;
    }

    public String getZfxqms() {
        return zfxqms;
    }

    public void setZfxqms(String zfxqms) {
        this.zfxqms = zfxqms;
    }
}
