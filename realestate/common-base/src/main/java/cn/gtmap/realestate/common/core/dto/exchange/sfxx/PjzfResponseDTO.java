package cn.gtmap.realestate.common.core.dto.exchange.sfxx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 2022/05/25
 * @description 票据作废
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class PjzfResponseDTO {

    // 返回业务码 00000代表成功
    @XmlElement(name = "CODE", required = true)
    private String code;

    // 返回码描述
    @XmlElement(name = "MSG", required = true)
    private String msg;

    // 响应请求的时间，格式 YYYYMMDDHHMMSS
    @XmlElement(name = "TIMESTAMP")
    private String timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PjzfResponseDTo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
