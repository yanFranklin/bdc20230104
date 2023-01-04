package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.dto.rules.BdcGzyzTsxxDTO;
import cn.gtmap.realestate.common.core.dto.rules.BdcLwxxDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:sunchao@gtmap.cn">sunchao</a>
 * @version 1.0, 2018/11/17
 * @description 不动产受理验证
 */
@ApiModel(value = "BdcSLyz", description = "不动产受理验证")
public class BdcSLyz implements Serializable {
    private static final long serialVersionUID = -8666028651164264093L;
    @ApiModelProperty(value = "不动产例外信息")
    List<BdcLwxxDTO> bdcLwxxDTOList;
    @ApiModelProperty(value = "不动产规则验证信息")
    List<BdcGzyzTsxxDTO> bdcGzyzTsxxDTOList;

    public List<BdcLwxxDTO> getBdcLwxxDTOList() {
        return bdcLwxxDTOList;
    }

    public void setBdcLwxxDTOList(List<BdcLwxxDTO> bdcLwxxDTOList) {
        this.bdcLwxxDTOList = bdcLwxxDTOList;
    }

    public List<BdcGzyzTsxxDTO> getBdcGzyzTsxxDTOList() {
        return bdcGzyzTsxxDTOList;
    }

    public void setBdcGzyzTsxxDTOList(List<BdcGzyzTsxxDTO> bdcGzyzTsxxDTOList) {
        this.bdcGzyzTsxxDTOList = bdcGzyzTsxxDTOList;
    }

    @Override
    public String toString() {
        return "BdcSLyz{" +
                "bdcLwxxDTOList=" + bdcLwxxDTOList +
                ", bdcGzyzTsxxDTOList=" + bdcGzyzTsxxDTOList +
                '}';
    }
}
