package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @version 1.0, 2019/9/4
 * @description 单元合计值
 */
public class BdcdySumDTO {

    /**
     * 建筑面积
     */
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    /**
     * 土地权利面积
     */
    @ApiModelProperty(value = "土地权利面积")
    private Double tdqlmj;

    /**
     * 房屋抵押面积
     */
    @ApiModelProperty(value = "房屋抵押面积")
    private Double fwdymj;

    /**
     * 土地抵押面积
     */
    @ApiModelProperty(value = "土地抵押面积")
    private Double tddymj;

    /**
     * 不动产类型
     */
    @ApiModelProperty(value = "不动产类型")
    private int bdclx;

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    public Double getTdqlmj() {
        return tdqlmj;
    }

    public void setTdqlmj(Double tdqlmj) {
        this.tdqlmj = tdqlmj;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Double getTddymj() {
        return tddymj;
    }

    public void setTddymj(Double tddymj) {
        this.tddymj = tddymj;
    }

    public int getBdclx() {
        return bdclx;
    }

    public void setBdclx(int bdclx) {
        this.bdclx = bdclx;
    }

    public BdcdySumDTO() {
    }

    public BdcdySumDTO(Double jzmj, Double tdqlmj) {
        this.jzmj = jzmj;
        this.tdqlmj = tdqlmj;
    }
}
