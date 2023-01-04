package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author <a href="mailto:haungjian@gtmap.cn">huangjian</a>
 * @Date 2021/11/30
 * @description 通用返回Voucher
 */
@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.FIELD)
public class FsczResponse {


    /**
     * OriMsgNo : 8999
     * Result : 00000
     * Information : 0001690117
     */
    @XmlElement(name = "OriMsgNo")
    private String OriMsgNo;
    @XmlElement(name = "Result")
    private String Result;
    @XmlElement(name = "Information")
    private String Information;

    public String getOriMsgNo() {
        return OriMsgNo;
    }

    public void setOriMsgNo(String OriMsgNo) {
        this.OriMsgNo = OriMsgNo;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getInformation() {
        return Information;
    }

    public void setInformation(String Information) {
        this.Information = Information;
    }

    @Override
    public String toString() {
        return "FsczResponse{" +
                "OriMsgNo='" + OriMsgNo + '\'' +
                ", Result='" + Result + '\'' +
                ", Information='" + Information + '\'' +
                '}';
    }
}
