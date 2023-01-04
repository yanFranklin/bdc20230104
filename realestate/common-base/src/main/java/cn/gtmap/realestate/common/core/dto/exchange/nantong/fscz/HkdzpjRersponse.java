package cn.gtmap.realestate.common.core.dto.exchange.nantong.fscz;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Voucher")
@XmlAccessorType(XmlAccessType.FIELD)
public class HkdzpjRersponse {
    @XmlElement(name = "Result")
    private String Result;
    @XmlElement(name = "Msg")
    private String Msg;
    @XmlElement(name = "Svr_Code")
    private String Svr_Code;
    @XmlElement(name = "Svr_Msg")
    private String Svr_Msg;
    @XmlElement(name = "Trade_Id")
    private String Trade_Id;
    @XmlElement(name = "Issue_Date")
    private String Issue_Date;
    @XmlElement(name = "Invoice_Url")
    private String Invoice_Url;
    @XmlElement(name = "Attach_Info")
    private String Attach_Info;

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public String getSvr_Code() {
        return Svr_Code;
    }

    public void setSvr_Code(String svr_Code) {
        Svr_Code = svr_Code;
    }

    public String getSvr_Msg() {
        return Svr_Msg;
    }

    public void setSvr_Msg(String svr_Msg) {
        Svr_Msg = svr_Msg;
    }

    public String getTrade_Id() {
        return Trade_Id;
    }

    public void setTrade_Id(String trade_Id) {
        Trade_Id = trade_Id;
    }

    public String getIssue_Date() {
        return Issue_Date;
    }

    public void setIssue_Date(String issue_Date) {
        Issue_Date = issue_Date;
    }

    public String getInvoice_Url() {
        return Invoice_Url;
    }

    public void setInvoice_Url(String invoice_Url) {
        Invoice_Url = invoice_Url;
    }

    public String getAttach_Info() {
        return Attach_Info;
    }

    public void setAttach_Info(String attach_Info) {
        Attach_Info = attach_Info;
    }

    @Override
    public String toString() {
        return "HkdzpjRersponse{" +
                "Result='" + Result + '\'' +
                ", Msg='" + Msg + '\'' +
                ", Svr_Code='" + Svr_Code + '\'' +
                ", Svr_Msg='" + Svr_Msg + '\'' +
                ", Trade_Id='" + Trade_Id + '\'' +
                ", Issue_Date='" + Issue_Date + '\'' +
                ", Invoice_Url='" + Invoice_Url + '\'' +
                ", Attach_Info='" + Attach_Info + '\'' +
                '}';
    }
}
