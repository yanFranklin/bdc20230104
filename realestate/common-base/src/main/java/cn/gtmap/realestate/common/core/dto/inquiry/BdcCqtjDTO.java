package cn.gtmap.realestate.common.core.dto.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @Author: <a href="mailto:sunyuzhuang@gtmap.cn">sunyuzhaung</a>
 * @version: 2022/06/28/14:36
 * @Description: 超期统计DTO
 */
@Api(value = "BdcCqtjDTO", description = "不动产超期统计DTO")
public class BdcCqtjDTO {

    @ApiModelProperty(value = "流程超期状态  0 未超期  1 已超期")
    private Integer procTimeoutStatus;
    @ApiModelProperty(value = "流程开始时间")
    private Long procStartTimeInLong;
    @ApiModelProperty(value = "登记原因")
    private String djyy;
    @ApiModelProperty(value = "节点间总办理时长")
    private Long customStatisticTime;
    @ApiModelProperty(value = "流程计算超期时间类型 D H 根据天或小时")
    private String procDueType;
    @ApiModelProperty(value = "流程状态  1 运行中 2 结束 3 挂起  4 废弃")
    private Integer procStatus;
    @ApiModelProperty(value = "业务扩展表数据")
    private String isxn;
    @ApiModelProperty(value = "启动人员行政区比那么")
    private String startRegionCode;
    @ApiModelProperty(value = "义务人")
    private String ywr;
    @ApiModelProperty(value = "启动人员部门id")
    private List<String>  startUserDepList;
    @ApiModelProperty(value = "业务扩展表字段")
    private String bdcdyh;
    @ApiModelProperty(value = "主键id")
    private String id;
    @ApiModelProperty(value = "总锁定时长")
    private String sumLockTime;
    @ApiModelProperty(value = "流程定义key")
    private String  procDefKey;
    @ApiModelProperty(value = "流程开始时间")
    private String  procStartTime;
    @ApiModelProperty(value = "审批来源")
    private String  sply;
    @ApiModelProperty(value = "业务扩展字段")
    private String dyzt;
    @ApiModelProperty(value = "流程总办理时长")
    private String statisticsTime;
    @ApiModelProperty(value = "流程实例id")
    private String processInstanceId;
    @ApiModelProperty(value = "流程到期时间")
    private String procDueTime;
    @ApiModelProperty(value = "启动人员部门")
    private String startUserDepId;
    @ApiModelProperty(value = "结束时间戳")
    private Long endTimeInLong;
    @ApiModelProperty(value = "全部统计时长")
    private String fullStatisticsTime;
    @ApiModelProperty(value = "优先级")
    private Integer priority;
    @ApiModelProperty(value = "流程定义id")
    private String procDefId;
    @ApiModelProperty(value = "坐落")
    private String zl;
    @ApiModelProperty(value = "部门人员部门")
    private String startUserDep;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "流程超期限制")
    private String procDueLimit;
    @ApiModelProperty(value = "流程定义名称")
    private String procDefName;
    @ApiModelProperty(value = "流程结束时间戳")
    private Long procEndTimeInLong;
    @ApiModelProperty(value = "流程实例名称")
    private String processInstanceName;
    @ApiModelProperty(value = "流程实例id")
    private String procInsId;
    @ApiModelProperty(value = "最后计算时间")
    private String reckonTime;
    @ApiModelProperty(value = "流程到期时间时间戳")
    private Long procDueTimeInLong;
    @ApiModelProperty(value = "流程业务分类")
    private String categoryName;
    @ApiModelProperty(value = "流程业务分类")
    private String procTimeoutCount;
    @ApiModelProperty(value = "启动人员userName")
    private String startUserId;
    @ApiModelProperty(value = "启动节点id")
    private String startActivityId;//
    @ApiModelProperty(value = "启动人员名称")
    private String startUserName;
    @ApiModelProperty(value = "流程定义Key")
    private String processKey;
    @ApiModelProperty(value = "流程开始时间")
    private String startTime;
    @ApiModelProperty(value = "流程开始时间时间戳")
    private Long startTimeInLong;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "关联工作日id")
    private String workId;
    @ApiModelProperty(value = "启动人员部门名称")
    private String startUserDepName;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
    @ApiModelProperty(value = "流程业务分类")
    private String category;
    @ApiModelProperty(value = "流程结束时间")
    private String procEndTime;

    public Integer getProcTimeoutStatus() {
        return procTimeoutStatus;
    }

    public void setProcTimeoutStatus(Integer procTimeoutStatus) {
        this.procTimeoutStatus = procTimeoutStatus;
    }

    public Long getProcStartTimeInLong() {
        return procStartTimeInLong;
    }

    public void setProcStartTimeInLong(Long procStartTimeInLong) {
        this.procStartTimeInLong = procStartTimeInLong;
    }

    public String getDjyy() {
        return djyy;
    }

    public void setDjyy(String djyy) {
        this.djyy = djyy;
    }

    public Long getCustomStatisticTime() {
        return customStatisticTime;
    }

    public void setCustomStatisticTime(Long customStatisticTime) {
        this.customStatisticTime = customStatisticTime;
    }

    public String getProcDueType() {
        return procDueType;
    }

    public void setProcDueType(String procDueType) {
        this.procDueType = procDueType;
    }

    public Integer getProcStatus() {
        return procStatus;
    }

    public void setProcStatus(Integer procStatus) {
        this.procStatus = procStatus;
    }

    public String getIsxn() {
        return isxn;
    }

    public void setIsxn(String isxn) {
        this.isxn = isxn;
    }

    public String getStartRegionCode() {
        return startRegionCode;
    }

    public void setStartRegionCode(String startRegionCode) {
        this.startRegionCode = startRegionCode;
    }

    public String getYwr() {
        return ywr;
    }

    public void setYwr(String ywr) {
        this.ywr = ywr;
    }

    public List<String> getStartUserDepList() {
        return startUserDepList;
    }

    public void setStartUserDepList(List<String> startUserDepList) {
        this.startUserDepList = startUserDepList;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSumLockTime() {
        return sumLockTime;
    }

    public void setSumLockTime(String sumLockTime) {
        this.sumLockTime = sumLockTime;
    }

    public String getProcDefKey() {
        return procDefKey;
    }

    public void setProcDefKey(String procDefKey) {
        this.procDefKey = procDefKey;
    }

    public String getProcStartTime() {
        return procStartTime;
    }

    public void setProcStartTime(String procStartTime) {
        this.procStartTime = procStartTime;
    }

    public String getSply() {
        return sply;
    }

    public void setSply(String sply) {
        this.sply = sply;
    }

    public String getDyzt() {
        return dyzt;
    }

    public void setDyzt(String dyzt) {
        this.dyzt = dyzt;
    }

    public String getStatisticsTime() {
        return statisticsTime;
    }

    public void setStatisticsTime(String statisticsTime) {
        this.statisticsTime = statisticsTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcDueTime() {
        return procDueTime;
    }

    public void setProcDueTime(String procDueTime) {
        this.procDueTime = procDueTime;
    }

    public String getStartUserDepId() {
        return startUserDepId;
    }

    public void setStartUserDepId(String startUserDepId) {
        this.startUserDepId = startUserDepId;
    }

    public Long getEndTimeInLong() {
        return endTimeInLong;
    }

    public void setEndTimeInLong(Long endTimeInLong) {
        this.endTimeInLong = endTimeInLong;
    }

    public String getFullStatisticsTime() {
        return fullStatisticsTime;
    }

    public void setFullStatisticsTime(String fullStatisticsTime) {
        this.fullStatisticsTime = fullStatisticsTime;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getStartUserDep() {
        return startUserDep;
    }

    public void setStartUserDep(String startUserDep) {
        this.startUserDep = startUserDep;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getProcDueLimit() {
        return procDueLimit;
    }

    public void setProcDueLimit(String procDueLimit) {
        this.procDueLimit = procDueLimit;
    }

    public String getProcDefName() {
        return procDefName;
    }

    public void setProcDefName(String procDefName) {
        this.procDefName = procDefName;
    }

    public Long getProcEndTimeInLong() {
        return procEndTimeInLong;
    }

    public void setProcEndTimeInLong(Long procEndTimeInLong) {
        this.procEndTimeInLong = procEndTimeInLong;
    }

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }

    public String getProcInsId() {
        return procInsId;
    }

    public void setProcInsId(String procInsId) {
        this.procInsId = procInsId;
    }

    public String getReckonTime() {
        return reckonTime;
    }

    public void setReckonTime(String reckonTime) {
        this.reckonTime = reckonTime;
    }

    public Long getProcDueTimeInLong() {
        return procDueTimeInLong;
    }

    public void setProcDueTimeInLong(Long procDueTimeInLong) {
        this.procDueTimeInLong = procDueTimeInLong;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProcTimeoutCount() {
        return procTimeoutCount;
    }

    public void setProcTimeoutCount(String procTimeoutCount) {
        this.procTimeoutCount = procTimeoutCount;
    }

    public String getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(String startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartActivityId() {
        return startActivityId;
    }

    public void setStartActivityId(String startActivityId) {
        this.startActivityId = startActivityId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getStartTimeInLong() {
        return startTimeInLong;
    }

    public void setStartTimeInLong(Long startTimeInLong) {
        this.startTimeInLong = startTimeInLong;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getStartUserDepName() {
        return startUserDepName;
    }

    public void setStartUserDepName(String startUserDepName) {
        this.startUserDepName = startUserDepName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProcEndTime() {
        return procEndTime;
    }

    public void setProcEndTime(String procEndTime) {
        this.procEndTime = procEndTime;
    }
}
