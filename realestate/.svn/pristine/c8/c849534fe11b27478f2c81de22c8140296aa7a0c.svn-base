package cn.gtmap.realestate.common.core.dto.building;

import cn.gtmap.realestate.common.core.domain.building.FwXmxxDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangzijie@gtmap.cn">wangzijie</a>
 * @version 1.0  2018-12-05
 * @description 房屋类型变更请求实体
 */
@ApiModel(value = "FwlxBgRequestDTO", description = "房屋类型变更请求实体")
public class FwlxBgRequestDTO {

    /**
     * 逻辑幢主键
     */
    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;

    /**
     * 变更编号
     */
    @ApiModelProperty(value = "变更编号")
    private String bgbh;

    /**
     * 原房屋类型
     */
    @ApiModelProperty(value = "原房屋类型")
    private String yfwlx;

    /**
     * 现房屋类型
     */
    @ApiModelProperty(value = "现房屋类型")
    private String fwlx;

    /**
     * 挂接项目的主键
     */
    @ApiModelProperty(value = "挂接项目的主键")
    private String fwXmxxIndex;

    @ApiModelProperty(value = "项目下的所有逻辑幢都变更")
    private boolean changeAllXmLjz;

    /**
     * 新增项目信息
     */
    private FwXmxxDO fwXmxxDO;

    /**
     * 权籍管理代码
     */
    private String qjgldm;

    public FwXmxxDO getFwXmxxDO() {
        return fwXmxxDO;
    }

    public void setFwXmxxDO(FwXmxxDO fwXmxxDO) {
        this.fwXmxxDO = fwXmxxDO;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public String getBgbh() {
        return bgbh;
    }

    public void setBgbh(String bgbh) {
        this.bgbh = bgbh;
    }

    public String getYfwlx() {
        return yfwlx;
    }

    public void setYfwlx(String yfwlx) {
        this.yfwlx = yfwlx;
    }

    public String getFwlx() {
        return fwlx;
    }

    public void setFwlx(String fwlx) {
        this.fwlx = fwlx;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("FwlxBgRequestDTO{");
        sb.append("fwDcbIndex='").append(fwDcbIndex).append('\'');
        sb.append(", bgbh='").append(bgbh).append('\'');
        sb.append(", yfwlx='").append(yfwlx).append('\'');
        sb.append(", fwlx='").append(fwlx).append('\'');
        sb.append(", fwXmxxDO=").append(fwXmxxDO);
        sb.append('}');
        return sb.toString();
    }

    public String getFwXmxxIndex() {
        return fwXmxxIndex;
    }

    public void setFwXmxxIndex(String fwXmxxIndex) {
        this.fwXmxxIndex = fwXmxxIndex;
    }

    public boolean isChangeAllXmLjz() {
        return changeAllXmLjz;
    }

    public void setChangeAllXmLjz(boolean changeAllXmLjz) {
        this.changeAllXmLjz = changeAllXmLjz;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }
}
