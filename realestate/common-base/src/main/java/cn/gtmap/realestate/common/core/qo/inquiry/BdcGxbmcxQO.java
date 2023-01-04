package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:chenyucheng@gtmap.cn">chenyucheng</a>
 * @version 1.0, 2020/11/4
 * @description 纪委查询QO
 */
@Api(value = "BdcGxbmcxQO", description = "共享部门QO")
public class BdcGxbmcxQO {

    @ApiModelProperty("权利人名称")
    private String qlr;

    @ApiModelProperty("权利人证件号")
    private String zjh;

    @ApiModelProperty("权利人证件号模糊类型")
    private String zjhmh;

    @ApiModelProperty("坐落")
    private String zl;

    @ApiModelProperty("产权证号")
    private String cqzh;

    @ApiModelProperty("产权证号模糊类型")
    private String cqzhmh;

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

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    public String getCqzh() {
        return cqzh;
    }

    public void setCqzh(String cqzh) {
        this.cqzh = cqzh;
    }

    public String getZjhmh() {
        return zjhmh;
    }

    public void setZjhmh(String zjhmh) {
        this.zjhmh = zjhmh;
    }

    public String getCqzhmh() {
        return cqzhmh;
    }

    public void setCqzhmh(String cqzhmh) {
        this.cqzhmh = cqzhmh;
    }

    @Override
    public String toString() {
        return "BdcGxbmcxQO{" +
                "qlr='" + qlr + '\'' +
                ", zjh='" + zjh + '\'' +
                ", zjhmh='" + zjhmh + '\'' +
                ", zl='" + zl + '\'' +
                ", cqzh='" + cqzh + '\'' +
                ", cqzhmh='" + cqzhmh + '\'' +
                '}';
    }
}
