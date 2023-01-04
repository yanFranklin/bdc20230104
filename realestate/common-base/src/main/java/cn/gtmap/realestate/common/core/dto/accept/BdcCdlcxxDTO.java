package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcFdcqDO;
import cn.gtmap.realestate.common.core.domain.BdcXmDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlCdxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSqrDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlXmDO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @program: realestate
 * @description: 常州查档受理页面信息
 * @author: <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @create: 2020-09-24 08:52
 **/
public class BdcCdlcxxDTO implements Serializable {
    private static final long serialVersionUID = 279491582213232090L;

    @ApiModelProperty(value = "申请人id")
    private String sqrid;
    @ApiModelProperty(value = "权利人")
    private String qlr;
    @ApiModelProperty(value = "共有人")
    private String gyr;
    @ApiModelProperty(value = "房地产权")
    private BdcFdcqDO bdcFdcqDO = new BdcFdcqDO();

    @ApiModelProperty(value = "权利人查询gzldyid")
    private String qlrcxGzldyid;

    @ApiModelProperty(value = "项目信息")
    private BdcSlXmDO bdcXmDO;
    @ApiModelProperty(value = "原项目信息")
    private BdcXmDO yBdcXmDO;
    @ApiModelProperty(value = "查档填报信息")
    private BdcSlCdxxDO bdcCdxxDO;

    @ApiModelProperty(value = "权利人集合")
    private List<BdcSlSqrDO> qlrList;

    @ApiModelProperty(value = "基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO;

    @ApiModelProperty(value = "建筑面积之和")
    private Double jzmjzh;

    public BdcSlXmDO getBdcXmDO() {
        return bdcXmDO;
    }

    public void setBdcXmDO(BdcSlXmDO bdcXmDO) {
        this.bdcXmDO = bdcXmDO;
    }

    public BdcSlCdxxDO getBdcCdxxDO() {
        return bdcCdxxDO;
    }

    public void setBdcCdxxDO(BdcSlCdxxDO bdcCdxxDO) {
        this.bdcCdxxDO = bdcCdxxDO;
    }

    public BdcXmDO getyBdcXmDO() {
        return yBdcXmDO;
    }

    public void setyBdcXmDO(BdcXmDO yBdcXmDO) {
        this.yBdcXmDO = yBdcXmDO;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getGyr() {
        return gyr;
    }

    public void setGyr(String gyr) {
        this.gyr = gyr;
    }

    public BdcFdcqDO getBdcFdcqDO() {
        return bdcFdcqDO;
    }

    public void setBdcFdcqDO(BdcFdcqDO bdcFdcqDO) {
        this.bdcFdcqDO = bdcFdcqDO;
    }

    public List<BdcSlSqrDO> getQlrList() {
        return qlrList;
    }

    public void setQlrList(List<BdcSlSqrDO> qlrList) {
        this.qlrList = qlrList;
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid;
    }

    public String getQlrcxGzldyid() {
        return qlrcxGzldyid;
    }

    public void setQlrcxGzldyid(String qlrcxGzldyid) {
        this.qlrcxGzldyid = qlrcxGzldyid;
    }

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }

    public Double getJzmjzh() {
        return jzmjzh;
    }

    public void setJzmjzh(Double jzmjzh) {
        this.jzmjzh = jzmjzh;
    }

    @Override
    public String toString() {
        return "BdcCdlcxxDTO{" +
                "sqrid='" + sqrid + '\'' +
                ", qlr='" + qlr + '\'' +
                ", gyr='" + gyr + '\'' +
                ", bdcFdcqDO=" + bdcFdcqDO +
                ", qlrcxGzldyid='" + qlrcxGzldyid + '\'' +
                ", bdcXmDO=" + bdcXmDO +
                ", yBdcXmDO=" + yBdcXmDO +
                ", bdcCdxxDO=" + bdcCdxxDO +
                ", qlrList=" + qlrList +
                ", bdcSlJbxxDO=" + bdcSlJbxxDO +
                ", jzmjzh=" + jzmjzh +
                '}';
    }
}
