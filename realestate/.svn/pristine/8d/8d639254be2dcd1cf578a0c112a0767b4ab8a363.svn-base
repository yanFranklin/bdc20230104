package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/08/09
 * @description 缴费状态查询 Voucher
 */
@XmlType(propOrder = {"payCode", "payMode", "hold1", "hold2"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class JfztCxVoucher {

    /**
     * 标识符	数据项名称	    类型	     长度	 数据项描述	强制/可选
     * PayCode  缴款码/非税缴款编号 String   [1,20]  唯一码 M
     * PayMode  收款渠道          NString  [1,2]   见附录 2 O
     * Hold1    预留字段          GBString [0,100] 票据有校验码时填校验码 O
     * Hold2    预留字段          GBString [0,100]
     */

    private String PayCode;
    private String PayMode;
    private String Hold1;
    private String Hold2;

    @XmlElement(name = "PayCode")
    public String getPayCode() {
        return PayCode;
    }

    public void setPayCode(String payCode) {
        PayCode = payCode;
    }

    @XmlElement(name = "PayMode")
    public String getPayMode() {
        return PayMode;
    }

    public void setPayMode(String payMode) {
        PayMode = payMode;
    }

    @XmlElement(name = "Hold1")
    public String getHold1() {
        return Hold1;
    }

    public void setHold1(String hold1) {
        Hold1 = hold1;
    }

    @XmlElement(name = "Hold2")
    public String getHold2() {
        return Hold2;
    }

    public void setHold2(String hold2) {
        Hold2 = hold2;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("JfztCxVoucher{");
        sb.append("PayCode='").append(PayCode).append('\'');
        sb.append(", PayMode='").append(PayMode).append('\'');
        sb.append(", Hold1='").append(Hold1).append('\'');
        sb.append(", Hold2='").append(Hold2).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
