package cn.gtmap.realestate.exchange.core.qo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @program: realestate
 * @description: 登簿日志查询QO
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-07-04 17:25
 **/

public class BdcDbrzQO {

    @ApiModelProperty("成功标识")
    private Integer cgbs;

    private String id;

    private String xmid;

    private String qxdm;

    private Date jrrq;

    private String slbh;

    private String bdcdyh;

    private String dbrzid;

    @ApiModelProperty("上报的时间")
    private Date accessDate;


    public Integer getCgbs() {
        return cgbs;
    }

    public void setCgbs(Integer cgbs) {
        this.cgbs = cgbs;
    }

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

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public Date getJrrq() {
        return jrrq;
    }

    public void setJrrq(Date jrrq) {
        this.jrrq = jrrq;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }


    public String getDbrzid() {
        return dbrzid;
    }

    public void setDbrzid(String dbrzid) {
        this.dbrzid = dbrzid;
    }

    public Date getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(Date accessDate) {
        this.accessDate = accessDate;
    }

    @Override
    public String toString() {
        return "BdcDbrzQO{" +
                "cgbs=" + cgbs +
                ", id='" + id + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qxdm='" + qxdm + '\'' +
                ", jrrq=" + jrrq +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", dbrzid='" + dbrzid + '\'' +
                ", accessDate=" + accessDate +
                '}';
    }
}
