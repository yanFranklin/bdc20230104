package cn.gtmap.realestate.exchange.core.dto.hefei.fcjy.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:liyinqiao@gtmap.cn">liyinqiao</a>
 * @version 1.0  2019-07-01
 * @description 读取房产交易系统 备案合同信息请求参数
 */
public class FcjyBahtxxRequest {

    // 身份证号
    @NotBlank
    private String cardNo;

    // 产权证号
    @NotBlank
    private String realNo;

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getRealNo() {
        return realNo;
    }

    public void setRealNo(String realNo) {
        this.realNo = realNo;
    }
}
