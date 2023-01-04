package cn.gtmap.realestate.common.core.dto.exchange.nantong.fssr;

import javax.xml.bind.annotation.*;

/**
 * @author <a href="mailto:yaoyi@gtmap.cn">yaoyi</a>
 * @Date 2022/07/11
 * @description 通用应答返回消息Voucher
 */
@XmlType(propOrder = {"oriMsgNo", "result", "information"})
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ResponseVoucher {

    /**
     * OriMsgNo	原报文编号	NString	4	最终处理单位填写	M
     * Result	处理结果	NString	5	见附录处理结果代码一览表。	M
     * Information	详细信息	GBString		报文结果的补充信息。	O
     */
    private String OriMsgNo;
    private String Result;
    private String Information;

    @XmlElement(name = "OriMsgNo")
    public String getOriMsgNo() {
        return OriMsgNo;
    }

    public void setOriMsgNo(String oriMsgNo) {
        OriMsgNo = oriMsgNo;
    }

    @XmlElement(name = "Result")
    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    @XmlElement(name = "Information")
    public String getInformation() {
        return Information;
    }

    public void setInformation(String information) {
        Information = information;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResponseVoucher{");
        sb.append("OriMsgNo='").append(OriMsgNo).append('\'');
        sb.append(", Result='").append(Result).append('\'');
        sb.append(", Information='").append(Information).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
