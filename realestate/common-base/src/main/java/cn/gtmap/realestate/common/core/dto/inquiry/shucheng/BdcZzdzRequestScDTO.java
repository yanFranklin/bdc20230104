package cn.gtmap.realestate.common.core.dto.inquiry.shucheng;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author <a href="mailto:wnagyongming@gtmap.cn">wangyongming</a>
 * @version 1.0  2021-11-11
 * @description 不动产自助打证（舒城）requestDTO
 */
@Api(value = "BdcZzdzRequestScDTO", description = "不动产自助打证(舒城)requestDTO")
public class BdcZzdzRequestScDTO {
    @ApiModelProperty(value = "受理编号")
    private String ywbh;

    @ApiModelProperty(value = "代理人姓名")
    private String xm;

    @ApiModelProperty(value = "代理人证件号")
    private String sfzh;

    @ApiModelProperty(value = "不出证流程登记小类")
    private List<String> excludeDjxl;

    @ApiModelProperty(value = "不出证工作流定义id")
    private List<String> gzldyid;

    @ApiModelProperty(value = "领取人名称")
    private String lqrxm;

    @ApiModelProperty(value = "领取人证件号")
    private String lqrzjh;

    public String getLqrxm() {
        return lqrxm;
    }

    public void setLqrxm(String lqrxm) {
        this.lqrxm = lqrxm;
    }

    public String getLqrzjh() {
        return lqrzjh;
    }

    public void setLqrzjh(String lqrzjh) {
        this.lqrzjh = lqrzjh;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public List<String> getExcludeDjxl() {
        return excludeDjxl;
    }

    public void setExcludeDjxl(List<String> excludeDjxl) {
        this.excludeDjxl = excludeDjxl;
    }

    public List<String> getGzldyid() {
        return gzldyid;
    }

    public void setGzldyid(List<String> gzldyid) {
        this.gzldyid = gzldyid;
    }

    @Override
    public String toString() {
        return "BdcZzdzRequestScDTO{" +
                "ywbh='" + ywbh + '\'' +
                ", xm='" + xm + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", excludeDjxl=" + excludeDjxl +
                ", gzldyid=" + gzldyid +
                '}';
    }
}
