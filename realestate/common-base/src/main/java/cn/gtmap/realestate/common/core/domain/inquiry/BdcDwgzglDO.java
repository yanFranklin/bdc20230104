package cn.gtmap.realestate.common.core.domain.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
  *
  * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
  * @version 1.0
  * @description 单位公章管理DTO
  */
@Table(name = "BDC_DWGZGL")
@ApiModel(value = "BdcDwgzglDO", description = "单位公章管理")
public class BdcDwgzglDO {

  @Id
  @ApiModelProperty(value = "主键")
  private String id;

  @ApiModelProperty(value = "单位名称")
  private String dwmc;

  @ApiModelProperty(value = "备案时间")
  private Date basj;

  @ApiModelProperty(value = "情况说明")
  private String qksm;

  @ApiModelProperty(value = "附件材料id")
  private String fjcl;

  @ApiModelProperty(value = "修改时间")
  private Date xgsj;

  @ApiModelProperty(value = "修改人员")
  private String xgry;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getDwmc() {
    return dwmc;
  }

  public void setDwmc(String dwmc) {
    this.dwmc = dwmc;
  }


  public Date getBasj() {
    return basj;
  }

  public void setBasj(Date basj) {
    this.basj = basj;
  }


  public String getQksm() {
    return qksm;
  }

  public void setQksm(String qksm) {
    this.qksm = qksm;
  }


  public String getFjcl() {
    return fjcl;
  }

  public void setFjcl(String fjcl) {
    this.fjcl = fjcl;
  }


  public Date getXgsj() {
    return xgsj;
  }

  public void setXgsj(Date xgsj) {
    this.xgsj = xgsj;
  }


  public String getXgry() {
    return xgry;
  }

  public void setXgry(String xgry) {
    this.xgry = xgry;
  }

  @Override
  public String toString() {
    return "BdcDwgzglDO{" +
            "id='" + id + '\'' +
            ", dwmc='" + dwmc + '\'' +
            ", basj=" + basj +
            ", qksm='" + qksm + '\'' +
            ", fjcl='" + fjcl + '\'' +
            ", xgsj=" + xgsj +
            ", xgry='" + xgry + '\'' +
            '}';
  }
}
