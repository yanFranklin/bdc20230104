package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Author: <a href="mailto:wutao@gtmap.cn">wutao</a>
 * @version: 2022/07/15/17:37
 * @Description: 省级查询数据量DTO
 */
public class SjcxsjlDTO {
    @ApiModelProperty("下发量")
    String xfl;
    @ApiModelProperty("查询量")
    String cxl;
    @ApiModelProperty("上报量")
    String sbl;

    public String getXfl() { return xfl; }

    public void setXfl(String xfl) { this.xfl = xfl; }

    public String getCxl() { return cxl; }

    public void setCxl(String cxl) { this.cxl = cxl; }

    public String getSbl() { return sbl; }

    public void setSbl(String sbl) { this.sbl = sbl; }

    @Override
    public String toString() {
        return "SjcxsjlDTO{" +
                "xfl='" + xfl + '\'' +
                ", cxl='" + cxl + '\'' +
                ", sbl='" + sbl + '\'' +
                '}';
    }
}
