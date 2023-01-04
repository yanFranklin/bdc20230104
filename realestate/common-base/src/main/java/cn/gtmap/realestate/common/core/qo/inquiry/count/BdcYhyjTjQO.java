package cn.gtmap.realestate.common.core.qo.inquiry.count;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @version 1.0, 2020/7/28
 * @description  不动产银行月结统计QO
 */
@ApiModel(value = "BdcYhyjTjQO", description = "不动产银行月结统计")
public class BdcYhyjTjQO {

    @ApiModelProperty(value = "开始时间")
    private String kssj;
    @ApiModelProperty(value = "结束时间")
    private String jssj;
    @ApiModelProperty(value = "抵押权人")
    private String dyqr;
    @ApiModelProperty(value = "抵押人类型：1为个人，其他为单位")
    private String dyrlx;


    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getDyqr() {
        return dyqr;
    }

    public void setDyqr(String dyqr) {
        this.dyqr = dyqr;
    }

    public String getDyrlx() {
        return dyrlx;
    }

    public void setDyrlx(String dyrlx) {
        this.dyrlx = dyrlx;
    }

    @Override
    public String toString() {
        return "BdcYhyjTjQO{" +
                "kssj='" + kssj + '\'' +
                ", jssj='" + jssj + '\'' +
                ", dyqr='" + dyqr + '\'' +
                ", dyrlx='" + dyrlx + '\'' +
                '}';
    }
}
