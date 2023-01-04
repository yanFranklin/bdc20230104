package cn.gtmap.realestate.inquiry.core.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author <a href="mailto:hanyi@gtmap.cn">hanyi</a>
 * @version 1.0, 2019/7/10
 * @description 综合屏呼叫信息传入日志
 */
@Table(name = "BDC_HJXX_LOG")
public class BdcHjxxLogDO {
    @Id
    @ApiModelProperty(value = "日志id")
    private String logid;
    @ApiModelProperty(value = "生成时间")
    private String scsj;
    @ApiModelProperty(value = "条数")
    private String ts;
    @ApiModelProperty(value = "中心名称")
    private String zxmc;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getZxmc() {
        return zxmc;
    }

    public void setZxmc(String zxmc) {
        this.zxmc = zxmc;
    }

    @Override
    public String toString() {
        return "BdcHjxxLogDO{" +
                "logid='" + logid + '\'' +
                ", scsj='" + scsj + '\'' +
                ", ts='" + ts + '\'' +
                ", zxmc='" + zxmc + '\'' +
                '}';
    }
}
