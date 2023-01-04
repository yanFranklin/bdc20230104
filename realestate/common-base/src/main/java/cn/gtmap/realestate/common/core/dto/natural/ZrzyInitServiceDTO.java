package cn.gtmap.realestate.common.core.dto.natural;

import cn.gtmap.realestate.common.core.domain.natural.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/27
 * @ 初始化单个业务信息返回的所有结果集
 */
@ApiModel(value = "ZrzyInitServiceDTO", description = "初始化单个业务信息返回的所有结果集")
public class ZrzyInitServiceDTO {

    @ApiModelProperty(value = "自然资源项目")
    private ZrzyXmDO zrzyXmDO;

    @ApiModelProperty(value = "自然资源项目历史关系")
    private List<ZrzyXmLsgxDO> zrzyXmLsgxDOList;

    @ApiModelProperty(value = "自然资源登记单元")
    private ZrzyDjdyDO zrzyDjdyDO;

    @ApiModelProperty(value = "水流相关数据")
    private List<ZrzySzyDO> zrzySzyDOList;

    @ApiModelProperty(value = "湿地相关数据")
    private List<ZrzySdDO> zrzySdDOList;

    @ApiModelProperty(value = "森林相关数据")
    private List<ZrzySlDO> zrzySlDOList;

    @ApiModelProperty(value = "荒地相关数据")
    private List<ZrzyHdDO> zrzyHdDOList;

    @ApiModelProperty(value = "草原相关数据")
    private List<ZrzyCyDO> zrzyCyDOList;

    @ApiModelProperty(value = "海域相关数据")
    private List<ZrzyHyDO> zrzyHyDOList;

    @ApiModelProperty(value = "无居民海岛相关数据")
    private List<ZrzyWjmhdDO> zrzyWjmhdDOList;

    @ApiModelProperty(value = "探明储量矿产资源相关数据")
    private List<ZrzyTmclkczyDO> zrzyTmclkczyDOList;

    @ApiModelProperty(value = "公共管制关联信息相关数据")
    private List<ZrzyGggzglxxDO> zrzyGggzglxxDOList;

    @ApiModelProperty(value = "不动产权利关联信息相关数据")
    private List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxDOList;

    @ApiModelProperty(value = "矿业权关联信息相关数据")
    private List<ZrzyKyqglxxDO> zrzyKyqglxxDOList;

    @ApiModelProperty(value = "取水权关联信息相关数据")
    private List<ZrzyQsqglxxDO> zrzyQsqglxxDOList;

    @ApiModelProperty(value = "排污权关联信息相关数据")
    private List<ZrzyPwqglxxDO> zrzyPwqglxxDOList;

    public ZrzyXmDO getZrzyXmDO() {
        return zrzyXmDO;
    }

    public void setZrzyXmDO(ZrzyXmDO zrzyXmDO) {
        this.zrzyXmDO = zrzyXmDO;
    }

    public List<ZrzyXmLsgxDO> getZrzyXmLsgxDOList() {
        return zrzyXmLsgxDOList;
    }

    public void setZrzyXmLsgxDOList(List<ZrzyXmLsgxDO> zrzyXmLsgxDOList) {
        this.zrzyXmLsgxDOList = zrzyXmLsgxDOList;
    }

    public ZrzyDjdyDO getZrzyDjdyDO() {
        return zrzyDjdyDO;
    }

    public void setZrzyDjdyDO(ZrzyDjdyDO zrzyDjdyDO) {
        this.zrzyDjdyDO = zrzyDjdyDO;
    }

    public List<ZrzySzyDO> getZrzySzyDOList() {
        return zrzySzyDOList;
    }

    public void setZrzySzyDOList(List<ZrzySzyDO> zrzySzyDOList) {
        this.zrzySzyDOList = zrzySzyDOList;
    }

    public List<ZrzySdDO> getZrzySdDOList() {
        return zrzySdDOList;
    }

    public void setZrzySdDOList(List<ZrzySdDO> zrzySdDOList) {
        this.zrzySdDOList = zrzySdDOList;
    }

    public List<ZrzySlDO> getZrzySlDOList() {
        return zrzySlDOList;
    }

    public void setZrzySlDOList(List<ZrzySlDO> zrzySlDOList) {
        this.zrzySlDOList = zrzySlDOList;
    }

    public List<ZrzyHdDO> getZrzyHdDOList() {
        return zrzyHdDOList;
    }

    public void setZrzyHdDOList(List<ZrzyHdDO> zrzyHdDOList) {
        this.zrzyHdDOList = zrzyHdDOList;
    }

    public List<ZrzyCyDO> getZrzyCyDOList() {
        return zrzyCyDOList;
    }

    public void setZrzyCyDOList(List<ZrzyCyDO> zrzyCyDOList) {
        this.zrzyCyDOList = zrzyCyDOList;
    }

    public List<ZrzyHyDO> getZrzyHyDOList() {
        return zrzyHyDOList;
    }

    public void setZrzyHyDOList(List<ZrzyHyDO> zrzyHyDOList) {
        this.zrzyHyDOList = zrzyHyDOList;
    }

    public List<ZrzyWjmhdDO> getZrzyWjmhdDOList() {
        return zrzyWjmhdDOList;
    }

    public void setZrzyWjmhdDOList(List<ZrzyWjmhdDO> zrzyWjmhdDOList) {
        this.zrzyWjmhdDOList = zrzyWjmhdDOList;
    }

    public List<ZrzyTmclkczyDO> getZrzyTmclkczyDOList() {
        return zrzyTmclkczyDOList;
    }

    public void setZrzyTmclkczyDOList(List<ZrzyTmclkczyDO> zrzyTmclkczyDOList) {
        this.zrzyTmclkczyDOList = zrzyTmclkczyDOList;
    }

    public List<ZrzyGggzglxxDO> getZrzyGggzglxxDOList() {
        return zrzyGggzglxxDOList;
    }

    public void setZrzyGggzglxxDOList(List<ZrzyGggzglxxDO> zrzyGggzglxxDOList) {
        this.zrzyGggzglxxDOList = zrzyGggzglxxDOList;
    }

    public List<ZrzyBdcqlglxxDO> getZrzyBdcqlglxxDOList() {
        return zrzyBdcqlglxxDOList;
    }

    public void setZrzyBdcqlglxxDOList(List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxDOList) {
        this.zrzyBdcqlglxxDOList = zrzyBdcqlglxxDOList;
    }

    public List<ZrzyKyqglxxDO> getZrzyKyqglxxDOList() {
        return zrzyKyqglxxDOList;
    }

    public void setZrzyKyqglxxDOList(List<ZrzyKyqglxxDO> zrzyKyqglxxDOList) {
        this.zrzyKyqglxxDOList = zrzyKyqglxxDOList;
    }

    public List<ZrzyQsqglxxDO> getZrzyQsqglxxDOList() {
        return zrzyQsqglxxDOList;
    }

    public void setZrzyQsqglxxDOList(List<ZrzyQsqglxxDO> zrzyQsqglxxDOList) {
        this.zrzyQsqglxxDOList = zrzyQsqglxxDOList;
    }

    public List<ZrzyPwqglxxDO> getZrzyPwqglxxDOList() {
        return zrzyPwqglxxDOList;
    }

    public void setZrzyPwqglxxDOList(List<ZrzyPwqglxxDO> zrzyPwqglxxDOList) {
        this.zrzyPwqglxxDOList = zrzyPwqglxxDOList;
    }
}
