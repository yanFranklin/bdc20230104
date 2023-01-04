package cn.gtmap.realestate.common.core.domain.register;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @program: realestate
 * @description: 工作流事件执行日志
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2022-04-18 16:31
 **/
@Table(name = "BDC_GZLSJ_LOG")
@ApiModel(value = "BdcGzlsjLogDO", description = "工作流事件执行接口日志")
public class BdcGzlsjLogDO {

    @ApiModelProperty("日志主键")
    @Id
    private String rzid;

    @ApiModelProperty("请求地址")
    private String qqdz;

    @ApiModelProperty("请求方式")
    private String qqfs;

    @ApiModelProperty("用户信息")
    private String yhxx;

    @ApiModelProperty("异常信息")
    private String ycxx;

    @ApiModelProperty("请求时间")
    private Date qqsj;

    public String getRzid() {
        return rzid;
    }

    public void setRzid(String rzid) {
        this.rzid = rzid;
    }

    public String getQqdz() {
        return qqdz;
    }

    public void setQqdz(String qqdz) {
        this.qqdz = qqdz;
    }

    public String getQqfs() {
        return qqfs;
    }

    public void setQqfs(String qqfs) {
        this.qqfs = qqfs;
    }

    public String getYhxx() {
        return yhxx;
    }

    public void setYhxx(String yhxx) {
        this.yhxx = yhxx;
    }

    public String getYcxx() {
        return ycxx;
    }

    public void setYcxx(String ycxx) {
        this.ycxx = ycxx;
    }

    public Date getQqsj() {
        return qqsj;
    }

    public void setQqsj(Date qqsj) {
        this.qqsj = qqsj;
    }
}
