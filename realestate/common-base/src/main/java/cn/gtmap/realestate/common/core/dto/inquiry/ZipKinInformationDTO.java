package cn.gtmap.realestate.common.core.dto.inquiry;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:fanghao@gtmap.cn">fanghao</a>
 * @version 1.0, 2022/06/30
 * @description 存放从zipKin接口接收到的数据
 */
public class ZipKinInformationDTO {
    private String traceId;

    private String id;

    private String name;

    private Double timestamp;

    private Long duration;

    private List<Map<String,Object>> annotations;

    private List<Map<String,Object>> binaryAnnotations;

    @ApiModelProperty(value = "总耗时")
    private Long sumDuration;

    @ApiModelProperty(value = "是否存在多条耗时服务")
    private Boolean sfczdgfw;

    public Boolean getSfczdgfw() {
        return sfczdgfw;
    }

    public void setSfczdgfw(Boolean sfczdgfw) {
        this.sfczdgfw = sfczdgfw;
    }

    public Long getSumDuration() {
        return sumDuration;
    }

    public void setSumDuration(Long sumDuration) {
        this.sumDuration = sumDuration;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Double timestamp) {
        this.timestamp = timestamp;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public List<Map<String, Object>> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Map<String, Object>> annotations) {
        this.annotations = annotations;
    }

    public List<Map<String, Object>> getBinaryAnnotations() {
        return binaryAnnotations;
    }

    public void setBinaryAnnotations(List<Map<String, Object>> binaryAnnotations) {
        this.binaryAnnotations = binaryAnnotations;
    }

    @Override
    public String toString() {
        return "ZipKinInformationDTO{" +
                "traceId='" + traceId + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", duration=" + duration +
                ", annotations=" + annotations +
                ", binaryAnnotations=" + binaryAnnotations +
                ", sumDuration=" + sumDuration +
                ", sfczdgfw=" + sfczdgfw +
                '}';
    }
}
