package cn.gtmap.realestate.etl.core.domian.nantong;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/*
    审核信息表
 */
@Table(name = "bj_inf_apply_process")
public class BjInfApplyProcess {
    @Id
    @ApiModelProperty(value = "唯一标识")
    private String id;
    @ApiModelProperty(value = "唯一办件编号")
    private String internalNo;
    @ApiModelProperty(value = "序号")
    private Integer xh;
    @ApiModelProperty(value = "节点名称")
    private String tacheName;
    @ApiModelProperty(value = "节点结束时间")
    private Date endTime;
    @ApiModelProperty(value = "节点开始时间")
    private Date createDate;
    @ApiModelProperty(value = "办理人员姓名")
    private String userName;
    @ApiModelProperty(value = "操作类型")
    private String eventName;
    @ApiModelProperty(value = "处理意见")
    private String note;
    @ApiModelProperty(value = "操作时间")
    private Date processDate;
    @ApiModelProperty(value = "区域代码")
    private String qydm;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternalNo() {
        return internalNo;
    }

    public void setInternalNo(String internalNo) {
        this.internalNo = internalNo;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getTacheName() {
        return tacheName;
    }

    public void setTacheName(String tacheName) {
        this.tacheName = tacheName;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getProcessDate() {
        return processDate;
    }

    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    public String getQydm() {
        return qydm;
    }

    public void setQydm(String qydm) {
        this.qydm = qydm;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }
}
