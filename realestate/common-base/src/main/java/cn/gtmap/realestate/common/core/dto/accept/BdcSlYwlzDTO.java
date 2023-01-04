package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlYwlzDO;
import cn.gtmap.realestate.common.core.vo.register.ui.BdcShxxVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @program: realestate
 * @description: 受理业务流转ui交互信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-09-22 16:53
 **/
public class BdcSlYwlzDTO {
    @ApiModelProperty("基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO = new BdcSlJbxxDO();

    @ApiModelProperty("受理项目信息")
    private BdcSlXmDO bdcSlXmDO = new BdcSlXmDO();

    @ApiModelProperty("业务流转信息")
    private BdcSlYwlzDO bdcSlYwlzDO = new BdcSlYwlzDO();

    @ApiModelProperty("受理审核信息")
    private BdcShxxVO slShxxVO = new BdcShxxVO();

    @ApiModelProperty("公正审核信息")
    private BdcShxxVO gzShxxVO = new BdcShxxVO();

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }

    public BdcSlXmDO getBdcSlXmDO() {
        return bdcSlXmDO;
    }

    public void setBdcSlXmDO(BdcSlXmDO bdcSlXmDO) {
        this.bdcSlXmDO = bdcSlXmDO;
    }

    public BdcSlYwlzDO getBdcSlYwlzDO() {
        return bdcSlYwlzDO;
    }

    public void setBdcSlYwlzDO(BdcSlYwlzDO bdcSlYwlzDO) {
        this.bdcSlYwlzDO = bdcSlYwlzDO;
    }

    public BdcShxxVO getSlShxxVO() {
        return slShxxVO;
    }

    public void setSlShxxVO(BdcShxxVO slShxxVO) {
        this.slShxxVO = slShxxVO;
    }

    public BdcShxxVO getGzShxxVO() {
        return gzShxxVO;
    }

    public void setGzShxxVO(BdcShxxVO gzShxxVO) {
        this.gzShxxVO = gzShxxVO;
    }

    @Override
    public String toString() {
        return "BdcSlYwlzDTO{" +
                "bdcSlJbxxDO=" + bdcSlJbxxDO +
                ", bdcSlXmDO=" + bdcSlXmDO +
                ", bdcSlYwlzDO=" + bdcSlYwlzDO +
                ", slShxxVO=" + slShxxVO +
                ", gzShxxVO=" + gzShxxVO +
                '}';
    }
}
