package cn.gtmap.realestate.common.core.qo.pub;
/*
 * @author <a href="mailto:yinyao@gtmap.cn">yinyao</a>
 * @version 1.0, 2018/12/14
 * @description 权利人
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "QlrQO", description = "权利人信息")
public class QlrQO {
    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;
    @ApiModelProperty(value = "权利人证件号")
    private String qlrzjh;

    public String getQlrmc() {
        return qlrmc;
    }

    public void setQlrmc(String qlrmc) {
        this.qlrmc = qlrmc;
    }

    public String getQlrzjh() {
        return qlrzjh;
    }

    public void setQlrzjh(String qlrzjh) {
        this.qlrzjh = qlrzjh;
    }
}
