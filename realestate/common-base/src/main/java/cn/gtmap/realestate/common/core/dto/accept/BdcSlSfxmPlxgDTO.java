package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.accept.BdcSlSfxmDO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "BdcSlSfxmPlxgDTO", description = "不动产受理收费项目批量修改DTO")
public class BdcSlSfxmPlxgDTO {

    @ApiModelProperty(value = "工作流实例ID集合")
    private List<String> gzlslidList;

    @ApiModelProperty(value = "不动产受理收费项目DO集合")
    private List<BdcSlSfxmDO> bdcSlSfxmDOList;

    @ApiModelProperty(value = "修改缴费原因")
    private String xgjfyy;

    @ApiModelProperty(value = "备注")
    private String bz;

    public List<String> getGzlslidList() {
        return gzlslidList;
    }

    public void setGzlslidList(List<String> gzlslidList) {
        this.gzlslidList = gzlslidList;
    }

    public List<BdcSlSfxmDO> getBdcSlSfxmDOList() {
        return bdcSlSfxmDOList;
    }

    public void setBdcSlSfxmDOList(List<BdcSlSfxmDO> bdcSlSfxmDOList) {
        this.bdcSlSfxmDOList = bdcSlSfxmDOList;
    }

    public String getXgjfyy() {
        return xgjfyy;
    }

    public void setXgjfyy(String xgjfyy) {
        this.xgjfyy = xgjfyy;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    @Override
    public String toString() {
        return "BdcSlSfxmPlxgDTO{" +
                "gzlslidList=" + gzlslidList +
                ", bdcSlSfxmDOList=" + bdcSlSfxmDOList +
                ", xgjfyy='" + xgjfyy + '\'' +
                ", bz='" + bz + '\'' +
                '}';
    }
}
