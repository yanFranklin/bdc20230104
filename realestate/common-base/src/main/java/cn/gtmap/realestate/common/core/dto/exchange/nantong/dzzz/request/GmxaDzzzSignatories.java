package cn.gtmap.realestate.common.core.dto.exchange.nantong.dzzz.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangyinghao
 */
public class GmxaDzzzSignatories {
    /**
     * 该签署用户在签约模板中的编号(从1开始计数)。获取sigerIndex请使用导出模板签署用户签署控件列表接口。
     */
    @ApiModelProperty("该签署用户在签约模板中的编号(从1开始计数)。获取sigerIndex请使用导出模板签署用户签署控件列表接口。")
    Integer page;

    /**
     * x坐标
     */
    @ApiModelProperty("x坐标")
    float x;

    /**
     * y坐标
     */
    @ApiModelProperty("y坐标")
    float y;


    /**
     * 骑缝章中心点在PDF页面上的纵坐标
     */
    @ApiModelProperty("骑缝章中心点在PDF页面上的纵坐标")
    float posY;

    /**
     * 一个骑缝章印模图片所能覆盖最大页数
     */
    @ApiModelProperty("一个骑缝章印模图片所能覆盖最大页数")
    Integer maxPageCount;

    /**
     * 该签署用户在签署时使用电子印章编号
     */
    @ApiModelProperty("该签署用户在签署时使用电子印章编号")
    String sealId;

    /**
     * 签章关键字字段：默认页面查找到第一个关键字
     */
    @ApiModelProperty("签章关键字字段：默认页面查找到第一个关键字")
    String keyWord;

    /**
     * 关键字签署（0:从最后一页向前，1:从第一页向后）
     * 搜索所有的关键字： 2
     */
    @ApiModelProperty("关键字签署（0:从最后一页向前，1:从第一页向后）\n" +
            "  搜索所有的关键字： 2")
    Integer localType;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public Integer getMaxPageCount() {
        return maxPageCount;
    }

    public void setMaxPageCount(Integer maxPageCount) {
        this.maxPageCount = maxPageCount;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getLocalType() {
        return localType;
    }

    public void setLocalType(Integer localType) {
        this.localType = localType;
    }
}
