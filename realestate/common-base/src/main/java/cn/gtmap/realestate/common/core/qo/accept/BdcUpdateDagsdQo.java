package cn.gtmap.realestate.common.core.qo.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;


public class BdcUpdateDagsdQo {

    @ApiModelProperty(value = "档案归属地")
    private String dagsd;

    @ApiModelProperty(value = "单个项目ID")
    private String onexmid;

    @ApiModelProperty(value = "基本信息ID")
    private List<String> jbxxidList;

    @ApiModelProperty(value = "xmid")
    private List<String> xmidList;

    public String getDagsd() {
        return dagsd;
    }

    public void setDagsd(String dagsd) {
        this.dagsd = dagsd;
    }

    public String getOnexmid() {
        return onexmid;
    }

    public void setOnexmid(String onexmid) {
        this.onexmid = onexmid;
    }

    public List<String> getJbxxidList() {
        return jbxxidList;
    }

    public void setJbxxidList(List<String> jbxxidList) {
        this.jbxxidList = jbxxidList;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }
}
