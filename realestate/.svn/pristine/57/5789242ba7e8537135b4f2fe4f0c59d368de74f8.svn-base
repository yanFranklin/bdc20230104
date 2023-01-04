package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/12/11
 * @description 不动产查档信息查询QO实体定义
 */
public class BdcCdxxCxQO {

    @ApiModelProperty(value = "不动产权证号")
    private String bdcqzh;

    @ApiModelProperty(value = "权利人名称")
    private String[] qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String[] qlrzjh;

    @ApiModelProperty(value = "坐落")
    private String zl;

    public String getBdcqzh() {
        return bdcqzh;
    }

    public void setBdcqzh(String bdcqzh) {
        this.bdcqzh = bdcqzh;
    }

    public String[] getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String[] qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String[] getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String[] qlrzjh) {
        this.qlrzjh = qlrzjh;
    }

    public String getZl() {
        return zl;
    }

    public void setZl(String zl) {
        this.zl = zl;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BdcCdxxCxQO{");
        sb.append("bdcqzh='").append(bdcqzh).append('\'');
        sb.append(", qlrmc=").append(qlrmc == null ? "null" : Arrays.asList(qlrmc).toString());
        sb.append(", qlrzjh=").append(qlrzjh == null ? "null" : Arrays.asList(qlrzjh).toString());
        sb.append(", zl='").append(zl).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
