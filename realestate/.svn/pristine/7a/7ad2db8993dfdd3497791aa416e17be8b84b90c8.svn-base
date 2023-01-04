package cn.gtmap.realestate.common.core.qo.inquiry;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;

/**
 * @author <a href="mailto:huangjian@gtmap.cn">huangjian</a>
 * @version 1.0, 2019/10/4 19:03
 * @description
 */
@Api(value = "BdcGrafQO", description = "个人住房查询QO")
public class BdcGrzfQO {

    @ApiModelProperty(value = "权利人名称")
    private String[] qlrmc;

    @ApiModelProperty(value = "权利人证件号")
    private String[] zjh;

    public String[] getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String[] qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String[] getZjh() {
        return zjh;
    }

    public void setZjh(String[] zjh) {
        this.zjh = zjh;
    }

    @Override
    public String toString() {
        return "BdcGrzfQO{" +
                "qlrmc=" + Arrays.toString(qlrmc) +
                ", zjh=" + Arrays.toString(zjh) +
                '}';
    }
}
