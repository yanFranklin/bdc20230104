package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwFcqlrDO;
import cn.gtmap.realestate.common.core.domain.building.FwHsDO;
import cn.gtmap.realestate.common.core.domain.building.FwYchsDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2018-11-28
 * @description 户室拆分请求实体
 */
@ApiModel(value = "FwhsBgRequestDTO", description = "户室拆分请求实体")
public class FwhsBgRequestDTO {

    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;

    /**
     * 原房屋主键
     */
    @ApiModelProperty(value = "原房屋主键")
    private List<String> yFwHsIndexList;

    /**
     * 拆分后的 房屋户室List
     */
    @ApiModelProperty(value = "拆分后房屋户室List")
    private List<FwHsDO> fwHsList;

    /**
     * 变更后房屋权利人信息
     */
    @ApiModelProperty(value = "变更后房屋权利人信息")
    private List<FwFcqlrDO> fwFcqlrDOList;

    @ApiModelProperty(value = "预测户室list")
    private List<FwYchsDO> fwYchsDOList;

    @ApiModelProperty(value = "实测户室还是预测户室")
    private String hslx;

    private List<String> zhsList;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public List<String> getZhsList() {
        return zhsList;
    }

    public void setZhsList(List<String> zhsList) {
        this.zhsList = zhsList;
    }

    public List<FwFcqlrDO> getFwFcqlrDOList() {
        return fwFcqlrDOList;
    }

    public void setFwFcqlrDOList(List<FwFcqlrDO> fwFcqlrDOList) {
        this.fwFcqlrDOList = fwFcqlrDOList;
    }

    public List<String> getyFwHsIndexList() {
        return yFwHsIndexList;
    }

    public void setyFwHsIndexList(List<String> yFwHsIndexList) {
        this.yFwHsIndexList = yFwHsIndexList;
    }

    public List<FwHsDO> getFwHsList() {
        return fwHsList;
    }

    public void setFwHsList(List<FwHsDO> fwHsList) {
        this.fwHsList = fwHsList;
    }

    public String getHslx() {
        return hslx;
    }

    public void setHslx(String hslx) {
        this.hslx = hslx;
    }

    public List<FwYchsDO> getFwYchsDOList() {
        return fwYchsDOList;
    }

    public void setFwYchsDOList(List<FwYchsDO> fwYchsDOList) {
        this.fwYchsDOList = fwYchsDOList;
    }

    @Override
    public String toString() {
        return "FwhsBgRequestDTO{" +
                "bgbh='" + bgbh + '\'' +
                ", yFwHsIndexList=" + yFwHsIndexList +
                ", fwHsList=" + fwHsList +
                ", fwFcqlrDOList=" + fwFcqlrDOList +
                ", fwYchsDOList=" + fwYchsDOList +
                ", hslx='" + hslx + '\'' +
                ", zhsList=" + zhsList +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
