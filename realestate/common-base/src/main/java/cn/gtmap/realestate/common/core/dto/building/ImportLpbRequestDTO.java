package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:shaoliyao@gtmap.cn">shaoliyao</a>
 * @version 1.0  2018-12-26
 * @description 逻辑幢与幢下所属的房屋户室信息
 */
@ApiModel(value = "ImportLpbRequestDTO", description = "导入楼盘表数据传递DTO")
public class ImportLpbRequestDTO {

    @ApiModelProperty(value = "覆盖已有户室信息")
    private Boolean fgyyhs;

    @NotEmpty(message = "逻辑幢主键不能为空")
    @ApiModelProperty(value = "逻辑幢主键")
    private String fwDcbIndex;

    @NotEmpty(message = "楼盘表数据不能为空")
    @ApiModelProperty(value = "楼盘表数据")
    private List<Map<String, Object>> lpbList;

    @ApiModelProperty(value = "权籍管理代码")
    private String qjgldm;

    public Boolean getFgyyhs() {
        return fgyyhs;
    }

    public void setFgyyhs(Boolean fgyyhs) {
        this.fgyyhs = fgyyhs;
    }

    public String getFwDcbIndex() {
        return fwDcbIndex;
    }

    public void setFwDcbIndex(String fwDcbIndex) {
        this.fwDcbIndex = fwDcbIndex;
    }

    public List<Map<String, Object>> getLpbList() {
        return lpbList;
    }

    public void setLpbList(List<Map<String, Object>> lpbList) {
        this.lpbList = lpbList;
    }

    public String getQjgldm() {
        return qjgldm;
    }

    public void setQjgldm(String qjgldm) {
        this.qjgldm = qjgldm;
    }

    @Override
    public String toString() {
        return "ImportLpbRequestDTO{" +
                "fgyyhs=" + fgyyhs +
                ", fwDcbIndex='" + fwDcbIndex + '\'' +
                ", lpbList=" + lpbList +
                ", qjgldm='" + qjgldm + '\'' +
                '}';
    }
}
