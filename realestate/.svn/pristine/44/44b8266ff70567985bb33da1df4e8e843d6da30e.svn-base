package cn.gtmap.realestate.common.core.qo.register;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 恢复权利信息
 *
 * @author <a href="mailto:zhangwentao@gtmap.cn>zhangwentao</a>"
 * @version 1.0, 2019/6/26
 */
public class BdcHfQO {
    @ApiModelProperty(value = "多个xmid")
    private List<String> xmidList;
    @ApiModelProperty(value = "xmid")
    private String xmid;
    @ApiModelProperty(value = "权属状态")
    private Integer qszt;
    @ApiModelProperty(value = "恢复原因")
    private String hfyy;

    @Override
    public String toString() {
        return "BdcHfQO{" +
                "xmidList=" + xmidList +
                ", xmid='" + xmid + '\'' +
                ", qszt=" + qszt +
                ", hfyy=" + hfyy +
                '}';
    }

    public String getHfyy() {
        return hfyy;
    }

    public void setHfyy(String hfyy) {
        this.hfyy = hfyy;
    }

    public List<String> getXmidList() {
        return xmidList;
    }

    public void setXmidList(List<String> xmidList) {
        this.xmidList = xmidList;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public Integer getQszt() {
        return qszt;
    }

    public void setQszt(Integer qszt) {
        this.qszt = qszt;
    }
}
