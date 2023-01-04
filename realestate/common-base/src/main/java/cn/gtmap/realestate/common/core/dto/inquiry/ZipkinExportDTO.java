package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/07/20
 * @description 存放最耗时服务用于导出数据
 */
public class ZipkinExportDTO {
    @ApiModelProperty(value = "文件名称")
    String filedName;

    @ApiModelProperty(value = "表头名称")
    List<String> colNameList;

    @ApiModelProperty(value = "行数据")
    List<List<String>> valList;

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public List<String> getColNameList() {
        return colNameList;
    }

    public void setColNameList(List<String> colNameList) {
        this.colNameList = colNameList;
    }

    public List<List<String>> getValList() {
        return valList;
    }

    public void setValList(List<List<String>> valList) {
        this.valList = valList;
    }
}
