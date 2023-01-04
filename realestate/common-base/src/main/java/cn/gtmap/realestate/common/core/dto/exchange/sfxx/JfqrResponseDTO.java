package cn.gtmap.realestate.common.core.dto.exchange.sfxx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 2022/05/25
 * @description 缴费确认
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class JfqrResponseDTO {

    // 返回业务码 00000代表成功
    @XmlElement(name = "CODE", required = true)
    private String code;

    // 返回码描述
    @XmlElement(name = "MSG", required = true)
    private String msg;

    // 响应请求的时间，格式 YYYYMMDDHHMMSS
    @XmlElement(name = "TIMESTAMP")
    private String timestamp;

    @XmlElement(name = "DATA", required = true)
    private JfqrResponseDataDTO data;

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

    public JfqrResponseDataDTO getData() {
        return data;
    }

    public void setData(JfqrResponseDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JfqrResponseDTo{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
