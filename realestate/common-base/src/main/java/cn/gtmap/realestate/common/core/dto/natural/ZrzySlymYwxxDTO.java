package cn.gtmap.realestate.common.core.dto.natural;

import cn.gtmap.realestate.common.core.domain.natural.*;
import cn.gtmap.realestate.common.core.vo.natural.ZrzyZrzkVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/10/25
 * @description 自然资源单元信息DTO
 */
@Api(value = "ZrzySlymYwxxDTO", description = "自然资源单元信息DTO")
public class ZrzySlymYwxxDTO {

    @ApiModelProperty(value="自然资源项目")
    private ZrzyXmDO zrzyXm;

    @ApiModelProperty(value="自然资源登记单元")
    private ZrzyDjdyDO zrzyDjdy;

    @ApiModelProperty(value="公共管制关联信息")
    private List<ZrzyGggzglxxDO> zrzyGggzglxxList;

    @ApiModelProperty(value="不动产权利关联信息")
    private List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxList;

    @ApiModelProperty(value="矿业权关联信息")
    private List<ZrzyKyqglxxDO> zrzyKyqglxxList;

    @ApiModelProperty(value="取水权关联信息")
    private List<ZrzyQsqglxxDO> zrzyQsqglxxList;

    @ApiModelProperty(value="排污权关联信息")
    private List<ZrzyPwqglxxDO> zrzyPwqglxxList;

    @ApiModelProperty(value="自然资源自然状况信息")
    private List<ZrzyZrzkVO> zrzyZrzkList;


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

    public List<ZrzyGggzglxxDO> getZrzyGggzglxxList() {
        return zrzyGggzglxxList;
    }

    public void setZrzyGggzglxxList(List<ZrzyGggzglxxDO> zrzyGggzglxxList) {
        this.zrzyGggzglxxList = zrzyGggzglxxList;
    }

    public List<ZrzyBdcqlglxxDO> getZrzyBdcqlglxxList() {
        return zrzyBdcqlglxxList;
    }

    public void setZrzyBdcqlglxxList(List<ZrzyBdcqlglxxDO> zrzyBdcqlglxxList) {
        this.zrzyBdcqlglxxList = zrzyBdcqlglxxList;
    }

    public List<ZrzyKyqglxxDO> getZrzyKyqglxxList() {
        return zrzyKyqglxxList;
    }

    public void setZrzyKyqglxxList(List<ZrzyKyqglxxDO> zrzyKyqglxxList) {
        this.zrzyKyqglxxList = zrzyKyqglxxList;
    }

    public List<ZrzyQsqglxxDO> getZrzyQsqglxxList() {
        return zrzyQsqglxxList;
    }

    public void setZrzyQsqglxxList(List<ZrzyQsqglxxDO> zrzyQsqglxxList) {
        this.zrzyQsqglxxList = zrzyQsqglxxList;
    }

    public List<ZrzyPwqglxxDO> getZrzyPwqglxxList() {
        return zrzyPwqglxxList;
    }

    public void setZrzyPwqglxxList(List<ZrzyPwqglxxDO> zrzyPwqglxxList) {
        this.zrzyPwqglxxList = zrzyPwqglxxList;
    }

    public List<ZrzyZrzkVO> getZrzyZrzkList() {
        return zrzyZrzkList;
    }

    public void setZrzyZrzkList(List<ZrzyZrzkVO> zrzyZrzkList) {
        this.zrzyZrzkList = zrzyZrzkList;
    }
}
