package cn.gtmap.realestate.common.core.qo.analysis.count;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author: <a href="@mailto:hynkoala@163.com">hanyaning</a>
 * @version: V1.0, 2019.03.05
 * @description: 证书印制号统计qo对象
 */
public class ZsyzhtjQO {

    @ApiModelProperty("查询截止日期")
    public String jzrq;
    @ApiModelProperty("权利类型")
    private String qllx;
    @ApiModelProperty("登记类型")
    private String djlx;
    @ApiModelProperty("登记小类")
    private String djxl;
    @ApiModelProperty("使用情况")
    private String syqk;
    @ApiModelProperty("查询起始日期")
    private String qsrq;

    public String getSyqk() {
        return syqk;
    }

    public void setSyqk(String syqk) {
        this.syqk = syqk;
    }

    public String getQllx() {
        return qllx;
    }

    public void setQllx(String qllx) {
        this.qllx = qllx;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getDjxl() {
        return djxl;
    }

    public void setDjxl(String djxl) {
        this.djxl = djxl;
    }

    public String getQsrq() {
        return qsrq;
    }

    public void setQsrq(String qsrq) {
        this.qsrq = qsrq;
    }

    public String getJzrq() {
        return jzrq;
    }

    public void setJzrq(String jzrq) {
        this.jzrq = jzrq;
    }
}