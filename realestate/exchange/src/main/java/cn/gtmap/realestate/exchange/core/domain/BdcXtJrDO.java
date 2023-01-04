package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Auther 徐涛
 * @Date 2018-12-17
 * @Description
 */
@Table(name = "BDC_XT_JR")
public class BdcXtJrDO {

    @Id
    /**主键*/
    @ApiModelProperty(value = "主键")
    private String id;

    /**
     * 不动产权利类型代码
     */
    @ApiModelProperty(value = "不动产权利类型代码")
    private String bdcqllxdm;

    /**
     * 不动产权利类型名称
     */
    @ApiModelProperty(value = "不动产权利类型名称")
    private Integer bdcqllxmc;

    /**
     * 不动产登记类型代码
     */
    @ApiModelProperty(value = "不动产登记类型代码")
    private Double bdcdjlxdm;

    /**
     * 不动产登记类型名称
     */
    @ApiModelProperty(value = "不动产登记类型名称")
    private Double bdcdjlxmc;

    /**
     * 业务代码
     */
    @ApiModelProperty(value = "业务代码")
    private Double ywdm;

    /**
     * 业务服务代码
     */
    @ApiModelProperty(value = "业务服务代码")
    private Double ywfwdm;

    /**
     * 是否多幢
     */
    @ApiModelProperty(value = "是否多幢")
    private Double sfdz;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBdcqllxdm() {
        return bdcqllxdm;
    }

    public void setBdcqllxdm(String bdcqllxdm) {
        this.bdcqllxdm = bdcqllxdm;
    }

    public Integer getBdcqllxmc() {
        return bdcqllxmc;
    }

    public void setBdcqllxmc(Integer bdcqllxmc) {
        this.bdcqllxmc = bdcqllxmc;
    }

    public Double getBdcdjlxdm() {
        return bdcdjlxdm;
    }

    public void setBdcdjlxdm(Double bdcdjlxdm) {
        this.bdcdjlxdm = bdcdjlxdm;
    }

    public Double getBdcdjlxmc() {
        return bdcdjlxmc;
    }

    public void setBdcdjlxmc(Double bdcdjlxmc) {
        this.bdcdjlxmc = bdcdjlxmc;
    }

    public Double getYwdm() {
        return ywdm;
    }

    public void setYwdm(Double ywdm) {
        this.ywdm = ywdm;
    }

    public Double getYwfwdm() {
        return ywfwdm;
    }

    public void setYwfwdm(Double ywfwdm) {
        this.ywfwdm = ywfwdm;
    }

    public Double getSfdz() {
        return sfdz;
    }

    public void setSfdz(Double sfdz) {
        this.sfdz = sfdz;
    }

    @Override
    public String toString() {
        return "BdcXtJrDO{" +
                "id='" + id + '\'' +
                ", bdcqllxdm='" + bdcqllxdm + '\'' +
                ", bdcqllxmc=" + bdcqllxmc +
                ", bdcdjlxdm=" + bdcdjlxdm +
                ", bdcdjlxmc=" + bdcdjlxmc +
                ", ywdm=" + ywdm +
                ", ywfwdm=" + ywfwdm +
                ", sfdz=" + sfdz +
                '}';
    }
}
