package cn.gtmap.realestate.common.core.domain.inquiry;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/06/30
 * @description
 */
@Table(name = "ZIPKIN")
@ApiModel(value = "ZipKinDO", description = "链路服务调用记录")
public class ZipKinDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "链路中最高耗时")
    private Long duration;

    @ApiModelProperty(value = "总耗时")
    private Long sumDuration;

    @ApiModelProperty(value = "异常情况（0：正常，1：异常）")
    private Integer error;

    @ApiModelProperty(value = "应用")
    private String appService;

    @ApiModelProperty(value = "方法")
    private String appMethod;

    @ApiModelProperty(value = "服务地址")
    private String url;

    @ApiModelProperty(value = "路径(名称)")
    private String name;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @ApiModelProperty(value = "监控日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date monitorDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(Long sumDuration) {
        this.sumDuration = sumDuration;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getAppService() {
        return appService;
    }

    public void setAppService(String appService) {
        this.appService = appService;
    }

    public String getAppMethod() {
        return appMethod;
    }

    public void setAppMethod(String appMethod) {
        this.appMethod = appMethod;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getMonitorDate() {
        return monitorDate;
    }

    public void setMonitorDate(Date monitorDate) {
        this.monitorDate = monitorDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
