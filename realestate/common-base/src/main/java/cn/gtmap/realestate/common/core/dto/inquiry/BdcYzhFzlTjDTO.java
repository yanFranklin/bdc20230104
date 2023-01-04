package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2021/03/03
 * @description  不动产印制号废证量统计
 */
@ApiModel(value = "BdcYzhFzlTjDTO", description = "不动产印制号废证量统计结果DTO")
public class BdcYzhFzlTjDTO {

    @ApiModelProperty(value = "使用人")
    private String syr;

    @ApiModelProperty(value = "作废量")
    private int zf;

    @ApiModelProperty(value = "领用量")
    private int lyl;

    @ApiModelProperty(value = "已使用")
    private int ysy;

    public String getSyr() {
        return syr;
    }

    public void setSyr(String syr) {
        this.syr = syr;
    }

    public int getZf() {
        return zf;
    }

    public void setZf(int zf) {
        this.zf = zf;
    }

    public int getLyl() {
        return lyl;
    }

    public void setLyl(int lyl) {
        this.lyl = lyl;
    }

    public int getYsy() {
        return ysy;
    }

    public void setYsy(int ysy) {
        this.ysy = ysy;
    }
}
