package cn.gtmap.realestate.common.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
 /**
  * @author <a href="mailto:songjiawei@gtmap.cn">songjiawei</a>
  * @description 抵押物清单
  */
@Table(
        name = "BDC_DYWQD"
)
public class BdcDywqdDO {
    @Id
    /**抵押物ID*/
    @ApiModelProperty(value = "抵押物ID")
    private String dywid;
     /**工作流实例ID*/
     @ApiModelProperty(value = "工作流实例ID")
     private String gzlslid;
     /**不动产单元号*/
     @ApiModelProperty(value = "不动产单元号")
     private String bdcdyh;
     /**不动产单元编号*/
     @ApiModelProperty(value = "不动产单元编号")
     private String bdcdybh;
     /**坐落*/
     @ApiModelProperty(value = "坐落")
     private String zl;
     /**土地抵押面积*/
     @ApiModelProperty(value = "土地抵押面积")
     private Double tddymj;
     /**房屋抵押面积*/
     @ApiModelProperty(value = "房屋抵押面积")
     private Double fwdymj;
     /**定着物用途*/
     @ApiModelProperty(value = "定着物用途")
     private Integer dzwyt;
     /**不动产权证号*/
     @ApiModelProperty(value = "不动产权证")
     private String bdcqzh;

    public String getDywid() {
        return dywid;
    }

    public void setDywid(String dywid) {
        this.dywid = dywid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdybh() {
        return bdcdybh;
    }

    public void setBdcdybh(String bdcdybh) {
        this.bdcdybh = bdcdybh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
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

    public Integer getDzwyt() {
        return dzwyt;
    }

    public void setDzwyt(Integer dzwyt) {
        this.dzwyt = dzwyt;
    }

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

     @Override
     public String toString() {
         return "BdcDywqdDO{" +
                 "dywid='" + dywid + '\'' +
                 ", gzlslid='" + gzlslid + '\'' +
                 ", bdcdyh='" + bdcdyh + '\'' +
                 ", bdcdybh='" + bdcdybh + '\'' +
                 ", zl='" + zl + '\'' +
                 ", tddymj=" + tddymj +
                 ", fwdymj=" + fwdymj +
                 ", dzwyt=" + dzwyt +
                 ", bdcqzh='" + bdcqzh + '\'' +
                 '}';
     }
 }
