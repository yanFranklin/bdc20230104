package cn.gtmap.realestate.common.core.dto.init.znsh;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0  2022/4/29
 * @description 数据核验信息
 */
public class BdcSjjyxxDTO {


    @ApiModelProperty(value = "字段名称")
    private String zdmc;

    @ApiModelProperty(value = "原值")
    private String sourceVal;

    @ApiModelProperty(value = "数据对比情况")
    private List<BdcSjjybdDTO> bdcSjjyxxbdList;

    public String getZdmc() {
        return zdmc;
    }

    public void setZdmc(String zdmc) {
        this.zdmc = zdmc;
    }

    public String getSourceVal() {
        return sourceVal;
    }

    public void setSourceVal(String sourceVal) {
        this.sourceVal = sourceVal;
    }

    public List<BdcSjjybdDTO> getBdcSjjyxxbdList() {
        return bdcSjjyxxbdList;
    }

    public void setBdcSjjyxxbdList(List<BdcSjjybdDTO> bdcSjjyxxbdList) {
        this.bdcSjjyxxbdList = bdcSjjyxxbdList;
    }
}
