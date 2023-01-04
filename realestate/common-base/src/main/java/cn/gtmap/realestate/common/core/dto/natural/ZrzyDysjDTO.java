package cn.gtmap.realestate.common.core.dto.natural;

import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

/**
 * @author <a href ="mailto:songhaowen@gtmap.cn">songhaowen</a>
 * @version 1.3, 2019/1/17
 * @description 打印数据
 */
public class ZrzyDysjDTO {
    /**
     * 打印类型
     */
    @ApiModelProperty(value = "打印类型")
    private String dylx;
    /**
     * 打印主表数据
     */
    @ApiModelProperty(value = "打印主表数据")
    private String dysj;
    /**
     * 打印子表数据
     */
    @ApiModelProperty(value = "打印子表数据")
    private String dyzbsj;
    /**
     * 打印字段（xml配置数据）
     */
    @ApiModelProperty(value = "打印字段")
    private String dyzd;

    @ApiModelProperty(value = "打印参数")
    private Map paramMap;

    public String getDylx() {
        return dylx;
    }

    public void setDylx(String dylx) {
        this.dylx = dylx;
    }

    public String getDysj() {
        return dysj;
    }

    public void setDysj(String dysj) {
        this.dysj = dysj;
    }

    public String getDyzbsj() {
        return dyzbsj;
    }

    public void setDyzbsj(String dyzbsj) {
        this.dyzbsj = dyzbsj;
    }

    public String getDyzd() {
        return dyzd;
    }

    public void setDyzd(String dyzd) {
        this.dyzd = dyzd;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    @Override
    public String toString() {
        return "BdcDysjDTO{" +
                "dylx='" + dylx + '\'' +
                ", dysj='" + dysj + '\'' +
                ", dyzbsj='" + dyzbsj + '\'' +
                ", dyzd='" + dyzd + '\'' +
                ", paramMap=" + paramMap +
                '}';
    }
}
