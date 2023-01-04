package cn.gtmap.realestate.common.core.qo.analysis;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:wangwei2@gtmap.cn">wangwei2</a>
 * @version 1.0, 2018/12/20
 * @description
 */
@Api(value = "BdcParamQO", description = "查询参数列表")
public class BdcParamQO {

    @ApiModelProperty("权利人")
    private String qlr;

    @ApiModelProperty("证件号")
    private String zjh;

    @ApiModelProperty("不动产单元号")
    private String bdcdyh;

    @ApiModelProperty("不动产单元唯一编号")
    private String bdcdywybh;

    @ApiModelProperty("产权证号")
    private String cqzh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("受理编号")
    private String slbh;

    public String getQlr() {
        return qlr;
    }

    public void setQlr(String qlr) {
        this.qlr = qlr;
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh;
    }

    public String getBdcdyh() {
        return bdcdyh;
    }

    public void setBdcdyh(String bdcdyh) {
        this.bdcdyh = bdcdyh;
    }

    public String getBdcdywybh() {
        return bdcdywybh;
    }

    public void setBdcdywybh(String bdcdywybh) {
        this.bdcdywybh = bdcdywybh;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getSlbh() {
        return slbh;
    }

    public void setSlbh(String slbh) {
        this.slbh = slbh;
    }
}
