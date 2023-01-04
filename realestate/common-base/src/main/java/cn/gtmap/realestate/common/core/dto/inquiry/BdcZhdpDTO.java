package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2021/9/10
 * @description 综合大屏加载信息
 */
public class BdcZhdpDTO {

    @ApiModelProperty("大屏轮播切换时间")
    private Integer qhsj;

    @ApiModelProperty("综合大屏信息")
    private List<Map> zhdpDataList;

    public Integer getQhsj() {
        return qhsj;
    }

    public void setQhsj(Integer qhsj) {
        this.qhsj = qhsj;
    }

    public List<Map> getZhdpDataList() {
        return zhdpDataList;
    }

    public void setZhdpDataList(List<Map> zhdpDataList) {
        this.zhdpDataList = zhdpDataList;
    }
}
