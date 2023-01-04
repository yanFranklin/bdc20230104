package cn.gtmap.realestate.common.core.dto.inquiry;

import cn.gtmap.realestate.common.core.domain.BdcCfDO;
import cn.gtmap.realestate.common.core.domain.BdcYyDO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:zhuyong@gtmap.cn">zhuyong</a>
 * @version 1.0, 2019/7/15
 * @description 住房查询——房产档案信息
 */
public class BdcFcdaDTO {
    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    @ApiModelProperty(value = "房地产权信息")
    private BdcFdcqDTO fdcq;

    @ApiModelProperty(value = "住房信息")
    private List<BdcZfxxDTO> zfxx;

    @ApiModelProperty(value = "抵押信息")
    private List<BdcFcdaDyaqDTO> dyxx;

    @ApiModelProperty(value = "查封信息")
    private List<BdcCfDO> cfxx;

    @ApiModelProperty(value = "异议信息")
    private List<BdcYyDO> yyxx;

    @ApiModelProperty(value = "权籍管理代码")
    private List<String> qjgldm;

    @ApiModelProperty(value = "居住权信息")
    private List<BdcJzqxxDTO> jzqxx;

    public List<BdcZfxxDTO> getZfxx() {
        return zfxx;
    }

    public void setZfxx(List<BdcZfxxDTO> zfxx) {
        this.zfxx = zfxx;
    }

    public BdcFdcqDTO getFdcq() {
        return fdcq;
    }

    public void setFdcq(BdcFdcqDTO fdcq) {
        this.fdcq = fdcq;
    }

    public List<BdcFcdaDyaqDTO> getDyxx() {
        return dyxx;
    }

    public void setDyxx(List<BdcFcdaDyaqDTO> dyxx) {
        this.dyxx = dyxx;
    }

    public List<BdcCfDO> getCfxx() {
        return cfxx;
    }

    public void setCfxx(List<BdcCfDO> cfxx) {
        this.cfxx = cfxx;
    }

    public List<BdcYyDO> getYyxx() {
        return yyxx;
    }

    public void setYyxx(List<BdcYyDO> yyxx) {
        this.yyxx = yyxx;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public void setQjgldm(List<String> qjgldm) {
        this.qjgldm = qjgldm;
    }

    public List<String> getQjgldm() {
        return qjgldm;
    }

    public List<BdcJzqxxDTO> getJzqxx() {
        return jzqxx;
    }

    public void setJzqxx(List<BdcJzqxxDTO> jzqxx) {
        this.jzqxx = jzqxx;
    }

    @Override
    public String toString() {
        return "BdcFcdaDTO{" +
                "zfxx=" + zfxx +
                ", fdcq=" + fdcq +
                ", dyxx=" + dyxx +
                ", cfxx=" + cfxx +
                ", yyxx=" + yyxx +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
