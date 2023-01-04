package cn.gtmap.realestate.common.core.dto.certificate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author <a href="mailto:bianwen@gtmap.cn">bianwen/a>"
 * @version 1.0, 2018/11/19
 * @description 归档信息查询QO
 */
public class BdcUpdateDagsdDTO implements Serializable {
    @ApiModelProperty(value = "项目id")
    private String xmid;

    @ApiModelProperty(value = "工作流实例ID")
    private String gzlslid;

    @ApiModelProperty(value = "档案归属地")
    private String dagsd;

    @ApiModelProperty(value = "数据来源")
    private String sjly = "0";

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getDagsd() {
        return dagsd;
    }

    public void setDagsd(String dagsd) {
        this.dagsd = dagsd;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }
}
