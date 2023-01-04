package cn.gtmap.realestate.common.core.dto.inquiry.shucheng;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0  2022/07/05
 * @description 舒城-共享业务系统查询接口返回参数
 */
@Api(value = "BdcGxywResponseDTO", description = "舒城-共享业务系统查询接口返回参数")
public class BdcGxywResponseDTO {

    @ApiModelProperty(value = "受理编号")
    private String slbh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "不动产单元号")
    private String bdcdyh;

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    @Override
    public String toString() {
        return "BdcGxywResponseDTO{" +
                "slbh='" + slbh + '\'' +
                ", zl='" + zl + '\'' +
                ", qlrmc='" + qlrmc + '\'' +
                ", bdcdyh='" + bdcdyh + '\'' +
                '}';
    }
}
