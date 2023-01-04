package cn.gtmap.realestate.common.core.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/7/26
 * @description 信息比对
 */
@ApiModel(value = "BdcXxbdDTO", description = "信息比对")
public class BdcXxbdDTO {

    @ApiModelProperty(value = "数据来源集合")
    private List<String> sjlyList;

    @ApiModelProperty(value = "比对字段信息")
    private List<Map<String,Object>> bdcBdsjDTOList;

    public List<String> getSjlyList() {
        return sjlyList;
    }

    public void setSjlyList(List<String> sjlyList) {
        this.sjlyList = sjlyList;
    }

    public List<Map<String,Object>> getBdcBdsjDTOList() {
        return bdcBdsjDTOList;
    }

    public void setBdcBdsjDTOList(List<Map<String,Object>> bdcBdsjDTOList) {
        this.bdcBdsjDTOList = bdcBdsjDTOList;
    }
}
