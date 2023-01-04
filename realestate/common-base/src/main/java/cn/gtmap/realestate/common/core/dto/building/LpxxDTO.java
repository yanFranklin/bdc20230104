package cn.gtmap.realestate.common.core.dto.building;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 流程中楼盘资源信息展现
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-01-15 14:21
 **/
public class LpxxDTO {

    @ApiModelProperty("当前流程所有的逻辑幢数量")
    private List<FwLjzDTO> fwLjzDTOList;
    @ApiModelProperty("当前流程所有户室的规划用途种类数量")
    private Integer sumGhytZl;
    @ApiModelProperty("当前流程所有户室的终止日期种类数量")
    private Integer sumZzrqZl;
    @ApiModelProperty("当前流程所有户室的实测建筑面积和")
    private Double sumJzmj;
    @ApiModelProperty("当前流程所有户室的分摊土地面积和")
    private Double sumTdmj;
    @ApiModelProperty("当前流程所有户室的数量")
    private Integer sumFwhs;


    public List<FwLjzDTO> getFwLjzDTOList() {
        return fwLjzDTOList;
    }

    public void setFwLjzDTOList(List<FwLjzDTO> fwLjzDTOList) {
        this.fwLjzDTOList = fwLjzDTOList;
    }

    public Integer getSumGhytZl() {
        return sumGhytZl;
    }

    public void setSumGhytZl(Integer sumGhytZl) {
        this.sumGhytZl = sumGhytZl;
    }

    public Integer getSumZzrqZl() {
        return sumZzrqZl;
    }

    public void setSumZzrqZl(Integer sumZzrqZl) {
        this.sumZzrqZl = sumZzrqZl;
    }

    public Double getSumJzmj() {
        return sumJzmj;
    }

    public void setSumJzmj(Double sumJzmj) {
        this.sumJzmj = sumJzmj;
    }

    public Double getSumTdmj() {
        return sumTdmj;
    }

    public void setSumTdmj(Double sumTdmj) {
        this.sumTdmj = sumTdmj;
    }

    public Integer getSumFwhs() {
        return sumFwhs;
    }

    public void setSumFwhs(Integer sumFwhs) {
        this.sumFwhs = sumFwhs;
    }

    @Override
    public String toString() {
        return "LpxxDTO{" +
                "fwLjzDTOList=" + fwLjzDTOList +
                ", sumGhytZl=" + sumGhytZl +
                ", sumZzrqZl=" + sumZzrqZl +
                ", sumJzmj=" + sumJzmj +
                ", sumTdmj=" + sumTdmj +
                ", sumFwhs=" + sumFwhs +
                '}';
    }
}
