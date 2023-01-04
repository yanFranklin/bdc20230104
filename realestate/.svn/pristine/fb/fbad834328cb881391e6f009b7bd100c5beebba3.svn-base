package cn.gtmap.realestate.common.core.dto.engine;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/6
 * @description 规则子系统：组合规则验证提示信息
 */
@ApiModel(value = "BdcGzZhgzTsxxDTO", description = "组合规则验证提示信息")
public class BdcGzZhgzTsxxDTO {
    @ApiModelProperty(value = "组合标识")
    protected String zhbs;

    @ApiModelProperty(value = "组合名称")
    protected String zhmc;

    @ApiModelProperty(value = "子规则提示信息")
    protected List<BdcGzZgzTsxxDTO> zgzTsxxDTOList;


    public String getZhmc() {
        return zhmc;
    }

    public void setZhmc(String zhmc) {
        this.zhmc = zhmc;
    }

    public String getZhbs() {
        return zhbs;
    }

    public void setZhbs(String zhbs) {
        this.zhbs = zhbs;
    }

    public List<BdcGzZgzTsxxDTO> getZgzTsxxDTOList() {
        return zgzTsxxDTOList;
    }

    public void setZgzTsxxDTOList(List<BdcGzZgzTsxxDTO> zgzTsxxDTOList) {
        this.zgzTsxxDTOList = zgzTsxxDTOList;
    }

    @Override
    public String toString() {
        return "BdcGzZhgzTsxxDTO{" +
                "zhbs='" + zhbs + '\'' +
                ", zhmc='" + zhmc + '\'' +
                ", zgzTsxxDTOList=" + zgzTsxxDTOList +
                '}';
    }
}
