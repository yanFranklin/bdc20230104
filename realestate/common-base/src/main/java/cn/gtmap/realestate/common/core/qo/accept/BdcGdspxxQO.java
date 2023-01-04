package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/12/18
 * @description
 */
@ApiModel(value = "BdcGdspxxQO", description = "供地审批信息QO对象")
public class BdcGdspxxQO {

    @ApiModelProperty(value = "申请用地单位")
    private String sqyddw;

    @ApiModelProperty(value = "用地位置")
    private String ydwz;

    public String getSqyddw() {
        return sqyddw;
    }

    public void setSqyddw(String sqyddw) {
        this.sqyddw = sqyddw;
    }

    public String getYdwz() {
        return ydwz;
    }

    public void setYdwz(String ydwz) {
        this.ydwz = ydwz;
    }

    @Override
    public String toString() {
        return "BdcGdspxxQO{" +
                "sqyddw='" + sqyddw + '\'' +
                ", ydwz='" + ydwz + '\'' +
                '}';
    }
}
