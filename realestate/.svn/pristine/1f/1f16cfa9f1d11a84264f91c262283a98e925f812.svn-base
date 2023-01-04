package cn.gtmap.realestate.common.core.qo.engine;

import cn.gtmap.realestate.common.core.dto.engine.BdcGzYzsjDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/3/13
 * @description 不动产规则验证查询参数QO定义
 *   说明：
 *   1、bdcGzYzsjDTOList、paramMap 只需要传值一个，用于封装规则验证时需要的参数数据
 */
@ApiModel(value = "BdcGzYzQO",description = "规则验证查询实体")
public class BdcGzYzQO {
    @ApiModelProperty(value = "组合标识")
    private String zhbs;

    @ApiModelProperty(value = "验证数据：支持任意参数")
    private Map<String, Object> paramMap;

    @ApiModelProperty(value = "验证数据：将常用的参数封装")
    private List<BdcGzYzsjDTO> bdcGzYzsjDTOList;

    @ApiModelProperty(value = "验证数据：任意参数批量验证")
    private List<Map<String, Object>> paramList;

    @ApiModelProperty(value = "验证人id")
    private String yzrid;

    @ApiModelProperty(value = "验证人账号")
    private String yzrzh;

    public String getYzrid() {
        return yzrid;
    }

    public void setYzrid(String yzrid) {
        this.yzrid = yzrid;
    }

    public String getYzrzh() {
        return yzrzh;
    }

    public void setYzrzh(String yzrzh) {
        this.yzrzh = yzrzh;
    }

    public String getZhbs() {
        return zhbs;
    }

    public void setZhbs(String zhbs) {
        this.zhbs = zhbs;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public List<BdcGzYzsjDTO> getBdcGzYzsjDTOList() {
        return bdcGzYzsjDTOList;
    }

    public void setBdcGzYzsjDTOList(List<BdcGzYzsjDTO> bdcGzYzsjDTOList) {
        this.bdcGzYzsjDTOList = bdcGzYzsjDTOList;
    }

    public List<Map<String, Object>> getParamList() {
        return paramList;
    }

    public void setParamList(List<Map<String, Object>> paramList) {
        this.paramList = paramList;
    }


    @Override
    public String toString() {
        return "BdcGzYzQO{" +
                "zhbs='" + zhbs + '\'' +
                ", paramMap=" + paramMap +
                ", bdcGzYzsjDTOList=" + bdcGzYzsjDTOList +
                ", paramList=" + paramList +
                ", yzrid=" + yzrid +
                ", yzrzh=" + yzrzh +
                '}';
    }
}
