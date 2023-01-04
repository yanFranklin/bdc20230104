package cn.gtmap.realestate.supervise.core.dto;

import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxDO;
import cn.gtmap.realestate.supervise.core.domain.BdcLfSqxxSqryDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href ="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 2021/09/13
 * @description 职责权能监管-授权信息
 */
public class BdcLfSqxxDTO extends BdcLfSqxxDO {
    @ApiModelProperty(value = "授权人员")
    private List<BdcLfSqxxSqryDO> sqrys;

    @ApiModelProperty(value = "授权人员信息")
    private String sqryxx;

    @ApiModelProperty(value = "所属科室")
    private String ks;

    @ApiModelProperty(value = "部门名称")
    private String bmmc;


    public String getSqryxx() {
        return sqryxx;
    }

    public void setSqryxx(String sqryxx) {
        this.sqryxx = sqryxx;
    }

    public List<BdcLfSqxxSqryDO> getSqrys() {
        return sqrys;
    }

    public void setSqrys(List<BdcLfSqxxSqryDO> sqrys) {
        this.sqrys = sqrys;
    }

    public String getKs() {
        return ks;
    }

    public void setKs(String ks) {
        this.ks = ks;
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc;
    }

    @Override
    public String toString() {
        return "BdcLfSqxxDTO{" +
                "sqrys=" + sqrys +
                ", sqryxx='" + sqryxx + '\'' +
                ", ks='" + ks + '\'' +
                ", bmmc='" + bmmc + '\'' +
                '}';
    }
}
