package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 单元锁定查询条件
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-08-19 15:11
 **/
public class BdcDysdQO {
    @ApiModelProperty("单元号集合")
    private List<String> bdcdyhList;
    @ApiModelProperty("锁定状态")
    private Integer sdzt;
    @ApiModelProperty("锁定类型-默认查询为0 一般锁定")
    private Integer sdlx = 0;

    @ApiModelProperty("工作流实例id")
    private String gzlslid;
    @ApiModelProperty("项目id")
    private String xmid;

    @ApiModelProperty("单元锁定主键")
    private String dysdid;


    public List<String> getBdcdyhList() {
        return bdcdyhList;
    }

    public void setBdcdyhList(List<String> bdcdyhList) {
        this.bdcdyhList = bdcdyhList;
    }

    public Integer getSdzt() {
        return sdzt;
    }

    public void setSdzt(Integer sdzt) {
        this.sdzt = sdzt;
    }

    public Integer getSdlx() {
        return sdlx;
    }

    public void setSdlx(Integer sdlx) {
        this.sdlx = sdlx;
    }

    public String getGzlslid() {
        return gzlslid;
    }

    public void setGzlslid(String gzlslid) {
        this.gzlslid = gzlslid;
    }

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getDysdid() {
        return dysdid;
    }

    public void setDysdid(String dysdid) {
        this.dysdid = dysdid;
    }

    @Override
    public String toString() {
        return "BdcDysdQO{" +
                "bdcdyhList=" + bdcdyhList +
                ", sdzt=" + sdzt +
                ", sdlx=" + sdlx +
                ", gzlslid='" + gzlslid + '\'' +
                ", xmid='" + xmid + '\'' +
                ", dysdid='" + dysdid + '\'' +
                '}';
    }
}
