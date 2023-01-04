package cn.gtmap.realestate.common.core.domain.natural;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author <a href="mailto:liaoxiang@gtmap.cn">liaoxiang</a>
 * @description 自然资源草原
 */
@Table(name="ZRZY_CY")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@Clazz", defaultImpl = ZrzyCyDO.class)
@ApiModel(value = "ZrzyCyDO",description = "自然资源草原")
public class ZrzyCyDO implements ZrzyZrzk{

  private static final long serialVersionUID = 1L;

  @Id
  @ApiModelProperty(value="状况信息ID")
  private String zkxxid;

  @ApiModelProperty(value="项目ID")
  private String xmid;

  @ApiModelProperty(value="自然资源单元号")
  private String zrzydjdyh;

  @ApiModelProperty(value="草地类型")
  private String cdlx;

  @ApiModelProperty(value="包含图斑数量")
  private String bhtbsl;

  @ApiModelProperty(value="国有面积")
  private Double gymj;

  @ApiModelProperty(value="集体面积")
  private Double jtmj;

  @ApiModelProperty(value="争议区面积")
  private Double zyqmj;

  @ApiModelProperty(value="面积")
  private Double mj;

  @ApiModelProperty(value="草原类型")
  private String cylx;

  @ApiModelProperty(value="草原质量等级")
  private String cyzldj;


  public String getZkxxid() {
    return zkxxid;
  }

  public void setZkxxid(String zkxxid) {
    this.zkxxid = zkxxid;
  }

  public String getXmid() {
    return xmid;
  }

  public void setXmid(String xmid) {
    this.xmid = xmid;
  }

  public String getZrzydjdyh() {
    return zrzydjdyh;
  }

  public void setZrzydjdyh(String zrzydjdyh) {
    this.zrzydjdyh = zrzydjdyh;
  }

  public String getCdlx() {
    return cdlx;
  }

  public void setCdlx(String cdlx) {
    this.cdlx = cdlx;
  }

  public String getBhtbsl() {
    return bhtbsl;
  }

  public void setBhtbsl(String bhtbsl) {
    this.bhtbsl = bhtbsl;
  }

  public Double getGymj() {
    return gymj;
  }

  public void setGymj(Double gymj) {
    this.gymj = gymj;
  }

  public Double getJtmj() {
    return jtmj;
  }

  public void setJtmj(Double jtmj) {
    this.jtmj = jtmj;
  }

  public Double getZyqmj() {
    return zyqmj;
  }

  public void setZyqmj(Double zyqmj) {
    this.zyqmj = zyqmj;
  }

  public Double getMj() {
    return mj;
  }

  public void setMj(Double mj) {
    this.mj = mj;
  }

  public String getCylx() {
    return cylx;
  }

  public void setCylx(String cylx) {
    this.cylx = cylx;
  }

  public String getCyzldj() {
    return cyzldj;
  }

  public void setCyzldj(String cyzldj) {
    this.cyzldj = cyzldj;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("ZrzyCyDO{");
    sb.append("zkxxid='").append(zkxxid).append('\'');
    sb.append(", xmid='").append(xmid).append('\'');
    sb.append(", zrzydjdyh='").append(zrzydjdyh).append('\'');
    sb.append(", cdlx='").append(cdlx).append('\'');
    sb.append(", bhtbsl='").append(bhtbsl).append('\'');
    sb.append(", gymj=").append(gymj);
    sb.append(", jtmj=").append(jtmj);
    sb.append(", zyqmj=").append(zyqmj);
    sb.append(", mj='").append(mj).append('\'');
    sb.append(", cylx='").append(cylx).append('\'');
    sb.append(", cyzldj='").append(cyzldj).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
