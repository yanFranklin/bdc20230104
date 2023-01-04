package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author <a href=""mailto:chenchunxue@gtmap.cn>chenchunxue</a>
 * @version 1.0, 2020/7/30.
 * @description
 */
@Table(name = "CHECK_PLAN")
@ApiModel(value = "CheckPlanDO",description = "检查计划表")
public class CheckPlanDO {
    @Id
    @ApiModelProperty(value = "主键")
    private String id;
    @ApiModelProperty(value = "版本号")
    private String version;
    @ApiModelProperty(value = "起始日期")
    private Date qsrq;
    @ApiModelProperty(value = "结束日期")
    private Date jsrq;
    @ApiModelProperty(value = "单次检查条数")
    private Integer dcjcts;
    @ApiModelProperty(value = "总数据量")
    private Integer zsjl;
    @ApiModelProperty(value = "检查次数")
    private Integer jccs;
    @ApiModelProperty(value = "当前检查次数")
    private Integer dqjccs;
    @ApiModelProperty(value = "创建人")
    private String createuser;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "当前检查执行时间")
    private Date dqjcsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getQsrq() {
        return qsrq;
    }

    public void setQsrq(Date qsrq) {
        this.qsrq = qsrq;
    }

    public Date getJsrq() {
        return jsrq;
    }

    public void setJsrq(Date jsrq) {
        this.jsrq = jsrq;
    }

    public Integer getDcjcts() {
        return dcjcts;
    }

    public void setDcjcts(Integer dcjcts) {
        this.dcjcts = dcjcts;
    }

    public Integer getZsjl() {
        return zsjl;
    }

    public void setZsjl(Integer zsjl) {
        this.zsjl = zsjl;
    }

    public Integer getJccs() {
        return jccs;
    }

    public void setJccs(Integer jccs) {
        this.jccs = jccs;
    }

    public Integer getDqjccs() {
        return dqjccs;
    }

    public void setDqjccs(Integer dqjccs) {
        this.dqjccs = dqjccs;
    }

    public String getCreateuser() {
        return createuser;
    }

    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getDqjcsj() {
        return dqjcsj;
    }

    public void setDqjcsj(Date dqjcsj) {
        this.dqjcsj = dqjcsj;
    }

    @Override
    public String toString() {
        return "CheckPlanDO{" +
                "id='" + id + '\'' +
                ", version='" + version + '\'' +
                ", qsrq=" + qsrq +
                ", jsrq=" + jsrq +
                ", dcjcts=" + dcjcts +
                ", zsjl=" + zsjl +
                ", jccs=" + jccs +
                ", dqjccs=" + dqjccs +
                ", createuser='" + createuser + '\'' +
                ", createtime=" + createtime +
                ", dqjcsj=" + dqjcsj +
                '}';
    }
}
