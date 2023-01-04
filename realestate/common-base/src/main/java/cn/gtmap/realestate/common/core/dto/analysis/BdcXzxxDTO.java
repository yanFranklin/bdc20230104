package cn.gtmap.realestate.common.core.dto.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description 限制信息
 */
@ApiModel(value = "BdcXzxxDTO", description = "限制信息")
public class BdcXzxxDTO {

    @ApiModelProperty("查封信息")
    private List<BdcQscxCfXxDTO> cfxx;

    @ApiModelProperty("抵押信息")
    private List<BdcQscxDyaXxDTO> diyaxx;

    @ApiModelProperty("预告信息")
    private List<BdcQscxYgXxDTO> ygxx;

    @ApiModelProperty("异议信息")
    private List<BdcYyXxDTO> yyxx;

    @ApiModelProperty("地役信息")
    private List<BdcDyiXxDTO> diyixx;

    @ApiModelProperty("锁定信息")
    private List<BdcSdXxDTO> sdxx;

    public List<BdcQscxCfXxDTO> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<BdcQscxCfXxDTO> cfxx) {
        this.cfxx = cfxx;
    }

    public List<BdcQscxDyaXxDTO> getDiyaxx() {
        return diyaxx;
    }

    public void setDiyaxx(List<BdcQscxDyaXxDTO> diyaxx) {
        this.diyaxx = diyaxx;
    }

    public List<BdcYyXxDTO> getYyxx() {
        return yyxx;
    }

    public void setYyxx(List<BdcYyXxDTO> yyxx) {
        this.yyxx = yyxx;
    }

    public List<BdcQscxYgXxDTO> getYgxx() {
        return ygxx;
    }

    public void setYgxx(List<BdcQscxYgXxDTO> ygxx) {
        this.ygxx = ygxx;
    }

    public List<BdcDyiXxDTO> getDiyixx() {
        return diyixx;
    }

    public void setDiyixx(List<BdcDyiXxDTO> diyixx) {
        this.diyixx = diyixx;
    }

    public List<BdcSdXxDTO> getSdxx() {
        return sdxx;
    }

    public void setSdxx(List<BdcSdXxDTO> sdxx) {
        this.sdxx = sdxx;
    }
}
