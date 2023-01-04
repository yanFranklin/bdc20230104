package cn.gtmap.realestate.common.core.dto.exchange.yancheng.yzt.cxgdspxx.request;

import io.swagger.annotations.ApiModelProperty;


/**
 * @author <a href="mailto:zhongjinpeng@gtmap.cn">zhongjinpeng</a>
 * @version 1.0, 2020/12/24
 * @description 一张图 查询供地审批信息请求参数
 */
public class YztCxGdspxxRequestDTO {

    @ApiModelProperty(value = "申请用地单位",required = true)
    private String sqyddw;

    @ApiModelProperty(value = "用地位置",required = true)
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
        return "YztCxGdspxxRequestDTO{" +
                "sqyddw='" + sqyddw + '\'' +
                ", ydwz='" + ydwz + '\'' +
                '}';
    }
}
