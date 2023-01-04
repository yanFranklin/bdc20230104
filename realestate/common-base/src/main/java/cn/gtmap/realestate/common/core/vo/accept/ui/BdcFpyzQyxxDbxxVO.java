package cn.gtmap.realestate.common.core.vo.accept.ui;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2020/9/27
 * @description 分屏验证-企业对比信息
 */
@ApiModel(value = "BdcFpyzQyxxDbxxVO", description = "分屏验证-企业对比信息")
public class BdcFpyzQyxxDbxxVO {

    @ApiModelProperty(value = "权利人企业对比信息")
    private List<BdcFpyzQyxxMxVO> qlrFpyzQyxxMxVOList;

    @ApiModelProperty(value = "义务人企业对比信息")
    private List<BdcFpyzQyxxMxVO> ywrFpyzQyxxMxVOList;

    public List<BdcFpyzQyxxMxVO> getQlrFpyzQyxxMxVOList() {
        return qlrFpyzQyxxMxVOList;
    }

    public void setQlrFpyzQyxxMxVOList(List<BdcFpyzQyxxMxVO> qlrFpyzQyxxMxVOList) {
        this.qlrFpyzQyxxMxVOList = qlrFpyzQyxxMxVOList;
    }

    public List<BdcFpyzQyxxMxVO> getYwrFpyzQyxxMxVOList() {
        return ywrFpyzQyxxMxVOList;
    }

    public void setYwrFpyzQyxxMxVOList(List<BdcFpyzQyxxMxVO> ywrFpyzQyxxMxVOList) {
        this.ywrFpyzQyxxMxVOList = ywrFpyzQyxxMxVOList;
    }
}
