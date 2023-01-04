package cn.gtmap.realestate.common.core.domain.exchange;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date  2021/10/26
 * @description 登记登簿信息
 */
@Table(name = "DJF_DJ_DB")
@XmlRootElement(name = "DJF_DJ_DB")
@XmlAccessorType(XmlAccessType.NONE)
public class DjfDjDb  implements Serializable, AccessData{
  @Id
  private String ywh;
  private String ysdm;
  private String dbryxm;
  private Date dbsj;
  private String dbyj;
  private String czjg;
  private String qxdm;

  @XmlAttribute(name = "YWH")
  public String getYwh() {
    return ywh;
  }

  public void setYwh(String ywh) {
    this.ywh = ywh;
  }

  @XmlAttribute(name = "YSDM")
  public String getYsdm() {
    return ysdm;
  }

  public void setYsdm(String ysdm) {
    this.ysdm = ysdm;
  }

  @XmlAttribute(name = "DBRYXM")
  public String getDbryxm() {
    return dbryxm;
  }

  public void setDbryxm(String dbryxm) {
    this.dbryxm = dbryxm;
  }

  @XmlAttribute(name = "DBSJ")
  public Date getDbsj() {
    return dbsj;
  }

  public void setDbsj(Date dbsj) {
    this.dbsj = dbsj;
  }



  @XmlAttribute(name = "DBYJ")
  public String getDbyj() {
    return dbyj;
  }

  public void setDbyj(String dbyj) {
    this.dbyj = dbyj;
  }

  @XmlAttribute(name = "CZJG")
  public String getCzjg() {
    return czjg;
  }

  public void setCzjg(String czjg) {
    this.czjg = czjg;
  }

  @XmlAttribute(name = "QXDM")
  public String getQxdm() {
    return qxdm;
  }

  public void setQxdm(String qxdm) {
    this.qxdm = qxdm;
  }

  @Override
  public String toString() {
    return "DjfDjDb{" +
            "ywh='" + ywh + '\'' +
            ", ysdm='" + ysdm + '\'' +
            ", dbryxm='" + dbryxm + '\'' +
            ", dbsj=" + dbsj +
            ", dbyj='" + dbyj + '\'' +
            ", czjg='" + czjg + '\'' +
            ", qxdm='" + qxdm + '\'' +
            '}';
  }
}
