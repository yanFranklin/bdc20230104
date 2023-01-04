package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description 变更基本属性
 */
public class BgJbsxDTO {
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;

    /**
     * 原主键
     */
    @ApiModelProperty(value = "原主键")
    private String yIndex;
    /**
     * 新主键
     */
    @ApiModelProperty(value = "新主键")
    private String index;

    /**
     * 变更类型是否是灭失
     */
    @ApiModelProperty(value = "变更类型是否是灭失")
    private boolean ms;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getyIndex() {
        return yIndex;
    }

    public void setyIndex(String yIndex) {
        this.yIndex = yIndex;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public boolean isMs() {
        return ms;
    }

    public void setMs(boolean ms) {
        this.ms = ms;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BgJbsxDTO{");
        sb.append("bgbh='").append(bgbh).append('\'');
        sb.append(", yIndex='").append(yIndex).append('\'');
        sb.append(", index='").append(index).append('\'');
        sb.append(", ms=").append(ms);
        sb.append('}');
        return sb.toString();
    }
}