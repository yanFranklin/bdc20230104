package cn.gtmap.realestate.common.core.dto.natural;

import cn.gtmap.realestate.common.core.domain.natural.*;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZrzkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源资源状况单元信息DTO
 */
@Api(value = "ZrzyZrzkYwxxDTO", description = "自然资源资源状况单元信息DTO")
public class ZrzyZrzkYwxxDTO {

    @ApiModelProperty(value="自然资源项目")
    private ZrzyXmDO zrzyXm;

    @ApiModelProperty(value="自然资源登记单元")
    private ZrzyDjdyDO zrzyDjdy;

    @ApiModelProperty(value="自然资源水流")
    private List<ZrzySzyDO> zrzySzyDOList;

    @ApiModelProperty(value="自然资源湿地")
    private List<ZrzySdDO> zrzySdDOList;

    @ApiModelProperty(value="自然资源森林")
    private List<ZrzySlDO> zrzySlDOList;

    @ApiModelProperty(value="自然资源草原")
    private List<ZrzyCyDO> zrzyCyDOList;

    @ApiModelProperty(value="自然资源荒地")
    private List<ZrzyHdDO> zrzyHdDOList;

    @ApiModelProperty(value="自然资源海域")
    private List<ZrzyHyDO> zrzyHyDOList;

    @ApiModelProperty(value="自然资源无居民海岛")
    private List<ZrzyWjmhdDO> zrzyWjmhdDOList;

    @ApiModelProperty(value="自然资源探明储量矿产资源")
    private List<ZrzyTmclkczyDO> zrzyTmclkczyDOList;

    public ZrzyXmDO getZrzyXm() {
        return zrzyXm;
    }

    public void setZrzyXm(ZrzyXmDO zrzyXm) {
        this.zrzyXm = zrzyXm;
    }

    public ZrzyDjdyDO getZrzyDjdy() {
        return zrzyDjdy;
    }

    public void setZrzyDjdy(ZrzyDjdyDO zrzyDjdy) {
        this.zrzyDjdy = zrzyDjdy;
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

    public List<ZrzyCyDO> getZrzyCyDOList() {
        return zrzyCyDOList;
    }

    public void setZrzyCyDOList(List<ZrzyCyDO> zrzyCyDOList) {
        this.zrzyCyDOList = zrzyCyDOList;
    }

    public List<ZrzyHdDO> getZrzyHdDOList() {
        return zrzyHdDOList;
    }

    public void setZrzyHdDOList(List<ZrzyHdDO> zrzyHdDOList) {
        this.zrzyHdDOList = zrzyHdDOList;
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
}
