package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import cn.gtmap.realestate.common.core.domain.BdcSdqghDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/1/13
 * @description 水电气信息
 */
public class BdcSdqxxDTO {

    @ApiModelProperty(value = "水电气信息")
    private List<BdcSdqghDO> bdcSdqghDOList;

    @ApiModelProperty(value = "权利人信息")
    private List<BdcQlrDO> bdcQlrList;

    @ApiModelProperty(value = "义务人信息")
    private List<BdcQlrDO> bdcYwrList;

    @ApiModelProperty(value = "坐落")
    private String zl;

    public List<BdcSdqghDO> getBdcSdqghDOList() {
        return bdcSdqghDOList;
    }

    public void setBdcSdqghDOList(List<BdcSdqghDO> bdcSdqghDOList) {
        this.bdcSdqghDOList = bdcSdqghDOList;
    }

    public List<BdcQlrDO> getBdcQlrList() {
        return bdcQlrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
    }

    public List<BdcQlrDO> getBdcYwrList() {
        return bdcYwrList;
    }

    public void setBdcYwrList(List<BdcQlrDO> bdcYwrList) {
        this.bdcYwrList = bdcYwrList;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }
}
