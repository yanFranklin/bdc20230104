package cn.gtmap.realestate.common.core.vo.accept.ui;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href ="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.3, 2019-11-12
 * @description 受理收费项目VO
 */
@ApiModel(value = "BdcSlSfxmVO", description = "不动产受理收费信息VO")
public class BdcSlSfxmVO extends BdcSlSfxmDO {
    
    @ApiModelProperty(value = "收费项目单价")
    private Double sfxmdj;

    @ApiModelProperty(value = "收费项目代码@区县代码")
    private String sfxmdmAndQxdm;

    @Override
    public Double getSfxmdj() {
        return sfxmdj;
    }

    @Override
    public void setSfxmdj(Double sfxmdj) {
        this.sfxmdj = sfxmdj;
    }

    public String getSfxmdmAndQxdm() {
        return sfxmdmAndQxdm;
    }

    public void setSfxmdmAndQxdm(String sfxmdmAndQxdm) {
        this.sfxmdmAndQxdm = sfxmdmAndQxdm;
    }
}
