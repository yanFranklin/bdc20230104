package cn.gtmap.realestate.exchange.core.dto.changzhou.yjfw;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author <a href="mailto:wangyaqing@gtmap.cn">wangyaqing</a>
 * @version 1.0  2021/3/25 16:13
 * @description 易家服务接口响应head
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class YjfwResponseHeadDTO {

    @XmlElement(name = "sender")
    private String sender;

    @XmlElement(name = "receiver")
    private String receiver;

    @XmlElement(name = "appid")
    private String appid;

    @XmlElement(name = "transcode")
    private String transcode;

    @XmlElement(name = "trantime")
    private String trantime;

    @XmlElement(name = "iseqno")
    private String iseqno;

    @XmlElement(name = "retcode")
    private String retcode;

    @XmlElement(name = "retmsg")
    private String retmsg;

    @XmlElement(name = "oseqno")
    private String oseqno;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getTranscode() {
        return transcode;
    }

    public void setTranscode(String transcode) {
        this.transcode = transcode;
    }

    public String getTrantime() {
        return trantime;
    }

    public void setTrantime(String trantime) {
        this.trantime = trantime;
    }

    public String getIseqno() {
        return iseqno;
    }

    public void setIseqno(String iseqno) {
        this.iseqno = iseqno;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

    public String getOseqno() {
        return oseqno;
    }

    public void setOseqno(String oseqno) {
        this.oseqno = oseqno;
    }
}
