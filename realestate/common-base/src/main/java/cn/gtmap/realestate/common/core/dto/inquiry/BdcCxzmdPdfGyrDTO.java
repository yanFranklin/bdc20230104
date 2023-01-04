package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

/**
 * 常州查询证明单共有人信息
 * @date 2021/08/04 14:33
 */
public class BdcCxzmdPdfGyrDTO {
    @ApiModelProperty(value = "共有人")
    private String gyr;

    @ApiModelProperty(value = "共有人证号")
    private String gyrzh;


    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    public String getGyrzh() {
        return gyrzh;
    }

    public void setGyrzh(String gyrzh) {
        this.gyrzh = gyrzh;
    }

    @Override
    public String toString() {
        return "BdcCxzmdPdfGyrDTO{" +
                "gyr='" + gyr + '\'' +
                ", gyrzh='" + gyrzh + '\'' +
                '}';
    }
}
