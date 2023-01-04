package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
  *
  * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
  * @description 签字信息DTO
  */
@Table(name = "BDC_QZXX")
@ApiModel(value = "BdcQzxxDO",description = "签字信息DO")
public class BdcQzxxDO {

  /**
   * id
   */
  @Id
  @ApiModelProperty(value = "id")
  private String id;

  /**
   * 项目id
   */
  @ApiModelProperty(value = "项目id")
  private String xmid;

  /**
   * 工作流实例id
   */
  @ApiModelProperty(value = "工作流实例id")
  private String gzlslid;

  /**
   * 受理编号
   */
  @ApiModelProperty(value = "受理编号")
  private String slbh;

  /**
   * 表单类型
   */
  @ApiModelProperty(value = "表单类型")
  private Integer bdlx;

  /**
   * 签字内容
   */
  @ApiModelProperty(value = "签字内容")
  private String qznr;

  /**
   * 签字人类别
   */
  @ApiModelProperty(value = "签字人类别")
  private Integer qzrlb;

  @ApiModelProperty(value = "附件id")
  private String fid;

  @ApiModelProperty(value = "顺序号")
  private Integer sxh;

  @ApiModelProperty(value = "证书id")
  private String zsid;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getXmid() {
    return xmid;
  }

  public void setXmid(String xmid) {
    this.xmid = xmid;
  }


  public String getGzlslid() {
    return gzlslid;
  }

  public void setGzlslid(String gzlslid) {
    this.gzlslid = gzlslid;
  }


  public String getSlbh() {
    return slbh;
  }

  public void setSlbh(String slbh) {
    this.slbh = slbh;
  }


  public Integer getBdlx() {
    return bdlx;
  }

  public void setBdlx(Integer bdlx) {
    this.bdlx = bdlx;
  }


  public String getQznr() {
    return qznr;
  }

  public void setQznr(String qznr) {
    this.qznr = qznr;
  }

  public Integer getQzrlb() {
    return qzrlb;
  }

  public void setQzrlb(Integer qzrlb) {
    this.qzrlb = qzrlb;
  }

  public String getFid() {
    return fid;
  }

  public void setFid(String fid) {
    this.fid = fid;
  }

  public Integer getSxh() {
    return sxh;
  }

  public void setSxh(Integer sxh) {
    this.sxh = sxh;
  }

  public String getZsid() {
    return zsid;
  }

  public void setZsid(String zsid) {
    this.zsid = zsid;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("BdcQzxxDO{");
    sb.append("id='").append(id).append('\'');
    sb.append(", xmid='").append(xmid).append('\'');
    sb.append(", gzlslid='").append(gzlslid).append('\'');
    sb.append(", slbh='").append(slbh).append('\'');
    sb.append(", bdlx=").append(bdlx);
    sb.append(", qznr='").append(qznr).append('\'');
    sb.append(", qzrlb=").append(qzrlb);
    sb.append(", fid='").append(fid).append('\'');
    sb.append(", sxh=").append(sxh);
    sb.append(", zsid='").append(zsid).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
