package cn.gtmap.realestate.common.core.qo.rules;
/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/11/29 13:43
 * @description
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * @author <a href="mailto:zhangguangguang@gtmap.cn">zhangguangguang</a>
 * @version 1.0, 2018/12/5 17:43
 * @description
 */
@ApiModel(value = "BdcGzQtYzQO", description = "不动产规则其他验证信息")
public class BdcGzQtYzQO implements Serializable {
    private static final long serialVersionUID = -5066680504818699275L;
    @ApiModelProperty(value = "验证标识码", required = true)
    private String yzbsm;

    @ApiModelProperty(value = "规则验证数据")
    private Map<String, String> bdcgzyzsjmap;

    @ApiModelProperty(value = "验证模式", required = true)
    private String yzms;

    public String getYzbsm() {
        return yzbsm;
    }

    public void setYzbsm(String yzbsm) {
        this.yzbsm = yzbsm;
    }

    public Map<String, String> getBdcgzyzsjmap() {
        return bdcgzyzsjmap;
    }

    public void setBdcgzyzsjmap(Map<String, String> bdcgzyzsjmap) {
        this.bdcgzyzsjmap = bdcgzyzsjmap;
    }

    public String getYzms() {
        return yzms;
    }

    public void setYzms(String yzms) {
        this.yzms = yzms;
    }

    @Override
    public String toString() {
        return "BdcGzQtYzQO{" +
                "yzbsm='" + yzbsm + '\'' +
                ", bdcgzyzsjmap=" + bdcgzyzsjmap +
                ", yzms='" + yzms + '\'' +
                '}';
    }
}
