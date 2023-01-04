package cn.gtmap.realestate.common.core.dto.accept;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @program: realestate
 * @description: 获取房产交易合同信息的页面数据
 * @author: <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @create: 2022-01-10
 **/
public class FcjyHtxxDTO {
    @ApiModelProperty(value = "合同编号")
    private String htbh;

    @ApiModelProperty(value = "买受人名称")
    private List<String> msrmc;

    @ApiModelProperty(value = "出卖人名称")
    private List<String> cmrmc;

    @ApiModelProperty(value = "坐落")
    private String zl;

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public List<String> getMsrmc() {
        return msrmc;
    }

    public void setMsrmc(List<String> msrmc) {
        this.msrmc = msrmc;
    }

    public List<String> getCmrmc() {
        return cmrmc;
    }

    public void setCmrmc(List<String> cmrmc) {
        this.cmrmc = cmrmc;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        return "FcjyHtxxDTO{" +
                "htbh='" + htbh + '\'' +
                ", msrmc=" + msrmc +
                ", cmrmc=" + cmrmc +
                ", zl='" + zl + '\'' +
                '}';
    }
}
