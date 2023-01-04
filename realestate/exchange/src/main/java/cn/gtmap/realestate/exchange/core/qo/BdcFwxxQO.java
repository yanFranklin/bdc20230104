package cn.gtmap.realestate.exchange.core.qo;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author <a href="mailto:huanghui@gtmap.cn">caolu</a>
 * @version 1.0  2022/11/15
 * @description 查询房屋信息
 */
public class BdcFwxxQO {

    @ApiModelProperty(value = "权利人名称")
    private String qlrmc;

    @ApiModelProperty(value = "证件号码")
    private String qlrzjhm;

    public String getQlrmc() { return qlrmc; }

    public void setQlrmc(String qlrmc) { this.qlrmc = qlrmc; }

    public String getQlrzjhm() { return qlrzjhm; }

    public void setQlrzjhm(String qlrzjhm) { this.qlrzjhm = qlrzjhm; }

    @Override
    public String toString() {
        return "BdcFwxxQO{" +
                "qlrmc='" + qlrmc + '\'' +
                ", qlrzjhm='" + qlrzjhm + '\'' +
                '}';
    }
}
