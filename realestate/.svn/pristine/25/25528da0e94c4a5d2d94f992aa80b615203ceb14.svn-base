package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2020/5/25
 * @description 综合查询日志查询对象
 */
@ApiModel(value = "BdcZhcxLogQO", description = "综合查询日志查询QO")
public class BdcZhcxLogQO extends BdcLogQO {

    @ApiModelProperty(value = "导出列")
    private String exportCol;

    @ApiModelProperty(value = "行数")
    private Integer currcount;

    @ApiModelProperty(value = "页数")
    private Integer wpage;

    public String getExportCol() {
        return exportCol;
    }

    public void setExportCol(String exportCol) {
        this.exportCol = exportCol;
    }

    public Integer getCurrcount() {
        return currcount;
    }

    public void setCurrcount(Integer currcount) {
        this.currcount = currcount;
    }

    public Integer getWpage() {
        return wpage;
    }

    public void setWpage(Integer wpage) {
        this.wpage = wpage;
    }

    @Override
    public String toString() {
        return "BdcZhcxLogQO{" +
                "exportCol='" + exportCol + '\'' +
                ", currcount=" + currcount +
                ", wpage=" + wpage +
                '}';
    }
}
