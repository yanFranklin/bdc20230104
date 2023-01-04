package cn.gtmap.realestate.common.core.dto.init.znsh;

import cn.gtmap.realestate.common.core.domain.BdcQlrDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/5/5
 * @description 数据校验权利人比对信息
 */
public class BdcSjjyQlrbdDTO {

    @ApiModelProperty(value = "权利人信息列表")
    private List<BdcQlrDO> bdcQlrList;

    @ApiModelProperty(value = "是否一致：1：一致 0：不一致")
    private Integer sfyz;

    @ApiModelProperty(value = "数据来源")
    private String sjly;

    public List<BdcQlrDO> getBdcQlrList() {
        return bdcQlrList;
    }

    public void setBdcQlrList(List<BdcQlrDO> bdcQlrList) {
        this.bdcQlrList = bdcQlrList;
    }

    public Integer getSfyz() {
        return sfyz;
    }

    public void setSfyz(Integer sfyz) {
        this.sfyz = sfyz;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }
}
