package cn.gtmap.realestate.etl.core.domian.nantong;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
受理信息表
 */
@Table(name = "bj_inf_base")
public class BjInfBase {
    @Id
    @ApiModelProperty(value = "数据唯一标识,项目id")
    private String id;

    @ApiModelProperty(value = "行政区划编码")
    private String areaNo;

    @ApiModelProperty(value = "行政区划名称")
    private String areaName;

    @ApiModelProperty(value = "办件编号")
    private String internalNo;

    @ApiModelProperty(value = "项目id")
    private String internalId;

    @ApiModelProperty(value = "是否加急")
    private String ifUrgent;

    @ApiModelProperty(value = "受理时间")
    private Date slsj;

    @ApiModelProperty(value = "办结时间")
    private Date bjsj;

    @ApiModelProperty(value = "登记类型")
    private String djlx;

    @ApiModelProperty(value = "流程名称")
    private String lcmc;

    @ApiModelProperty(value = "登簿时间")
    private Date dbsj;

    @ApiModelProperty(value = "承诺期限")
    private Double cnqx;

    @ApiModelProperty(value = "案件状态")
    private String ajzt;

    @ApiModelProperty(value = "权利类型")
    private String qllx;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaNo() {
        return areaNo;
    }

    public void setAreaNo(String areaNo) {
        this.areaNo = areaNo;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public String getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(String ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    public Date getSlsj() {
        return slsj;
    }

    public void setSlsj(Date slsj) {
        this.slsj = slsj;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getLcmc() {
        return lcmc;
    }

    public void setLcmc(String lcmc) {
        this.lcmc = lcmc;
    }

    public Date getDbsj() {
        return dbsj;
    }

    public void setDbsj(Date dbsj) {
        this.dbsj = dbsj;
    }

    public Date getBjsj() {
        return bjsj;
    }

    public void setBjsj(Date bjsj) {
        this.bjsj = bjsj;
    }

    public Double getCnqx() {
        return cnqx;
    }

    public void setCnqx(Double cnqx) {
        this.cnqx = cnqx;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }
}
