package cn.gtmap.realestate.common.core.domain.check;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lst
 * @version 1.0, 2020-01-03
 * @description 检查日志实体类
 */
@Table(name = "CHECK_GZJC_LOG")
@ApiModel(value = "CheckGzjcLogDO",description = "检查日志")
public class CheckGzjcLogDO {

    @Id
    @ApiModelProperty(value = "日志编号")
    private String logid;
    @ApiModelProperty(value = "项目id")
    private String xmid;
    @ApiModelProperty(value = "受理编号")
    private String slbh;
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;
    @ApiModelProperty(value = "限制文号")
    private String xzwh;
    @ApiModelProperty(value = "规则id")
    private String gzid;
    @ApiModelProperty(value = "发现时间")
    private Date fxsj;
    @ApiModelProperty(value = "规则等级")
    private Integer jcdj;
    @ApiModelProperty(value = "检查信息")
    private String jcxx;
    @ApiModelProperty(value = "解决状态 0：未解决 1：已解决")
    private Integer jjzt;
    @ApiModelProperty(value = "解决时间")
    private Date jjsj;
    @ApiModelProperty(value = "解决方式")
    private String jjfs;
    @ApiModelProperty(value = "更新执行时间")
    private Date gxsj;
    @ApiModelProperty(value = "是否挂起")
    private Integer sfgq;
    @ApiModelProperty(value = "挂起原因")
    private String gqyy;
    @ApiModelProperty(value = "挂起时间")
    private Date gqsj;
    @ApiModelProperty(value = "限制文号项目id")
    private String xzwhxmid;
    @ApiModelProperty(value = "问题类型")
    private String type;
    @ApiModelProperty(value = "检查状态 0：正常 1：异常")
    private Integer jczt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
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

    public String getXzwh() {
        return xzwh;
    }

    public void setXzwh(String xzwh) {
        this.xzwh = xzwh;
    }

    public String getGzid() {
        return gzid;
    }

    public void setGzid(String gzid) {
        this.gzid = gzid;
    }

    public Date getFxsj() {
        return fxsj;
    }

    public void setFxsj(Date fxsj) {
        this.fxsj = fxsj;
    }

    public Integer getJcdj() {
        return jcdj;
    }

    public void setJcdj(Integer jcdj) {
        this.jcdj = jcdj;
    }

    public String getJcxx() {
        return jcxx;
    }

    public void setJcxx(String jcxx) {
        this.jcxx = jcxx;
    }

    public Integer getJjzt() {
        return jjzt;
    }

    public void setJjzt(Integer jjzt) {
        this.jjzt = jjzt;
    }

    public Date getJjsj() {
        return jjsj;
    }

    public void setJjsj(Date jjsj) {
        this.jjsj = jjsj;
    }

    public String getJjfs() {
        return jjfs;
    }

    public void setJjfs(String jjfs) {
        this.jjfs = jjfs;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public Integer getSfgq() {
        return sfgq;
    }

    public void setSfgq(Integer sfgq) {
        this.sfgq = sfgq;
    }

    public String getGqyy() {
        return gqyy;
    }

    public void setGqyy(String gqyy) {
        this.gqyy = gqyy;
    }

    public Date getGqsj() {
        return gqsj;
    }

    public void setGqsj(Date gqsj) {
        this.gqsj = gqsj;
    }

    public String getXzwhxmid() {
        return xzwhxmid;
    }

    public void setXzwhxmid(String xzwhxmid) {
        this.xzwhxmid = xzwhxmid;
    }

    public Integer getJczt() {
        return jczt;
    }

    public void setJczt(Integer jczt) {
        this.jczt = jczt;
    }

    @Override
    public String toString() {
        return "CheckGzjcLogDO{" +
                "logid='" + logid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", slbh='" + slbh + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                ", xzwh='" + xzwh + '\'' +
                ", gzid='" + gzid + '\'' +
                ", fxsj=" + fxsj +
                ", jcdj=" + jcdj +
                ", jcxx='" + jcxx + '\'' +
                ", jjzt=" + jjzt +
                ", jjsj=" + jjsj +
                ", jjfs='" + jjfs + '\'' +
                ", gxsj=" + gxsj +
                ", sfgq=" + sfgq +
                ", gqyy='" + gqyy + '\'' +
                ", gqsj=" + gqsj +
                ", xzwhxmid='" + xzwhxmid + '\'' +
                ", type='" + type + '\'' +
                ", jczt=" + jczt +
                '}';
    }
}
