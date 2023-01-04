package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * version 1.0
 *
 * @author <a href ="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @description
 * @date 2018/12/26
 */
@ApiModel(value = "BdcZfcxExportQO", description = "住房查询导出QO")
public class BdcZfcxExportQO{

    @ApiModelProperty("住房查询条件")
    private BdcZfcxQO zfcxQO;

    @ApiModelProperty("全选记录 0:否  1:是")
    private String exportAll;

    @ApiModelProperty("导出勾选记录")
    private List<String> xmidList;

    @ApiModelProperty("导出全选记录时，不导出的记录")
    private List<String> unExportList;

    public BdcZfcxQO getZfcxQO() {
        return zfcxQO;
    }

    public void setZfcxQO(BdcZfcxQO zfcxQO) {
        this.zfcxQO = zfcxQO;
    }

    public String getExportAll() {
        return exportAll;
    }

    public void setExportAll(String exportAll) {
        this.exportAll = exportAll;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public List<String> getUnExportList() {
        return unExportList;
    }

    public void setUnExportList(List<String> unExportList) {
        this.unExportList = unExportList;
    }
}
