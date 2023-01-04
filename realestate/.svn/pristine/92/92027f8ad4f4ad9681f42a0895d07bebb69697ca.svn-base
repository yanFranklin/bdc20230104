package cn.gtmap.realestate.exchange.core.dto.yctb;

import cn.gtmap.realestate.common.util.ParamUtil;

/**
 * @author <a href="mailto:caolu@gtmap.cn">caolu</a>
 * @version 1.0, 2022/6/14
 * @description 合肥_获取纳税缴费状态,请求参数
 */
public class YctbGetTaxPayRequest {
    /**
     * 区县代码
     */
    private String qxdm;
    /**
     * 业务号
     */
    private String ywh;
    /**
     * 类型 1 税务，2 非税
     */
    private String type;

    public void checkParam(){
        ParamUtil.nonNull(this.ywh, "业务号不能为空");
//        ParamUtil.nonNull(this.qxdm,"区县代码不能为空");
        ParamUtil.nonNull(this.type,"支付类型不能为空");
    }

    public String getQxdm() {
        return qxdm;
    }

    public void setQxdm(String qxdm) {
        this.qxdm = qxdm;
    }

    public String getYwh() {
        return ywh;
    }

    public void setYwh(String ywh) {
        this.ywh = ywh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "YctbGetPayUrlRequest{" +
                "qxdm='" + qxdm + '\'' +
                ", ywh='" + ywh + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
