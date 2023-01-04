package cn.gtmap.realestate.etl.core.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/10
 * @description 办理状态
 */
public class GxWwBlztDTO {

    @ApiModelProperty("项目ID")
    private String xmid;

    @ApiModelProperty("是否创建")
    private Integer sfcj;

    @ApiModelProperty("审核状态")
    private String shzt;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getSfcj() {
        return sfcj;
    }

    public void setSfcj(Integer sfcj) {
        this.sfcj = sfcj;
    }

    public String getShzt() {
        return shzt;
    }

    public void setShzt(String shzt) {
        this.shzt = shzt;
    }
}
