package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 2019/1/22
 * @description
 */
@ApiModel(value = "BdcSlSfxmDTO", description = "不动产受理收费项目类集合")
public class BdcSlSfxmsDTO implements Serializable {
    private static final long serialVersionUID = -6071917596346771008L;
    @ApiModelProperty(value = "受理收费项目信息")
    private BdcSlSfxmDO bdcSlSfxmDO;
    @ApiModelProperty(value = "项目List")
    private List<BdcXmDO> bdcXmDOList;
    @ApiModelProperty(value = "是否组合流程")
    private boolean sfzhlc;
    @ApiModelProperty(value = "是否重新计算")
    private boolean sfcxjs;
    @ApiModelProperty(value = "计算版本")
    private String version;

    public BdcSlSfxmDO getBdcSlSfxmDO() {
        return bdcSlSfxmDO;
    }

    public void setBdcSlSfxmDO(BdcSlSfxmDO bdcSlSfxmDO) {
        this.bdcSlSfxmDO = bdcSlSfxmDO;
    }

    public List<BdcXmDO> getBdcXmDOList() {
        return bdcXmDOList;
    }

    public void setBdcXmDOList(List<BdcXmDO> bdcXmDOList) {
        this.bdcXmDOList = bdcXmDOList;
    }

    public boolean getSfzhlc() {
        return sfzhlc;
    }

    public void setSfzhlc(boolean sfzhlc) {
        this.sfzhlc = sfzhlc;
    }


    public boolean getSfcxjs() {
        return sfcxjs;
    }

    public void setSfcxjs(boolean sfcxjs) {
        this.sfcxjs = sfcxjs;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmsDTO{" +
                "bdcSlSfxmDO=" + bdcSlSfxmDO +
                ", bdcXmDOList=" + bdcXmDOList +
                ", sfzhlc=" + sfzhlc +
                ", sfcxjs=" + sfcxjs +
                ", version='" + version + '\'' +
                '}';
    }
}
