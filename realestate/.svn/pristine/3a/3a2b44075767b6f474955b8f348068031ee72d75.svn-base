package cn.gtmap.realestate.common.core.dto.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.StringJoiner;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @description 不动产核定户室份额DTO
 */
public class BdcHdhsfeDTO {

    @ApiModelProperty(value = "宗地上所有现势户室建筑面积总和")
    private Double totalJzmj;
    @ApiModelProperty(value = "项目ID")
    private String xmid;
    @ApiModelProperty(value = "建筑面积")
    private Double jzmj;

    public BdcHdhsfeDTO() {
    }

    public BdcHdhsfeDTO(Double totalJzmj, String xmid, Double jzmj) {
        this.totalJzmj = totalJzmj;
        this.xmid = xmid;
        this.jzmj = jzmj;
    }

    public Double getTotalJzmj() {
        return totalJzmj;
    }

    public void setTotalJzmj(Double totalJzmj) {
        this.totalJzmj = totalJzmj;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Double getJzmj() {
        return jzmj;
    }

    public void setJzmj(Double jzmj) {
        this.jzmj = jzmj;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BdcHdhsfeDTO.class.getSimpleName() + "[", "]")
                .add("totalJzmj=" + totalJzmj)
                .add("xmid='" + xmid + "'")
                .add("jzmj=" + jzmj)
                .toString();
    }
}
