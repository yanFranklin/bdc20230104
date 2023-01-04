package cn.gtmap.realestate.exchange.core.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 登簿未接入数据存储
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-06-29 20:52
 **/
@Table(name = "BDC_DB_WJR")
public class BdcDbWjrDO {

    @Id
    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("受理编号")
    private String slbh;

    @ApiModelProperty("流程实例id")
    private String gzlslid;

    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("权利类型")
    private Integer qllx;

    @ApiModelProperty("处理状态0 未处理，1 已处理，重新上报")
    private Integer clzt;

    @ApiModelProperty("更新时间")
    private Date gxsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getQllx() {
        return qllx;
    }

    public void setQllx(Integer qllx) {
        this.qllx = qllx;
    }

    public Integer getClzt() {
        return clzt;
    }

    public void setClzt(Integer clzt) {
        this.clzt = clzt;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    @Override
    public String toString() {
        return "BdcDbWjrDO{" +
                "id='" + id + '\'' +
                ", slbh='" + slbh + '\'' +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", qllx=" + qllx +
                ", clzt=" + clzt +
                ", gxsj=" + gxsj +
                '}';
    }
}
