package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018/12/26
 * @description
 */
public class XmxxBgRequestDTO {
    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;

    /**
     * 原项目信息主键
     */
    @ApiModelProperty(value = "原项目信息主键")
    private List<String> yFwXmxxIndexList;

    /**
     * 变更后房屋项目信息
     */
    @ApiModelProperty(value = "变更后房屋项目信息")
    private FwXmxxDO fwXmxxDO;

    /**
     * 变更后房屋权利人信息
     */
    @ApiModelProperty(value = "变更后房屋权利人信息")
    private List<FwFcqlrDO> fwFcqlrDOList;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public List<String> getyFwXmxxIndexList() {
        return yFwXmxxIndexList;
    }

    public void setyFwXmxxIndexList(List<String> yFwXmxxIndexList) {
        this.yFwXmxxIndexList = yFwXmxxIndexList;
    }

    public FwXmxxDO getFwXmxxDO() {
        return fwXmxxDO;
    }

    public void setFwXmxxDO(FwXmxxDO fwXmxxDO) {
        this.fwXmxxDO = fwXmxxDO;
    }

    public List<FwFcqlrDO> getFwFcqlrDOList() {
        return fwFcqlrDOList;
    }

    public void setFwFcqlrDOList(List<FwFcqlrDO> fwFcqlrDOList) {
        this.fwFcqlrDOList = fwFcqlrDOList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "XmxxBgRequestDTO{" +
                "bgbh='" + bgbh + '\'' +
                ", yFwXmxxIndexList=" + yFwXmxxIndexList +
                ", fwXmxxDO=" + fwXmxxDO +
                ", fwFcqlrDOList=" + fwFcqlrDOList +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}