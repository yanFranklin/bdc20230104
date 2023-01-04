package cn.gtmap.realestate.common.core.dto.init.znsh;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/28
 * @description 数据校验结果
 */
public class BdcSjjyDTO {

    @ApiModelProperty(value = "权利人数据校验信息")
    private BdcSjjyQlrmkDTO qlr;

    @ApiModelProperty(value = "义务人数据校验信息")
    private BdcSjjyQlrmkDTO ywr;

    @ApiModelProperty(value = "项目权利数据校验信息")
    private BdcSjjyMkDTO xmqlxx;

    public BdcSjjyQlrmkDTO getQlr() {
        return qlr;
    }

    public void setQlr(BdcSjjyQlrmkDTO qlr) {
        this.qlr = qlr;
    }

    public BdcSjjyQlrmkDTO getYwr() {
        return ywr;
    }

    public void setYwr(BdcSjjyQlrmkDTO ywr) {
        this.ywr = ywr;
    }

    public BdcSjjyMkDTO getXmqlxx() {
        return xmqlxx;
    }

    public void setXmqlxx(BdcSjjyMkDTO xmqlxx) {
        this.xmqlxx = xmqlxx;
    }
}
