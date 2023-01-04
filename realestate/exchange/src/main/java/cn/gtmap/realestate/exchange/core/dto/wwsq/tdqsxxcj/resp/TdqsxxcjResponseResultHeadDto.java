package cn.gtmap.realestate.exchange.core.dto.wwsq.tdqsxxcj.resp;


import com.alibaba.fastjson.annotation.JSONField;


public class TdqsxxcjResponseResultHeadDto {
    @JSONField(name = "channel_id")
    private String channelid;
    @JSONField(name = "tran_id")
    private String tranid;
    @JSONField(name = "tran_date")
    private String trandate;
    @JSONField(name = "tran_seq")
    private String transeq;
    @JSONField(name = "rtn_code")
    private String rtncode;
    @JSONField(name = "rtn_msg")
    private String rtnmsg;

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getTranid() {
        return tranid;
    }

    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    public String getTrandate() {
        return trandate;
    }

    public void setTrandate(String trandate) {
        this.trandate = trandate;
    }

    public String getTranseq() {
        return transeq;
    }

    public void setTranseq(String transeq) {
        this.transeq = transeq;
    }

    public String getRtncode() {
        return rtncode;
    }

    public void setRtncode(String rtncode) {
        this.rtncode = rtncode;
    }

    public String getRtnmsg() {
        return rtnmsg;
    }

    public void setRtnmsg(String rtnmsg) {
        this.rtnmsg = rtnmsg;
    }

    @Override
    public String toString() {
        return "TdqsxxcjResponseResultHeadDto{" +
                "channelid='" + channelid + '\'' +
                ", tranid='" + tranid + '\'' +
                ", trandate='" + trandate + '\'' +
                ", transeq='" + transeq + '\'' +
                ", rtncode='" + rtncode + '\'' +
                ", rtnmsg='" + rtnmsg + '\'' +
                '}';
    }
}
