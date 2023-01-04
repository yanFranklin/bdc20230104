package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2019/2/28
 * @description 多库查询时接口参数
 */
public class BdcKkcxListQO{

    @ApiModelProperty("数据源查询记录个数")
    private Integer sourceSize;

    @ApiModelProperty("开始查询行")
    private Integer startNum;

    @ApiModelProperty("为true只查询记录数")
    private boolean queryTotal;

    public Integer getSourceSize() {
        return sourceSize;
    }

    public void setSourceSize(Integer sourceSize) {
        this.sourceSize = sourceSize;
    }

    public Integer getStartNum() {
        return startNum;
    }

    public void setStartNum(Integer startNum) {
        this.startNum = startNum;
    }

    public boolean isQueryTotal() {
        return queryTotal;
    }

    public void setQueryTotal(boolean queryTotal) {
        this.queryTotal = queryTotal;
    }
}
