package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2020/1/21
 * @description 抵押统计查询QO
 */
@Api(value = "DyaTjQO", description = "抵押统计查询QO")
public class DyaTjQO {
    @ApiModelProperty("部门名称")
    private String bmmc;
    @ApiModelProperty("部门名称List")
    private List<String> bmmcList;
    @ApiModelProperty("查询开始时间")
    private Date cxkssj;
    @ApiModelProperty("查询结束时间")
    private Date cxjssj;
    @ApiModelProperty("工作流定义ID集合")
    private List<String> gzldyidList;

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    public Date getCxkssj() {
        return cxkssj;
    }

    public void setCxkssj(Date cxkssj) {
        this.cxkssj = cxkssj;
    }

    public List<String> getBmmcList() {
        return bmmcList;
    }

    public void setBmmcList(List<String> bmmcList) {
        this.bmmcList = bmmcList;
    }

    public Date getCxjssj() {
        return cxjssj;
    }

    public void setCxjssj(Date cxjssj) {
        this.cxjssj = cxjssj;
    }

    public List<String> getGzldyidList() {
        return gzldyidList;
    }

    public void setGzldyidList(List<String> gzldyidList) {
        this.gzldyidList = gzldyidList;
    }

    @Override
    public String toString() {
        return "DyaTjQO{" +
                "bmmc='" + bmmc + '\'' +
                ", bmmcList=" + bmmcList +
                ", cxkssj=" + cxkssj +
                ", cxjssj=" + cxjssj +
                ", gzldyidList=" + gzldyidList +
                '}';
    }
}
