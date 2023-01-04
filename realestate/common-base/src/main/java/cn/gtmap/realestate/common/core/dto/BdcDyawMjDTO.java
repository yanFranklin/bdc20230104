package cn.gtmap.realestate.common.core.dto;

/**
 * *
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/9/30
 * @description 存储抵押物面积的DTO实体
 */
public class BdcDyawMjDTO {
    /**
     * 土地抵押面积
     */
    private Double tddymj;
    /**
     * 房屋抵押面积
     */
    private Double fwdymj;
    /**
     * 抵押物总面积
     */
    private Double dyawzmj;
    /**
     * 面积单位名称
     */
    private String mjdwmc;

    public BdcDyawMjDTO(Double fwdymj, Double tddymj) {
        this.fwdymj = fwdymj;
        this.tddymj = tddymj;
    }


    public Double getTddymj() {
        return tddymj;
    }

    public void setTddymj(Double tddymj) {
        this.tddymj = tddymj;
    }

    public Double getFwdymj() {
        return fwdymj;
    }

    public void setFwdymj(Double fwdymj) {
        this.fwdymj = fwdymj;
    }

    public Double getDyawzmj() {
        return dyawzmj;
    }

    public void setDyawzmj(Double dyawzmj) {
        this.dyawzmj = dyawzmj;
    }

    public String getMjdwmc() {
        return mjdwmc;
    }

    public void setMjdwmc(String mjdwmc) {
        this.mjdwmc = mjdwmc;
    }
}
