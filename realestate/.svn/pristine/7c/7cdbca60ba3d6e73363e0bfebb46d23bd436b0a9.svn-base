package cn.gtmap.realestate.common.core.dto.accept;

import cn.gtmap.realestate.common.core.domain.BdcJjdDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlJbxxDO;
import cn.gtmap.realestate.common.core.domain.accept.BdcSlSjclDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 档案交接页面信息
 * @author: <a href="mailto:gaolining@gtmap.cn">gaolining</a>
 * @create: 2021-06-10 15:49
 **/
public class BdcDajjDTO {
    @ApiModelProperty("基本信息")
    private BdcSlJbxxDO bdcSlJbxxDO;

    @ApiModelProperty("交接单信息")
    private BdcJjdDO bdcJjdDO;

    @ApiModelProperty(value = "权利人")
    private String qlr;

    @ApiModelProperty(value = "退回原因")
    private String thyy;

    @ApiModelProperty(value = "退件时间")
    private String thsj;

    @ApiModelProperty(value = "状态")
    private String ajzt;

    @ApiModelProperty(value = "收件材料信息")
    private List<BdcSlSjclDO> bdcSlSjclDOList;

    @ApiModelProperty(value = "原流程实例id")
    private String yxmGzlslid;

    public BdcSlJbxxDO getBdcSlJbxxDO() {
        return bdcSlJbxxDO;
    }

    public void setBdcSlJbxxDO(BdcSlJbxxDO bdcSlJbxxDO) {
        this.bdcSlJbxxDO = bdcSlJbxxDO;
    }

    public BdcJjdDO getBdcJjdDO() {
        return bdcJjdDO;
    }

    public void setBdcJjdDO(BdcJjdDO bdcJjdDO) {
        this.bdcJjdDO = bdcJjdDO;
    }

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getThyy() {
        return thyy;
    }

    public void setThyy(String thyy) {
        this.thyy = thyy;
    }

    public String getThsj() {
        return thsj;
    }

    public void setThsj(String thsj) {
        this.thsj = thsj;
    }

    public String getAjzt() {
        return ajzt;
    }

    public void setAjzt(String ajzt) {
        this.ajzt = ajzt;
    }

    public List<BdcSlSjclDO> getBdcSlSjclDOList() {
        return bdcSlSjclDOList;
    }

    public void setBdcSlSjclDOList(List<BdcSlSjclDO> bdcSlSjclDOList) {
        this.bdcSlSjclDOList = bdcSlSjclDOList;
    }

    public String getYxmGzlslid() {
        return yxmGzlslid;
    }

    public void setYxmGzlslid(String yxmGzlslid) {
        this.yxmGzlslid = yxmGzlslid;
    }

    @Override
    public String toString() {
        return "BdcDajjDTO{" +
                "bdcSlJbxxDO=" + bdcSlJbxxDO +
                ", bdcJjdDO=" + bdcJjdDO +
                ", qlr='" + qlr + '\'' +
                ", thyy='" + thyy + '\'' +
                ", thsj='" + thsj + '\'' +
                ", ajzt='" + ajzt + '\'' +
                ", bdcSlSjclDOList=" + bdcSlSjclDOList +
                ", yxmGzlslid='" + yxmGzlslid + '\'' +
                '}';
    }
}
