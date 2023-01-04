package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 运行日志实体类
 */
@Table(name = "CHECK_LOG")
@ApiModel(value = "CheckLogDO",description = "运行日志")
public class CheckLogDO {

    @Id
    @ApiModelProperty(value = "日志编号")
    private String logid;
    @ApiModelProperty(value = "检查项目数量")
    private Integer jcxmsl;
    @ApiModelProperty(value = "检查办结项目数量")
    private Integer jcbjxmsl;
    @ApiModelProperty(value = "问题数量")
    private Integer wtsl;
    @ApiModelProperty(value = "起始时间")
    private Date qssj;
    @ApiModelProperty(value = "结束时间")
    private Date jssj;
    @ApiModelProperty(value = "任务类型")
    private String type;
    public Integer getWtsl() {
        return wtsl;
    }

    public void setWtsl(Integer wtsl) {
        this.wtsl = wtsl;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public Integer getJcxmsl() {
        return jcxmsl;
    }

    public void setJcxmsl(Integer jcxmsl) {
        this.jcxmsl = jcxmsl;
    }

    public Integer getJcbjxmsl() {
        return jcbjxmsl;
    }

    public void setJcbjxmsl(Integer jcbjxmsl) {
        this.jcbjxmsl = jcbjxmsl;
    }

    public Date getQssj() {
        return qssj;
    }

    public void setQssj(Date qssj) {
        this.qssj = qssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CheckLogDO{" +
                "logid='" + logid + '\'' +
                ", jcxmsl=" + jcxmsl +
                ", jcbjxmsl=" + jcbjxmsl +
                ", wtsl=" + wtsl +
                ", qssj=" + qssj +
                ", jssj=" + jssj +
                ", type='" + type + '\'' +
                '}';
    }
}
