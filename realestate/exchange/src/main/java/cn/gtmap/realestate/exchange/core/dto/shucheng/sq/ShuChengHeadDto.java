package cn.gtmap.realestate.exchange.core.dto.shucheng.sq;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ShuChengHeadDto {
    /**
     * 	version json报文的版本号。若为1.00可不发。
     */
    private String version = "1.00";
    /**
     * 	servCode服务编码，即业务类型,必选项。
     */
    private String servCode;
    /**
     * 	msgId 报文id, 对于每个报文都有一个标识，这个标识用于标识报文的唯一性，应答时原样返回这个id。可选项，如客户端不需要则不发
     */
    private String msgId = IdUtil.fastSimpleUUID();
    /**
     * 	msgTime 报文的发送时间，时间格式为 yyyy-MM-dd HH24：mi：ss。例如2009-06-03 23:38:02。可选，可不发。
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date msgTime = new Date();

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getServCode() {
        return servCode;
    }

    public void setServCode(String servCode) {
        this.servCode = servCode;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(Date msgTime) {
        this.msgTime = msgTime;
    }

    @Override
    public String toString() {
        return "HeadDTO{" +
                "version='" + version + '\'' +
                ", servCode='" + servCode + '\'' +
                ", msgId='" + msgId + '\'' +
                ", msgTime=" + msgTime +
                '}';
    }
}
