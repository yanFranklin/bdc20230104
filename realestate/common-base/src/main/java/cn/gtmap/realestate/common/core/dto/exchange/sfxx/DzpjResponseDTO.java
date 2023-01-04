package cn.gtmap.realestate.common.core.dto.exchange.sfxx;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author <a href="mailto:xuzhou@gtmap.cn">xuzhou</a>
 * @version 2022/05/25
 * @description 电子票据
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ROOT")
public class DzpjResponseDTO {

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
    private DzpjResponseDataDTO data;

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

    public DzpjResponseDataDTO getData() {
        return data;
    }

    public void setData(DzpjResponseDataDTO data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DzpjResponseDTO{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
